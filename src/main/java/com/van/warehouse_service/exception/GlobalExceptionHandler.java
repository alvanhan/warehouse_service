package com.van.warehouse_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        response.put("errorCode", "INSUFFICIENT_STOCK");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidProcessFlowException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidProcessFlow(InvalidProcessFlowException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        response.put("errorCode", "INVALID_PROCESS_FLOW");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(ProductNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        response.put("errorCode", "PRODUCT_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStockNotFound(StockNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        response.put("errorCode", "STOCK_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        response.put("status", "ERROR");
        response.put("message", "Validation failed");
        response.put("errors", errors);
        response.put("errorCode", "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", "An unexpected error occurred");
        response.put("errorCode", "INTERNAL_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
