package com.health.healthassistant.dto;

public class DiseaseResponse {

    private String disease;
    private int confidence;

    public DiseaseResponse(String disease, int confidence) {
        this.disease = disease;
        this.confidence = confidence;
    }

    public String getDisease() {
        return disease;
    }

    public int getConfidence() {
        return confidence;
    }
}