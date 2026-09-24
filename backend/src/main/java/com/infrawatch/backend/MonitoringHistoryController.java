package com.infrawatch.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring/history")
public class MonitoringHistoryController {

    private final MonitoringMetricRepository monitoringMetricRepository;

    public MonitoringHistoryController(
            MonitoringMetricRepository monitoringMetricRepository) {
        this.monitoringMetricRepository = monitoringMetricRepository;
    }

    @GetMapping
    public List<MonitoringMetric> getMonitoringHistory() {
        return monitoringMetricRepository.findAll();
    }
}