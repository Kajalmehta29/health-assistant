package com.health.healthassistant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "symptom_disease")
public class SymptomDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symptom;
    private String disease;
    private int confidence;

    public SymptomDisease() {}

    public SymptomDisease(String symptom, String disease, int confidence) {
        this.symptom = symptom;
        this.disease = disease;
        this.confidence = confidence;
    }

    public String getSymptom() { return symptom; }
    public String getDisease() { return disease; }
    public int getConfidence() { return confidence; }
}