package com.infrawatch.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/monitoring")
public class SystemMonitoringController {

    private final SystemMonitoringService monitoringService;

    public SystemMonitoringController(SystemMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping
    public Map<String, Object> getMetrics() {
        return monitoringService.getSystemMetrics();
    }
}
