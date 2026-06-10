package com.health.healthassistant.controller;

import com.health.healthassistant.dto.SymptomRequest;
import com.health.healthassistant.dto.ApiResponse;
import com.health.healthassistant.service.SymptomService;
import com.health.healthassistant.dto.DiseaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @PostMapping("/check")
    public List<DiseaseResponse> checkSymptoms(@RequestBody SymptomRequest request){

        return symptomService.checkSymptoms(request.getSymptoms());


    }
}