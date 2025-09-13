package com.van.warehouse_service.production;

import com.van.warehouse_service.dto.request.ProductionRequest;
import com.van.warehouse_service.dto.response.ProductionResponse;
import com.van.warehouse_service.dto.response.ProductionTimeResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    private final CalculationService calculationService;

    public ProductionController(ProductionService productionService, CalculationService calculationService) {
        this.productionService = productionService;
        this.calculationService = calculationService;
    }

    @PostMapping("/start")
    public ResponseEntity<ProductionResponse> startProduction(
            @Valid @RequestBody ProductionRequest request) {
        ProductionResponse response = productionService.startProduction(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/finish")
    public ResponseEntity<ProductionResponse> finishProduction(
            @Valid @RequestBody ProductionRequest request) {
        ProductionResponse response = productionService.finishProduction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calc/{productId}")
    public ResponseEntity<ProductionTimeResponse> calculateProductionTime(
            @PathVariable Long productId,
            @RequestParam Integer qty) {
        ProductionTimeResponse response = calculationService.calculateProductionTime(productId, qty);
        return ResponseEntity.ok(response);
    }
}
