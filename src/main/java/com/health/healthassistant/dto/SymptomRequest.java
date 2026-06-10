package com.health.healthassistant.dto;

import java.util.List;

public class SymptomRequest {

    private List<String> symptoms;

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }
}