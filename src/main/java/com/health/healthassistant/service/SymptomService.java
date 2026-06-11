package com.health.healthassistant.service;

import com.health.healthassistant.dto.SymptomHistoryResponse;
import com.health.healthassistant.model.SymptomDisease;
import com.health.healthassistant.model.SymptomHistory;
import com.health.healthassistant.model.User;
import com.health.healthassistant.repository.SymptomDiseaseRepository;
import com.health.healthassistant.repository.SymptomHistoryRepository;
import com.health.healthassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.health.healthassistant.dto.DiseaseResponse;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Map;

@Service
public class SymptomService {
    @Autowired
    private SymptomDiseaseRepository repository;
    @Autowired
    private SymptomHistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;
    public List<DiseaseResponse> checkSymptoms(List<String> symptoms) {

        List<SymptomDisease> matches = repository.findBySymptomIn(symptoms);

        Map<String, Integer> diseaseMap = new HashMap<>();

        // Combine confidence
        for (SymptomDisease sd : matches) {
            diseaseMap.put(
                    sd.getDisease(),
                    diseaseMap.getOrDefault(sd.getDisease(), 0) + sd.getConfidence()
            );
        }

        List<DiseaseResponse> results = new ArrayList<>();

        // Convert map → list
        for (Map.Entry<String, Integer> entry : diseaseMap.entrySet()) {

            if (entry.getValue() >= 50) {
                results.add(new DiseaseResponse(entry.getKey(), entry.getValue()));
            }
        }

        //  Sort
        results.sort((a, b) -> b.getConfidence() - a.getConfidence());

        // Limit
        if (results.size() > 3) {
            results = results.subList(0, 3);
        }

        // Fallback
        if (results.isEmpty()) {
            results.add(new DiseaseResponse("No clear condition", 0));
        }

        String symptomString = String.join(",", symptoms);

        String resultString = results.stream()
                .map(r -> r.getDisease() + "(" + r.getConfidence() + ")")
                .collect(Collectors.joining(","));

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SymptomHistory history = new SymptomHistory(
                user,
                symptomString,
                resultString,
                LocalDateTime.now()
        );

        historyRepository.save(history);
        return results;


    }
    public Map<String, Integer> getTopDiseases(Long userId) {

        List<SymptomHistory> historyList = historyRepository.findByUser_Id(userId);

        Map<String, Integer> diseaseCount = new HashMap<>();

        for (SymptomHistory history : historyList) {

            String[] diseases = history.getResult().split(",");

            for (String d : diseases) {

                String diseaseName = d.split("\\(")[0]; // remove confidence

                diseaseCount.put(
                        diseaseName,
                        diseaseCount.getOrDefault(diseaseName, 0) + 1
                );
            }
        }

        return diseaseCount;
    }
    public List<SymptomHistory> getRecentHistory(Long userId) {
        return historyRepository.findTop5ByUser_IdOrderByTimestampDesc(userId);
    }
    public List<SymptomHistoryResponse> getHistoryResponse(Long userId) {

        List<SymptomHistory> historyList =
                historyRepository.findByUser_IdOrderByTimestampDesc(userId);

        return historyList.stream()
                .map(h -> new SymptomHistoryResponse(
                        h.getSymptoms(),
                        h.getResult(),
                        h.getTimestamp()
                ))
                .toList();
    }
    public List<String> getHealthInsights(Long userId) {

        Map<String, Integer> diseaseMap = getTopDiseases(userId);

        List<String> insights = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : diseaseMap.entrySet()) {

            String disease = entry.getKey();
            int count = entry.getValue();

            if (count >= 3) {
                insights.add("You frequently show symptoms of " + disease);
                insights.add("Consider taking precautions for " + disease);
            } else if (count == 2) {
                insights.add("You have recurring symptoms related to " + disease);
            } else {
                insights.add("Occasional symptoms of " + disease + " detected");
            }
        }

        if (insights.isEmpty()) {
            insights.add("No significant health patterns detected");
        }

        return insights;
    }

}