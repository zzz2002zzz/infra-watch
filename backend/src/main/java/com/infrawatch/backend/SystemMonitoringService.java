package com.infrawatch.backend;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SystemMonitoringService {

    private final MonitoringMetricRepository monitoringMetricRepository;

    public SystemMonitoringService(
            MonitoringMetricRepository monitoringMetricRepository) {

        this.monitoringMetricRepository = monitoringMetricRepository;
    }

    public Map<String, Object> getSystemMetrics() {

        return collectMetrics();
    }

    public Map<String, Object> collectAndSaveMetrics() {

        Map<String, Object> metrics = collectMetrics();

        double cpuUsage = (double) metrics.get("cpuUsage");
        double memoryUsage = (double) metrics.get("memoryUsage");
        double diskUsage = (double) metrics.get("diskUsage");
        String healthStatus = (String) metrics.get("healthStatus");

        MonitoringMetric monitoringMetric = new MonitoringMetric(
                "Development Server",
                cpuUsage,
                memoryUsage,
                diskUsage,
                healthStatus,
                LocalDateTime.now()
        );

        monitoringMetricRepository.save(monitoringMetric);

        return metrics;
    }

    private Map<String, Object> collectMetrics() {

        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean)
                        ManagementFactory.getOperatingSystemMXBean();

        Map<String, Object> metrics = new LinkedHashMap<>();

        // CPU
        double cpuUsage = osBean.getCpuLoad() * 100;

        // Memory
        long totalMemory = osBean.getTotalMemorySize();
        long freeMemory = osBean.getFreeMemorySize();

        double memoryUsage =
                ((double) (totalMemory - freeMemory) / totalMemory) * 100;

        // Disk
        File disk = new File("D:");

        long totalDisk = disk.getTotalSpace();
        long freeDisk = disk.getFreeSpace();

        double diskUsage =
                ((double) (totalDisk - freeDisk) / totalDisk) * 100;

        // Uptime
        long uptimeMillis =
                ManagementFactory.getRuntimeMXBean().getUptime();

        long uptimeSeconds = uptimeMillis / 1000;

        // Health status
        String healthStatus;

        if (cpuUsage > 90 ||
                memoryUsage > 90 ||
                diskUsage > 90) {

            healthStatus = "CRITICAL";

        } else if (cpuUsage >= 80 ||
                memoryUsage >= 80 ||
                diskUsage >= 80) {

            healthStatus = "WARNING";

        } else {

            healthStatus = "NORMAL";
        }

        // Round values
        double roundedCpu =
                Math.round(cpuUsage * 100.0) / 100.0;

        double roundedMemory =
                Math.round(memoryUsage * 100.0) / 100.0;

        double roundedDisk =
                Math.round(diskUsage * 100.0) / 100.0;

        // API response
        metrics.put("cpuUsage", roundedCpu);

        metrics.put("memoryUsage", roundedMemory);

        metrics.put(
                "totalMemoryMB",
                totalMemory / (1024 * 1024)
        );

        metrics.put(
                "freeMemoryMB",
                freeMemory / (1024 * 1024)
        );

        metrics.put("diskUsage", roundedDisk);

        metrics.put(
                "totalDiskGB",
                totalDisk / (1024 * 1024 * 1024)
        );

        metrics.put(
                "freeDiskGB",
                freeDisk / (1024 * 1024 * 1024)
        );

        metrics.put("uptimeSeconds", uptimeSeconds);

        metrics.put("healthStatus", healthStatus);

        return metrics;
    }
}