package com.van.warehouse_service.controller;

import com.van.warehouse_service.dto.response.MonitoringSummaryResponse;
import com.van.warehouse_service.warehouse.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping
    public ResponseEntity<MonitoringSummaryResponse> getMonitoringSummary() {
        MonitoringSummaryResponse response = monitoringService.getMonitoringSummary();
        return ResponseEntity.ok(response);
    }
}
