package com.infrawatch.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String serverName;
    private String type;
    private String severity;
    private String message;
    private double value;
    private LocalDateTime timestamp;
    private boolean active;

    // Default constructor
    public Alert() {
    }

    // Constructor
    public Alert(
            String serverName,
            String type,
            String severity,
            String message,
            double value,
            LocalDateTime timestamp,
            boolean active) {

        this.serverName = serverName;
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.value = value;
        this.timestamp = timestamp;
        this.active = active;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}