package com.health.healthassistant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.health.healthassistant.model.User;
@Entity
@Table(name = "symptom_history")
public class SymptomHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String symptoms;

    private String result;

    private LocalDateTime timestamp;

    public SymptomHistory() {}

    public SymptomHistory(User user, String symptoms, String result, LocalDateTime timestamp) {
        this.user = user;
        this.symptoms = symptoms;
        this.result = result;
        this.timestamp = timestamp;
    }

    // Getters
    public User getUser() { return user; }
    public String getSymptoms() { return symptoms; }
    public String getResult() { return result; }
    public LocalDateTime getTimestamp() { return timestamp; }
}