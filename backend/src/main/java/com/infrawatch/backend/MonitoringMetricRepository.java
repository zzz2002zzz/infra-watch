package com.infrawatch.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MonitoringMetricRepository
        extends MongoRepository<MonitoringMetric, String> {

    List<MonitoringMetric> findTop20ByOrderByTimestampDesc();
}