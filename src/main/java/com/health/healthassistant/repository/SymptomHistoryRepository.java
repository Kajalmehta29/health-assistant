package com.health.healthassistant.repository;

import com.health.healthassistant.model.SymptomHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SymptomHistoryRepository extends JpaRepository<SymptomHistory, Long> {

    List<SymptomHistory> findByUser_Id(Long userId);
    List<SymptomHistory> findTop5ByUser_IdOrderByTimestampDesc(Long userId);
    List<SymptomHistory> findByUser_IdOrderByTimestampDesc(Long userId);
    Page<SymptomHistory> findByUser_Id(Long userId, Pageable pageable);
}