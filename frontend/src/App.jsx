import { useEffect, useState } from "react";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

import "./App.css";

const API_BASE_URL = "http://localhost:8080/api";

function App() {
  const [latest, setLatest] = useState(null);
  const [history, setHistory] = useState([]);
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchDashboardData = async () => {
    try {
      const [latestResponse, historyResponse, alertsResponse] =
        await Promise.all([
          fetch(`${API_BASE_URL}/monitoring/latest`),
          fetch(`${API_BASE_URL}/monitoring/history`),
          fetch(`${API_BASE_URL}/alerts/active`),
        ]);

      if (!latestResponse.ok) {
        throw new Error("Latest monitoring API failed");
      }

      if (!historyResponse.ok) {
        throw new Error("Monitoring history API failed");
      }

      if (!alertsResponse.ok) {
        throw new Error("Alerts API failed");
      }

      const latestData = await latestResponse.json();
      const historyData = await historyResponse.json();
      const alertsData = await alertsResponse.json();

      setLatest(latestData);

      const sortedHistory = [...historyData].sort(
        (a, b) =>
          new Date(a.timestamp).getTime() -
          new Date(b.timestamp).getTime()
      );

      setHistory(sortedHistory);
      setAlerts(alertsData);
      setError("");
    } catch (err) {
      console.error("Dashboard error:", err);

      setError(
        "Unable to connect to InfraWatch backend. Make sure Spring Boot is running on port 8080."
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDashboardData();

    const interval = setInterval(fetchDashboardData, 30000);

    return () => clearInterval(interval);
  }, []);

  const getStatusClass = (status) => {
    if (status === "CRITICAL") return "critical";
    if (status === "WARNING") return "warning";

    return "normal";
  };

  const formatTime = (timestamp) => {
    if (!timestamp) return "-";

    return new Date(timestamp).toLocaleTimeString();
  };

  const formatChartTime = (timestamp) => {
    if (!timestamp) return "";

    const date = new Date(timestamp);

    return date.toLocaleString([], {
      month: "short",
      day: "numeric",
      hour: "numeric",
      minute: "2-digit",
    });
  };

  const getMetricStatus = (value) => {
    if (value >= 90) return "critical";
    if (value >= 80) return "warning";

    return "normal";
  };

  const chartData = history.map((item) => ({
    time: formatChartTime(item.timestamp),
    CPU: Number(item.cpuUsage?.toFixed(2)),
    Disk: Number(item.diskUsage?.toFixed(2)),
    Memory: Number(item.memoryUsage?.toFixed(2)),
  }));

  if (loading) {
    return (
      <div className="loading-screen">
        <h1>InfraWatch</h1>
        <p>Loading monitoring data...</p>
      </div>
    );
  }

  return (
    <div className="dashboard">

      {/* HEADER */}

      <header className="header">

        <div>
          <h1>InfraWatch</h1>

          <p>
            Infrastructure Monitoring Dashboard
          </p>
        </div>

        <div className="server-info">

          <span>Server</span>

          <strong>
            {latest?.serverName || "Development Server"}
          </strong>

        </div>

      </header>


      {/* ERROR */}

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}


      {latest && (
        <>

          {/* METRICS */}

          <section className="metrics-grid">

            {/* CPU */}

            <div
              className={`metric-card ${getMetricStatus(
                latest.cpuUsage
              )}`}
            >

              <div className="metric-title">
                CPU Usage
              </div>

              <div className="metric-value">
                {Number(latest.cpuUsage).toFixed(2)}%
              </div>

              <div className="metric-label">
                Current CPU utilization
              </div>

            </div>


            {/* MEMORY */}

            <div
              className={`metric-card ${getMetricStatus(
                latest.memoryUsage
              )}`}
            >

              <div className="metric-title">
                Memory Usage
              </div>

              <div className="metric-value">
                {Number(latest.memoryUsage).toFixed(2)}%
              </div>

              <div className="metric-label">
                Current memory utilization
              </div>

            </div>


            {/* DISK */}

            <div
              className={`metric-card ${getMetricStatus(
                latest.diskUsage
              )}`}
            >

              <div className="metric-title">
                Disk Usage
              </div>

              <div className="metric-value">
                {Number(latest.diskUsage).toFixed(2)}%
              </div>

              <div className="metric-label">
                Current disk utilization
              </div>

            </div>


            {/* HEALTH */}

            <div
              className={`metric-card status-card ${getStatusClass(
                latest.healthStatus
              )}`}
            >

              <div className="metric-title">
                System Health
              </div>

              <div className="metric-value">
                {latest.healthStatus}
              </div>

              <div className="metric-label">
                Current system status
              </div>

            </div>

          </section>


          {/* MONITORING */}

          <section className="content-section">

            <div className="section-header">

              <h2>
                System Monitoring
              </h2>

              <span>
                Last update: {formatTime(latest.timestamp)}
              </span>

            </div>

            <div className="chart-container">

              {chartData.length > 0 ? (

                <ResponsiveContainer
                  width="100%"
                  height={430}
                >

                  <LineChart
                    data={chartData}
                    margin={{
                      top: 10,
                      right: 20,
                      left: 10,
                      bottom: 20,
                    }}
                  >

                    <CartesianGrid
                      strokeDasharray="3 3"
                    />

                    <XAxis
                      dataKey="time"
                      tick={{ fontSize: 11 }}
                      minTickGap={35}
                      angle={-20}
                      textAnchor="end"
                      height={60}
                    />

                    <YAxis
                      domain={[0, 100]}
                      tick={{ fontSize: 12 }}
                    />

                    <Tooltip />

                    <Legend />

                    <Line
                      type="monotone"
                      dataKey="CPU"
                      stroke="#2563eb"
                      strokeWidth={2}
                      dot={false}
                      activeDot={{ r: 5 }}
                    />

                    <Line
                      type="monotone"
                      dataKey="Disk"
                      stroke="#16a34a"
                      strokeWidth={2}
                      dot={false}
                      activeDot={{ r: 5 }}
                    />

                    <Line
                      type="monotone"
                      dataKey="Memory"
                      stroke="#9333ea"
                      strokeWidth={2}
                      dot={false}
                      activeDot={{ r: 5 }}
                    />

                  </LineChart>

                </ResponsiveContainer>

              ) : (

                <p className="empty-message">
                  No monitoring history available.
                </p>

              )}

            </div>

          </section>


          {/* ALERTS */}

          <section className="content-section">

            <div className="section-header">

              <h2>
                Active Alerts
              </h2>

              <span>
                {alerts.length} active
              </span>

            </div>


            {alerts.length === 0 ? (

              <div className="no-alerts">

                <span>✓</span>

                <p>
                  No active alerts
                </p>

              </div>

            ) : (

              <div className="alerts-list">

                {alerts.map((alert) => (

                  <div
                    className={`alert-item ${getStatusClass(
                      alert.severity
                    )}`}
                    key={alert.id}
                  >

                    <div className="alert-icon">
                      !
                    </div>

                    <div className="alert-details">

                      <strong>
                        {alert.type} - {alert.severity}
                      </strong>

                      <p>
                        {alert.message}
                      </p>

                      <small>
                        Value:{" "}
                        {Number(alert.value).toFixed(2)}
                        % |{" "}
                        {formatTime(alert.timestamp)}
                      </small>

                    </div>

                  </div>

                ))}

              </div>

            )}

          </section>

        </>

      )}


      {/* FOOTER */}

      <footer>

        <p>
          InfraWatch • Infrastructure Monitoring System
        </p>

        <p>
          Auto-refresh: 30 seconds
        </p>

      </footer>

    </div>
  );
}

export default App;