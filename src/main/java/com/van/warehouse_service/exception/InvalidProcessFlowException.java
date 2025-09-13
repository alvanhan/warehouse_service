package com.van.warehouse_service.exception;

public class InvalidProcessFlowException extends RuntimeException {
    public InvalidProcessFlowException(String message) {
        super(message);
    }
}
