package com.health.healthassistant.dto;

import java.time.LocalDateTime;

public class SymptomHistoryResponse {

    private String symptoms;
    private String result;
    private LocalDateTime timestamp;

    public SymptomHistoryResponse(String symptoms, String result, LocalDateTime timestamp) {
        this.symptoms = symptoms;
        this.result = result;
        this.timestamp = timestamp;
    }

    public String getSymptoms() { return symptoms; }
    public String getResult() { return result; }
    public LocalDateTime getTimestamp() { return timestamp; }
}