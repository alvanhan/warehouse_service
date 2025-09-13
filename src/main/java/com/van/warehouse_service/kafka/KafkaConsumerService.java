package com.van.warehouse_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "production-events", groupId = "warehouse-monitoring-group")
    public void handleProductionEvent(KafkaProducerService.ProductionEvent event) {
        try {
            // For testing purposes, just log the event
            logger.info("=== PRODUCTION EVENT RECEIVED ===");
            logger.info("Event ID: {}", event.getEventId());
            logger.info("Product ID: {}", event.getProductId());
            logger.info("Quantity: {}", event.getQuantity());
            logger.info("Event Type: {}", event.getEventType());
            logger.info("Process: {}", event.getProcess());
            logger.info("Timestamp: {}", event.getTimestamp());
            logger.info("================================");
        } catch (Exception e) {
            logger.error("Error processing production event: {}", e.getMessage());
        }
    }
}
