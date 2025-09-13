package com.van.warehouse_service.dto.response;

public class ProductionResponse {

    private String status;
    private String message;

    // Constructors
    public ProductionResponse() {}

    public ProductionResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Static factory methods for common responses
    public static ProductionResponse success(String message) {
        return new ProductionResponse("SUCCESS", message);
    }

    public static ProductionResponse error(String message) {
        return new ProductionResponse("ERROR", message);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
