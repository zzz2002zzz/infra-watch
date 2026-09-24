cat > README.md << 'EOF'
# InfraWatch

## Infrastructure Monitoring Dashboard

InfraWatch is an infrastructure monitoring system that collects system performance metrics such as CPU, memory, and disk usage, stores the monitoring data in MongoDB, detects resource usage problems, generates alerts, and displays the information through a React dashboard.

The project uses a Spring Boot backend, React frontend, MongoDB database, and Docker for database containerization.

---

# Features

- Real-time CPU usage monitoring
- Real-time memory usage monitoring
- Real-time disk usage monitoring
- System health status detection
- NORMAL, WARNING, and CRITICAL health statuses
- Automatic monitoring data collection
- Monitoring history storage
- Active alert detection
- Automatic alert creation and updating
- Alert severity display
- Interactive monitoring charts
- Auto-refresh dashboard
- REST API backend
- MongoDB data storage
- Docker-based MongoDB setup
- React-based web dashboard

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
                    InfraWatch
                        │
          ┌─────────────┴─────────────┐
          │                           │
          ▼                           ▼
   Spring Boot Backend          React Frontend
          │                           │
          │                           │
          ▼                           ▼
   System Monitoring            Web Dashboard
          │
          ├── CPU Usage
          ├── Memory Usage
          └── Disk Usage
          │
          ▼
   Health Status Calculation
          │
     ┌────┼────┐
     ▼    ▼    ▼
  NORMAL WARNING CRITICAL
          │
          ▼
    Alert Detection
          │
          ▼
       MongoDB
```

---

# Project Structure

```text
infra-watch/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── infrawatch/
│   │   │   │           └── backend/
│   │   │   │               ├── Alert.java
│   │   │   │               ├── AlertController.java
│   │   │   │               ├── AlertRepository.java
│   │   │   │               ├── AlertService.java
│   │   │   │               ├── BackendApplication.java
│   │   │   │               ├── CorsConfig.java
│   │   │   │               ├── HealthController.java
│   │   │   │               ├── MonitoringController.java
│   │   │   │               ├── MonitoringHistoryController.java
│   │   │   │               ├── MonitoringMetric.java
│   │   │   │               ├── MonitoringMetricRepository.java
│   │   │   │               ├── MonitoringScheduler.java
│   │   │   │               ├── Server.java
│   │   │   │               ├── ServerController.java
│   │   │   │               ├── ServerRepository.java
│   │   │   │               ├── SystemMonitoringController.java
│   │   │   │               └── SystemMonitoringService.java
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.yaml
│   │   │
│   │   └── test/
│   │
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── App.jsx
│   │   ├── App.css
│   │   ├── index.css
│   │   └── main.jsx
│   │
│   ├── package.json
│   ├── package-lock.json
│   └── vite.config.js
│
├── docs/
│   ├── d1.png
│   ├── d2.png
│   └── d3.png
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

---

# Monitoring Workflow

```text
Server
   │
   ▼
Collect System Metrics
   │
   ├── CPU Usage
   ├── Memory Usage
   └── Disk Usage
   │
   ▼
Calculate Health Status
   │
   ├── NORMAL
   ├── WARNING
   └── CRITICAL
   │
   ▼
Save Monitoring Data
   │
   ▼
Check Alert Conditions
   │
   ▼
Create / Update Alerts
   │
   ▼
Store Data in MongoDB
   │
   ▼
React Dashboard
   │
   ▼
Display Metrics, Charts and Alerts
```

---

# Health Status

InfraWatch determines the overall system health based on monitored resource usage.

| Status | Description |
|---|---|
| NORMAL | System resources are within normal limits |
| WARNING | Resource usage has reached a warning level |
| CRITICAL | Resource usage has reached a critical level |

# Alert Severity

InfraWatch displays alerts according to their severity.

| Severity | UI |
|---|---|
| WARNING | 🟠 Orange |
| CRITICAL | 🔴 Red |

Example:

```
MEMORY - CRITICAL
MEMORY usage is above 90%
Value: 93.84%
```

---

# REST API

The Spring Boot backend provides REST APIs for the React dashboard.

## Monitoring API

### Get Latest Monitoring Metric

```
GET /api/monitoring/latest
```

Example response:

