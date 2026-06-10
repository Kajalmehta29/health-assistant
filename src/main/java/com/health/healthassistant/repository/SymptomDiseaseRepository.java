package com.health.healthassistant.repository;

import com.health.healthassistant.model.SymptomDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomDiseaseRepository extends JpaRepository<SymptomDisease, Long> {

    List<SymptomDisease> findBySymptomIn(List<String> symptoms);
}