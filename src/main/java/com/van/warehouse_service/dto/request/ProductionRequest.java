package com.van.warehouse_service.dto.request;

import com.van.warehouse_service.domain.enums.ProcessType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductionRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer qty;

    @NotNull(message = "Process type cannot be null")
    private ProcessType process;

    // Constructors
    public ProductionRequest() {}

    public ProductionRequest(Long productId, Integer qty, ProcessType process) {
        this.productId = productId;
        this.qty = qty;
        this.process = process;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public Integer getQty() {
        return qty;
    }

    public ProcessType getProcess() {
        return process;
    }

    public void setProcess(ProcessType process) {
        this.process = process;
    }
}
