package com.health.healthassistant.service;

import com.health.healthassistant.model.SymptomDisease;
import com.health.healthassistant.repository.SymptomDiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.health.healthassistant.dto.DiseaseResponse;

import java.util.ArrayList;
import java.util.Map;

@Service
public class SymptomService {
    @Autowired
    private SymptomDiseaseRepository repository;

    public List<DiseaseResponse> checkSymptoms(List<String> symptoms) {

        List<SymptomDisease> matches = repository.findBySymptomIn(symptoms);

        Map<String, Integer> diseaseMap = new HashMap<>();

        // ✅ Combine confidence
        for (SymptomDisease sd : matches) {
            diseaseMap.put(
                    sd.getDisease(),
                    diseaseMap.getOrDefault(sd.getDisease(), 0) + sd.getConfidence()
            );
        }

        List<DiseaseResponse> results = new ArrayList<>();

        // ✅ Convert map → list
        for (Map.Entry<String, Integer> entry : diseaseMap.entrySet()) {

            if (entry.getValue() >= 50) {
                results.add(new DiseaseResponse(entry.getKey(), entry.getValue()));
            }
        }

        // ✅ Sort
        results.sort((a, b) -> b.getConfidence() - a.getConfidence());

        // ✅ Limit
        if (results.size() > 3) {
            results = results.subList(0, 3);
        }

        // ✅ Fallback
        if (results.isEmpty()) {
            results.add(new DiseaseResponse("No clear condition", 0));
        }

        return results;
    }
}