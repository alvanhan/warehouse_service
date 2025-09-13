package com.van.warehouse_service.repository;

import com.van.warehouse_service.domain.entity.WarehouseStock;
import com.van.warehouse_service.domain.enums.ProcessType;
import com.van.warehouse_service.domain.enums.WarehouseType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {

    // Find stocks by warehouse type
    List<WarehouseStock> findByWarehouseType(WarehouseType warehouseType);

    // Find stock with pessimistic locking for race-condition safety
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.product.id = :productId AND ws.warehouseType = :warehouseType AND ws.processType = :processType")
    Optional<WarehouseStock> findByProductIdAndWarehouseTypeAndProcessTypeWithLock(
            @Param("productId") Long productId,
            @Param("warehouseType") WarehouseType warehouseType,
            @Param("processType") ProcessType processType);

    // Find stock with pessimistic locking for RAW materials (no process type)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.product.id = :productId AND ws.warehouseType = :warehouseType AND ws.processType IS NULL")
    Optional<WarehouseStock> findByProductIdAndWarehouseTypeWithLock(
            @Param("productId") Long productId,
            @Param("warehouseType") WarehouseType warehouseType);

    // Monitoring queries - aggregate data
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.warehouseType = 'RAW'")
    List<WarehouseStock> findAllRawMaterials();

    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.warehouseType = 'WIP'")
    List<WarehouseStock> findAllWorkInProgress();

    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.warehouseType = 'FINISHED'")
    List<WarehouseStock> findAllFinishedGoods();

    // Find specific stock without locking (for read operations)
    Optional<WarehouseStock> findByProductIdAndWarehouseTypeAndProcessType(
            Long productId, WarehouseType warehouseType, ProcessType processType);

    Optional<WarehouseStock> findByProductIdAndWarehouseType(Long productId, WarehouseType warehouseType);
}