```json
{
  "serverName": "Development Server",
  "cpuUsage": 45.07,
  "memoryUsage": 93.78,
  "diskUsage": 13.60,
  "healthStatus": "CRITICAL",
  "timestamp": "2026-09-24T12:00:53.661",
  "id": "example-id"
}
```

### Get Monitoring History

```
GET /api/monitoring/history
```

Returns previously collected monitoring metrics used by the dashboard monitoring chart.

## Alerts API

### Get All Alerts

```
GET /api/alerts
```

Returns all stored alerts.

### Get Active Alerts

```
GET /api/alerts/active
```

Returns currently active alerts.

Example response:

```json
[
  {
    "serverName": "Development Server",
    "type": "MEMORY",
    "severity": "CRITICAL",
    "message": "MEMORY usage is above 90%",
    "value": 93.84,
    "timestamp": "2026-09-24T12:01:53.675",
    "active": true,
    "id": "example-id"
  }
]
```

---

# Running the Project

## Prerequisites

Install the following software:

- Java
- Node.js
- npm
- Docker Desktop
- Git

## 1. Start MongoDB

Open PowerShell and go to the project directory:

```powershell
cd D:\Projects\infra-watch
```

Start MongoDB using Docker Compose:

```powershell
docker compose up -d
```

Check the running container:

```powershell
docker ps
```

The MongoDB container should be running.

## 2. Start the Backend

Open a new PowerShell terminal:

```powershell
cd D:\Projects\infra-watch\backend
```

Start the Spring Boot backend:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend runs on:

```
http://localhost:8080
```

## 3. Test the Backend

Open the following URL in your browser:

```
http://localhost:8080/api/monitoring/latest
```

You should receive monitoring data in JSON format.

Example:

```json
{
  "serverName": "Development Server",
  "cpuUsage": 38.19,
  "memoryUsage": 93.91,
  "diskUsage": 13.58,
  "healthStatus": "CRITICAL"
}
```

## 4. Start the Frontend

Open another PowerShell terminal:

```powershell
cd D:\Projects\infra-watch\frontend
```

Install the frontend dependencies:

```powershell
npm install
```

Start the React development server:

```powershell
npm run dev
```

The dashboard will be available at:

```
http://localhost:5173
```

## 5. Open the Dashboard

Open your browser and visit:

```
http://localhost:5173
```

The React dashboard displays monitoring information received from the Spring Boot backend.

---

# Docker

MongoDB is configured using Docker Compose.

## Start MongoDB

```powershell
docker compose up -d
```

## Stop MongoDB

```powershell
docker compose down
```

## Check Running Containers

```powershell
docker ps
```

---

# Dashboard

The InfraWatch dashboard contains the following sections.

## Current Metrics

- CPU Usage
- Memory Usage
- Disk Usage
- System Health

The metric cards display the current resource utilization of the monitored system.

## System Monitoring

The dashboard provides an interactive line chart for:

- CPU
- Memory
- Disk

The chart displays previously collected monitoring data.

## Active Alerts

The Active Alerts section displays currently active infrastructure alerts.

Examples:

```
MEMORY - CRITICAL
MEMORY usage is above 90%
```

Severity-based styling is used:

- 🔴 Critical alerts are shown in red.
- 🟠 Warning alerts are shown in orange.
- 🟢 Normal system status is shown in green.



---

# Project Goals

The main goal of InfraWatch is to provide a simple infrastructure monitoring solution that can:

- Collect system performance metrics
- Store monitoring data
- Monitor CPU usage
- Monitor memory usage
- Monitor disk usage
- Detect high resource usage
- Calculate system health status
- Generate alerts
- Update existing alerts
- Display monitoring information through a web dashboard
- Provide REST APIs for monitoring data
- Provide a foundation for future infrastructure monitoring features

---

# Future Improvements

Possible future improvements include:

- User authentication
- Multiple server monitoring
- Server registration through the dashboard
- Email notifications
- Slack notifications
- Alert acknowledgement
- Alert history and filtering
- Advanced monitoring charts
- Docker-based deployment
- Cloud deployment
- Role-based access control
- Infrastructure health reports
- Prometheus integration
- Grafana integration

---

# GitHub Repository

https://github.com/zzz2002zzz/infra-watch

---

# Author

Chamudi Thamasha

BSc (Hons) Information Technology

Horizon Campus, Sri Lanka

---

# License

This project was created for educational and portfolio purposes.

