package com.infrawatch.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "monitoring_metrics")
public class MonitoringMetric {

    @Id
    private String id;

    private String serverName;
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private String healthStatus;
    private LocalDateTime timestamp;

    public MonitoringMetric() {
    }

    public MonitoringMetric(
            String serverName,
            double cpuUsage,
            double memoryUsage,
            double diskUsage,
            String healthStatus,
            LocalDateTime timestamp) {

        this.serverName = serverName;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.healthStatus = healthStatus;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}