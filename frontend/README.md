# InfraWatch

## Infrastructure Monitoring Dashboard

InfraWatch is a full-stack infrastructure monitoring system that collects server performance metrics and displays them through a web-based monitoring dashboard.

The system monitors:

- CPU usage
- Memory usage
- Disk usage
- Overall system health
- Active alerts
- Monitoring history

When resource usage reaches configured thresholds, InfraWatch generates alerts with different severity levels such as `WARNING` and `CRITICAL`.

---

# Features

## 📊 Real-Time Monitoring

InfraWatch automatically collects server performance metrics and stores them in MongoDB.

The monitored metrics include:

- CPU Usage
- Memory Usage
- Disk Usage
- Health Status
- Timestamp

## 🚨 Alert Monitoring

The system detects high resource usage and creates alerts.

Alert severity levels include:

- 🟠 WARNING
- 🔴 CRITICAL

Active alerts are displayed on the dashboard with:

- Alert type
- Severity
- Message
- Current value
- Timestamp

## 📈 Monitoring History

The dashboard displays historical monitoring data using interactive line charts.

The chart shows:

- CPU usage
- Memory usage
- Disk usage

## 🔄 Automatic Refresh

The React dashboard automatically refreshes monitoring information every 30 seconds.

## 🖥️ Web Dashboard

The dashboard provides a simple interface for viewing the current condition of the monitored server.

The dashboard displays:

- Current CPU usage
- Current memory usage
- Current disk usage
- System health
- Monitoring history
- Active alerts

---

# Technologies Used

## Backend

- Java
- Spring Boot
- Spring Web
- Spring Data MongoDB
- Maven

## Frontend

- React
- JavaScript
- Vite
- Recharts
- HTML
- CSS

## Database

- MongoDB

## Containerization

- Docker
- Docker Compose

## Development Tools

- Visual Studio Code
- Git
- GitHub
- PowerShell

---

# System Architecture

```text
                    ┌──────────────────────┐
                    │     React Frontend   │
                    │                      │
                    │  Monitoring Dashboard│
                    │      + Recharts      │
                    └──────────┬───────────┘
                               │
                               │ REST API
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │                      │
                    │ Controllers          │
                    │ Services             │
                    │ Monitoring Scheduler │
                    └──────────┬───────────┘
                               │
                               │ Spring Data MongoDB
                               ▼
                    ┌──────────────────────┐
                    │       MongoDB        │
                    │                      │
                    │ Monitoring Metrics   │
                    │ Alerts               │
                    │ Server Information   │
                    └──────────────────────┘