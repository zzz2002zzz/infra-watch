package com.infrawatch.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends MongoRepository<Alert, String> {

    Optional<Alert> findFirstByServerNameAndTypeAndActiveTrue(
            String serverName,
            String type
    );

    List<Alert> findByActiveTrueOrderByTimestampDesc();
}