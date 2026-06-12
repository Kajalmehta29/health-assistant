package com.health.healthassistant.controller;

import com.health.healthassistant.dto.SymptomHistoryResponse;
import com.health.healthassistant.dto.SymptomRequest;
import com.health.healthassistant.dto.DiseaseResponse;
import com.health.healthassistant.model.SymptomHistory;
import com.health.healthassistant.service.SymptomService;
import com.health.healthassistant.repository.SymptomHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private SymptomHistoryRepository historyRepository;

    @PostMapping("/check")
    public List<DiseaseResponse> checkSymptoms(@RequestBody SymptomRequest request) {
        return symptomService.checkSymptoms(request.getSymptoms());
    }

    @GetMapping("/history/{userId}")
    public List<SymptomHistoryResponse> getHistory(@PathVariable Long userId) {
        return symptomService.getHistoryResponse(userId);
    }
    @GetMapping("/top-diseases/{userId}")
    public Map<String, Integer> getTopDiseases(@PathVariable Long userId) {
        return symptomService.getTopDiseases(userId);
    }
    @GetMapping("/recent/{userId}")
    public List<SymptomHistory> getRecentHistory(@PathVariable Long userId) {
        return symptomService.getRecentHistory(userId);
    }
    @GetMapping("/insights/{userId}")
    public List<String> getInsights(@PathVariable Long userId) {
        return symptomService.getHealthInsights(userId);
    }
    @GetMapping("/suggestions/{userId}")
    public List<String> getSuggestions(@PathVariable Long userId) {
        return symptomService.getPreventiveSuggestions(userId);
    }
    @GetMapping("/history/{userId}/paged")
    public Page<SymptomHistoryResponse> getPagedHistory(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size) {

        return symptomService.getPaginatedHistory(userId, page, size);
    }
}