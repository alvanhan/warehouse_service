package com.van.warehouse_service.warehouse;

import com.van.warehouse_service.domain.entity.WarehouseStock;
import com.van.warehouse_service.dto.response.MonitoringSummaryResponse;
import com.van.warehouse_service.repository.WarehouseStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonitoringService {

    private final WarehouseStockRepository warehouseStockRepository;

    public MonitoringService(WarehouseStockRepository warehouseStockRepository) {
        this.warehouseStockRepository = warehouseStockRepository;
    }

    public MonitoringSummaryResponse getMonitoringSummary() {
        // Get Raw Materials
        List<WarehouseStock> rawStocks = warehouseStockRepository.findAllRawMaterials();
        List<MonitoringSummaryResponse.RawMaterialItem> rawMaterials = rawStocks.stream()
                .map(stock -> new MonitoringSummaryResponse.RawMaterialItem(
                        stock.getProduct().getId(),
                        stock.getProduct().getName(),
                        stock.getQuantity()
                ))
                .collect(Collectors.toList());

        // Get Work In Progress - aggregate by process type
        List<WarehouseStock> wipStocks = warehouseStockRepository.findAllWorkInProgress();
        Map<String, Integer> wipGrouped = wipStocks.stream()
                .collect(Collectors.groupingBy(
                        stock -> stock.getProcessType().name(),
                        Collectors.summingInt(WarehouseStock::getQuantity)
                ));

        List<MonitoringSummaryResponse.WorkInProgressItem> workInProgress = wipGrouped.entrySet().stream()
                .map(entry -> new MonitoringSummaryResponse.WorkInProgressItem(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Get Finished Goods
        List<WarehouseStock> finishedStocks = warehouseStockRepository.findAllFinishedGoods();
        List<MonitoringSummaryResponse.FinishedGoodsItem> finishedGoods = finishedStocks.stream()
                .map(stock -> new MonitoringSummaryResponse.FinishedGoodsItem(
                        stock.getProduct().getId(),
                        stock.getProduct().getName(),
                        stock.getQuantity()
                ))
                .collect(Collectors.toList());

        return new MonitoringSummaryResponse(rawMaterials, workInProgress, finishedGoods);
    }
}
