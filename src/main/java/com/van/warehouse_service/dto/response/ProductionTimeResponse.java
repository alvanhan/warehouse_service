package com.van.warehouse_service.dto.response;

import java.util.Map;

public class ProductionTimeResponse {

    private Long productId;
    private Integer qty;
    private Integer estimatedTimeMinutes;
    private Map<String, Integer> processBreakdown;

    // Constructors
    public ProductionTimeResponse() {}

    public ProductionTimeResponse(Long productId, Integer qty, Integer estimatedTimeMinutes, Map<String, Integer> processBreakdown) {
        this.productId = productId;
        this.qty = qty;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.processBreakdown = processBreakdown;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public Map<String, Integer> getProcessBreakdown() {
        return processBreakdown;
    }

    public void setProcessBreakdown(Map<String, Integer> processBreakdown) {
        this.processBreakdown = processBreakdown;
    }
}
