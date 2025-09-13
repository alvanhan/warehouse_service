package com.van.warehouse_service.dto.response;

import java.util.List;

public class StockInfoResponse {

    private String warehouse;
    private List<StockItem> stocks;

    // Constructors
    public StockInfoResponse() {}

    public StockInfoResponse(String warehouse, List<StockItem> stocks) {
        this.warehouse = warehouse;
        this.stocks = stocks;
    }

    // Getters and Setters
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public List<StockItem> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockItem> stocks) {
        this.stocks = stocks;
    }

    // Inner class for stock items
    public static class StockItem {
        private Long productId;
        private String process;
        private Integer qty;

        public StockItem() {}

        public StockItem(Long productId, String process, Integer qty) {
            this.productId = productId;
            this.process = process;
            this.qty = qty;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }
    }
}
