package com.infrawatch.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "servers")
public class Server {

    @Id
    private String id;

    private String name;
    private String host;
    private String environment;
    private String status;

    public Server() {
    }

    public Server(String name, String host, String environment, String status) {
        this.name = name;
        this.host = host;
        this.environment = environment;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}