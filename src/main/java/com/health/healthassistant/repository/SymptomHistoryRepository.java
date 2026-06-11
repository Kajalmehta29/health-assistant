package com.health.healthassistant.repository;

import com.health.healthassistant.model.SymptomHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomHistoryRepository extends JpaRepository<SymptomHistory, Long> {

    List<SymptomHistory> findByUser_Id(Long userId);
    List<SymptomHistory> findTop5ByUser_IdOrderByTimestampDesc(Long userId);
    List<SymptomHistory> findByUser_IdOrderByTimestampDesc(Long userId);
}