package com.van.warehouse_service.production;

import com.van.warehouse_service.domain.entity.Product;
import com.van.warehouse_service.domain.entity.WarehouseStock;
import com.van.warehouse_service.domain.enums.ProcessType;
import com.van.warehouse_service.domain.enums.WarehouseType;
import com.van.warehouse_service.dto.request.ProductionRequest;
import com.van.warehouse_service.dto.response.ProductionResponse;
import com.van.warehouse_service.exception.InsufficientStockException;
import com.van.warehouse_service.exception.InvalidProcessFlowException;
import com.van.warehouse_service.exception.ProductNotFoundException;
import com.van.warehouse_service.kafka.KafkaProducerService;
import com.van.warehouse_service.repository.ProductRepository;
import com.van.warehouse_service.repository.WarehouseStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductionService {

    private static final Logger logger = LoggerFactory.getLogger(ProductionService.class);

    private final WarehouseStockRepository warehouseStockRepository;

    private final ProductRepository productRepository;

    private final KafkaProducerService kafkaProducerService;

    public ProductionService(WarehouseStockRepository warehouseStockRepository, ProductRepository productRepository, KafkaProducerService kafkaProducerService) {
        this.warehouseStockRepository = warehouseStockRepository;
        this.productRepository = productRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional
    public ProductionResponse startProduction(ProductionRequest request) {
        logger.info("Starting production process: {} for product {} with quantity {}",
                   request.getProcess(), request.getProductId(), request.getQty());

        // Validate product exists
        productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + request.getProductId() + " not found"));

        WarehouseStock sourceStock;
        WarehouseStock targetStock;

        // Handle different process flows with pessimistic locking
        switch (request.getProcess()) {
            case CUTTING -> {
                // CUTTING: RAW -> WIP(CUTTING)
                sourceStock = getStockWithLock(request.getProductId(), WarehouseType.RAW, null);
                targetStock = getOrCreateStock(request.getProductId(), WarehouseType.WIP, ProcessType.CUTTING);
            }
            case SAWING -> {
                // SAWING: WIP(CUTTING) -> WIP(SAWING)
                sourceStock = getStockWithLock(request.getProductId(), WarehouseType.WIP, ProcessType.CUTTING);
                targetStock = getOrCreateStock(request.getProductId(), WarehouseType.WIP, ProcessType.SAWING);
            }
            case FINISHING -> {
                // FINISHING: WIP(SAWING) -> WIP(FINISHING)
                sourceStock = getStockWithLock(request.getProductId(), WarehouseType.WIP, ProcessType.SAWING);
                targetStock = getOrCreateStock(request.getProductId(), WarehouseType.WIP, ProcessType.FINISHING);
            }
            default -> throw new InvalidProcessFlowException("Invalid process type: " + request.getProcess());
        }

        // Validate sufficient stock
        if (sourceStock.getQuantity() < request.getQty()) {
            throw new InsufficientStockException(
                String.format("Insufficient stock. Required: %d, Available: %d",
                             request.getQty(), sourceStock.getQuantity()));
        }

        // Perform stock transfer
        sourceStock.setQuantity(sourceStock.getQuantity() - request.getQty());
        targetStock.setQuantity(targetStock.getQuantity() + request.getQty());

        // Save changes
        warehouseStockRepository.save(sourceStock);
        warehouseStockRepository.save(targetStock);

        // Send Kafka event
        kafkaProducerService.sendProductionEvent(
                request.getProductId(),
                request.getQty(),
                "PRODUCTION_STARTED",
                request.getProcess().name()
        );

        String message = String.format("Production process %s for product %d started successfully for %d units.",
                                     request.getProcess(), request.getProductId(), request.getQty());

        logger.info(message);
        return ProductionResponse.success(message);
    }

    @Transactional
    public ProductionResponse finishProduction(ProductionRequest request) {
        logger.info("Finishing production for product {} with quantity {}",
                   request.getProductId(), request.getQty());

        // Validate process is FINISHING
        if (request.getProcess() != ProcessType.FINISHING) {
            throw new InvalidProcessFlowException("Only FINISHING process is allowed for finish production");
        }

        // Validate product exists
        productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + request.getProductId() + " not found"));

        // Get source stock with pessimistic locking
        WarehouseStock sourceStock = getStockWithLock(request.getProductId(), WarehouseType.WIP, ProcessType.FINISHING);
        WarehouseStock targetStock = getOrCreateStock(request.getProductId(), WarehouseType.FINISHED, null);

        // Validate sufficient stock
        if (sourceStock.getQuantity() < request.getQty()) {
            throw new InsufficientStockException(
                String.format("Insufficient stock in FINISHING. Required: %d, Available: %d",
                             request.getQty(), sourceStock.getQuantity()));
        }

        // Transfer stock from WIP-FINISHING to FINISHED
        sourceStock.setQuantity(sourceStock.getQuantity() - request.getQty());
        targetStock.setQuantity(targetStock.getQuantity() + request.getQty());

        // Save changes
        warehouseStockRepository.save(sourceStock);
        warehouseStockRepository.save(targetStock);

        // Send Kafka event
        kafkaProducerService.sendProductionEvent(
                request.getProductId(),
                request.getQty(),
                "PRODUCTION_FINISHED",
                "FINISHED"
        );

        String message = String.format("Production finished for product %d, %d units moved to finished goods warehouse.",
                                     request.getProductId(), request.getQty());

        logger.info(message);
        return ProductionResponse.success(message);
    }

    // Helper method to get stock with pessimistic locking - Race-condition safe
    private WarehouseStock getStockWithLock(Long productId, WarehouseType warehouseType, ProcessType processType) {
        Optional<WarehouseStock> stockOpt;

        if (processType == null) {
            stockOpt = warehouseStockRepository.findByProductIdAndWarehouseTypeWithLock(productId, warehouseType);
        } else {
            stockOpt = warehouseStockRepository.findByProductIdAndWarehouseTypeAndProcessTypeWithLock(productId, warehouseType, processType);
        }

        return stockOpt.orElseThrow(() ->
            new InvalidProcessFlowException(
                String.format("Stock not found for product %d in %s%s",
                             productId, warehouseType,
                             processType != null ? " with process " + processType : "")));
    }

    // Helper method to get or create stock
    private WarehouseStock getOrCreateStock(Long productId, WarehouseType warehouseType, ProcessType processType) {
        Optional<WarehouseStock> stockOpt;

        if (processType == null) {
            stockOpt = warehouseStockRepository.findByProductIdAndWarehouseType(productId, warehouseType);
        } else {
            stockOpt = warehouseStockRepository.findByProductIdAndWarehouseTypeAndProcessType(productId, warehouseType, processType);
        }

        if (stockOpt.isPresent()) {
            return stockOpt.get();
        } else {
            // Create new stock record
            Product product = productRepository.findById(productId).orElseThrow();
            WarehouseStock newStock = new WarehouseStock(product, 0, warehouseType, processType);
            return warehouseStockRepository.save(newStock);
        }
    }
}
