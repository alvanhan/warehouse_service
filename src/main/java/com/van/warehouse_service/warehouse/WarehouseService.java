package com.van.warehouse_service.warehouse;

import com.van.warehouse_service.domain.entity.WarehouseStock;
import com.van.warehouse_service.domain.enums.WarehouseType;
import com.van.warehouse_service.dto.response.StockInfoResponse;
import com.van.warehouse_service.repository.WarehouseStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private final WarehouseStockRepository warehouseStockRepository;

    public WarehouseService(WarehouseStockRepository warehouseStockRepository) {
        this.warehouseStockRepository = warehouseStockRepository;
    }

    public StockInfoResponse getStockByWarehouseType(WarehouseType warehouseType) {
        List<WarehouseStock> stocks = warehouseStockRepository.findByWarehouseType(warehouseType);

        List<StockInfoResponse.StockItem> stockItems = stocks.stream()
                .map(stock -> new StockInfoResponse.StockItem(
                        stock.getProduct().getId(),
                        stock.getProcessType() != null ? stock.getProcessType().name() : null,
                        stock.getQuantity()
                ))
                .collect(Collectors.toList());

        return new StockInfoResponse(warehouseType.name(), stockItems);
    }
}
