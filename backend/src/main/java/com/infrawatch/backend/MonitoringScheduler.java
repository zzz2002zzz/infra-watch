package com.infrawatch.backend;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MonitoringScheduler {

    private final SystemMonitoringService monitoringService;
    private final AlertService alertService;

    public MonitoringScheduler(
            SystemMonitoringService monitoringService,
            AlertService alertService) {

        this.monitoringService = monitoringService;
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 30000)
    public void collectMetrics() {

        Map<String, Object> metrics =
                monitoringService.collectAndSaveMetrics();

        double cpuUsage =
                ((Number) metrics.get("cpuUsage")).doubleValue();

        double memoryUsage =
                ((Number) metrics.get("memoryUsage")).doubleValue();

        double diskUsage =
                ((Number) metrics.get("diskUsage")).doubleValue();

        alertService.checkAndCreateAlerts(
                "Development Server",
                cpuUsage,
                memoryUsage,
                diskUsage
        );

        System.out.println(
                "InfraWatch: Monitoring metrics collected and saved."
        );
    }
}