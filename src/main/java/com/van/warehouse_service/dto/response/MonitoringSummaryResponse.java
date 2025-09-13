package com.van.warehouse_service.dto.response;

import java.util.List;

public class MonitoringSummaryResponse {

    private List<RawMaterialItem> rawMaterials;
    private List<WorkInProgressItem> workInProgress;
    private List<FinishedGoodsItem> finishedGoods;

    // Constructors
    public MonitoringSummaryResponse() {}

    public MonitoringSummaryResponse(List<RawMaterialItem> rawMaterials, List<WorkInProgressItem> workInProgress, List<FinishedGoodsItem> finishedGoods) {
        this.rawMaterials = rawMaterials;
        this.workInProgress = workInProgress;
        this.finishedGoods = finishedGoods;
    }

    // Getters and Setters
    public List<RawMaterialItem> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(List<RawMaterialItem> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public List<WorkInProgressItem> getWorkInProgress() {
        return workInProgress;
    }

    public void setWorkInProgress(List<WorkInProgressItem> workInProgress) {
        this.workInProgress = workInProgress;
    }

    public List<FinishedGoodsItem> getFinishedGoods() {
        return finishedGoods;
    }

    public void setFinishedGoods(List<FinishedGoodsItem> finishedGoods) {
        this.finishedGoods = finishedGoods;
    }

    // Inner classes
    public static class RawMaterialItem {
        private Long productId;
        private String productName;
        private Integer totalQuantity;

        public RawMaterialItem() {}

        public RawMaterialItem(Long productId, String productName, Integer totalQuantity) {
            this.productId = productId;
            this.productName = productName;
            this.totalQuantity = totalQuantity;
        }

        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }
    }

    public static class WorkInProgressItem {
        private String process;
        private Integer totalQuantity;

        public WorkInProgressItem() {}

        public WorkInProgressItem(String process, Integer totalQuantity) {
            this.process = process;
            this.totalQuantity = totalQuantity;
        }

        // Getters and Setters
        public String getProcess() { return process; }
        public void setProcess(String process) { this.process = process; }
        public Integer getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }
    }

    public static class FinishedGoodsItem {
        private Long productId;
        private String productName;
        private Integer totalQuantity;

        public FinishedGoodsItem() {}

        public FinishedGoodsItem(Long productId, String productName, Integer totalQuantity) {
            this.productId = productId;
            this.productName = productName;
            this.totalQuantity = totalQuantity;
        }

        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }
    }
}
