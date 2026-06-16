package com.health.healthassistant.controller;

import com.health.healthassistant.dto.ApiResponse;
import com.health.healthassistant.dto.SymptomHistoryResponse;
import com.health.healthassistant.dto.SymptomRequest;
import com.health.healthassistant.dto.DiseaseResponse;
import com.health.healthassistant.model.SymptomHistory;
import com.health.healthassistant.service.SymptomService;
import com.health.healthassistant.repository.SymptomHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<DiseaseResponse>>> checkSymptoms(
            @RequestBody SymptomRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Symptoms analyzed successfully",
                        symptomService.checkSymptoms(request.getSymptoms())
                )
        );
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<List<SymptomHistoryResponse>>> getHistory(@PathVariable Long userId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "History fetched successfully",
                        symptomService.getHistoryResponse(userId)
                )
        );
    }
    @GetMapping("/top-diseases/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> getTopDiseases(@PathVariable Long userId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Top diseases fetched",
                        symptomService.getTopDiseases(userId)
                )
        );
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
    public ResponseEntity<ApiResponse<List<String>>> getSuggestions(@PathVariable Long userId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Suggestions fetched",
                        symptomService.getPreventiveSuggestions(userId)
                )
        );
    }
    @GetMapping("/history/{userId}/paged")
    public ResponseEntity<ApiResponse<Page<SymptomHistoryResponse>>> getPagedHistory(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "History fetched successfully",
                        symptomService.getPaginatedHistory(userId, page, size)
                )
        );
    }
}