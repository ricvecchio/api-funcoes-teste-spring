package com.funcoes.logging;

import java.time.LocalDateTime;

public class LogEntry {

    private String level;
    private String message;
    private String serviceName;
    private String action;
    private LocalDateTime timestamp;

    public LogEntry() {
        this.timestamp = LocalDateTime.now();
    }

    public LogEntry(String level, String message, String serviceName, String action) {
        this.level = level;
        this.message = message;
        this.serviceName = serviceName;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) [%s]",
                level, message, serviceName, action, timestamp);
    }
}
