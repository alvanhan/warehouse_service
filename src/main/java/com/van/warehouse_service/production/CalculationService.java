package com.van.warehouse_service.production;

import com.van.warehouse_service.dto.response.ProductionTimeResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CalculationService {

    // Process duration constants (in minutes)
    private static final int CUTTING_DURATION = 2;
    private static final int SAWING_DURATION = 3;
    private static final int FINISHING_DURATION = 5;

    public ProductionTimeResponse calculateProductionTime(Long productId, Integer qty) {
        // Calculate time for each process
        int cuttingTime = qty * CUTTING_DURATION;
        int sawingTime = qty * SAWING_DURATION;
        int finishingTime = qty * FINISHING_DURATION;

        // Total estimated time
        int totalTime = cuttingTime + sawingTime + finishingTime;

        // Process breakdown
        Map<String, Integer> processBreakdown = new HashMap<>();
        processBreakdown.put("cutting", cuttingTime);
        processBreakdown.put("sawing", sawingTime);
        processBreakdown.put("finishing", finishingTime);

        return new ProductionTimeResponse(productId, qty, totalTime, processBreakdown);
    }
}
