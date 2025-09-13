package com.van.warehouse_service.warehouse;

import com.van.warehouse_service.domain.enums.WarehouseType;
import com.van.warehouse_service.dto.response.StockInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<StockInfoResponse> getStockByType(
            @PathVariable WarehouseType type) {
        StockInfoResponse response = warehouseService.getStockByWarehouseType(type);
        return ResponseEntity.ok(response);
    }
}
