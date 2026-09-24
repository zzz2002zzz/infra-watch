package com.infrawatch.backend;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void checkAndCreateAlerts(
            String serverName,
            double cpuUsage,
            double memoryUsage,
            double diskUsage) {

        checkResource(serverName, "CPU", cpuUsage);
        checkResource(serverName, "MEMORY", memoryUsage);
        checkResource(serverName, "DISK", diskUsage);
    }

    private void checkResource(
            String serverName,
            String type,
            double value) {

        // Resource is normal
        if (value < 80) {
            resolveAlert(serverName, type);
            return;
        }

        String severity = value > 90 ? "CRITICAL" : "WARNING";

        Optional<Alert> existingAlert =
                alertRepository.findFirstByServerNameAndTypeAndActiveTrue(
                        serverName,
                        type
                );

        if (existingAlert.isPresent()) {

            // Update the existing active alert
            Alert alert = existingAlert.get();

            alert.setSeverity(severity);
            alert.setValue(value);
            alert.setMessage(createMessage(type, severity));
            alert.setTimestamp(LocalDateTime.now());

            alertRepository.save(alert);

            System.out.println(
                    "InfraWatch: Existing "
                            + type
                            + " alert updated: "
                            + value
                            + "%"
            );

        } else {

            // Create a new alert
            Alert alert = new Alert(
                    serverName,
                    type,
                    severity,
                    createMessage(type, severity),
                    value,
                    LocalDateTime.now(),
                    true
            );

            alertRepository.save(alert);

            System.out.println(
                    "InfraWatch ALERT: "
                            + severity
                            + " - "
                            + type
                            + " usage is "
                            + value
                            + "%"
            );
        }
    }

    private String createMessage(
            String type,
            String severity) {

        if (severity.equals("CRITICAL")) {
            return type + " usage is above 90%";
        }

        return type + " usage is above 80%";
    }

    private void resolveAlert(
            String serverName,
            String type) {

        Optional<Alert> existingAlert =
                alertRepository.findFirstByServerNameAndTypeAndActiveTrue(
                        serverName,
                        type
                );

        if (existingAlert.isPresent()) {

            Alert alert = existingAlert.get();

            alert.setActive(false);
            alert.setTimestamp(LocalDateTime.now());

            alertRepository.save(alert);

            System.out.println(
                    "InfraWatch: "
                            + type
                            + " alert resolved."
            );
        }
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}