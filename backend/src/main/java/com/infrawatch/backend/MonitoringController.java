package com.infrawatch.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "http://localhost:5173")
public class MonitoringController {

    private final MonitoringMetricRepository monitoringMetricRepository;

    public MonitoringController(
            MonitoringMetricRepository monitoringMetricRepository) {

        this.monitoringMetricRepository = monitoringMetricRepository;
    }

    @GetMapping("/latest")
    public MonitoringMetric getLatest() {

        return monitoringMetricRepository
                .findTop20ByOrderByTimestampDesc()
                .stream()
                .findFirst()
                .orElse(null);
    }
}