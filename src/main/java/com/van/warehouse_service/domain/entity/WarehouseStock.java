package com.van.warehouse_service.domain.entity;

import com.van.warehouse_service.domain.enums.ProcessType;
import com.van.warehouse_service.domain.enums.WarehouseType;
import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_stocks")
public class WarehouseStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "warehouse_type", nullable = false)
    private WarehouseType warehouseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_type")
    private ProcessType processType;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    // Constructors
    public WarehouseStock() {}

    public WarehouseStock(Product product, Integer quantity, WarehouseType warehouseType, ProcessType processType) {
        this.product = product;
        this.quantity = quantity;
        this.warehouseType = warehouseType;
        this.processType = processType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
