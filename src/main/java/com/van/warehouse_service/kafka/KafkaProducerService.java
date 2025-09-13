package com.van.warehouse_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC_NAME = "production-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductionEvent(Long productId, Integer quantity, String eventType, String process) {
        try {
            ProductionEvent event = new ProductionEvent(
                UUID.randomUUID().toString(),
                productId,
                quantity,
                eventType,
                process,
                LocalDateTime.now().toString()
            );

            kafkaTemplate.send(TOPIC_NAME, event);
            logger.info("Production event sent: {}", event);
        } catch (Exception e) {
            logger.error("Failed to send production event: {}", e.getMessage());
        }
    }

    // Inner class for Production Event
    public static class ProductionEvent {
        private String eventId;
        private Long productId;
        private Integer quantity;
        private String eventType;
        private String process;
        private String timestamp;

        public ProductionEvent() {}

        public ProductionEvent(String eventId, Long productId, Integer quantity, String eventType, String process, String timestamp) {
            this.eventId = eventId;
            this.productId = productId;
            this.quantity = quantity;
            this.eventType = eventType;
            this.process = process;
            this.timestamp = timestamp;
        }

        // Getters and Setters
        public String getEventId() { return eventId; }
        public void setEventId(String eventId) { this.eventId = eventId; }
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public String getProcess() { return process; }
        public void setProcess(String process) { this.process = process; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

        @Override
        public String toString() {
            return "ProductionEvent{" +
                    "eventId='" + eventId + '\'' +
                    ", productId=" + productId +
                    ", quantity=" + quantity +
                    ", eventType='" + eventType + '\'' +
                    ", process='" + process + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }
}
