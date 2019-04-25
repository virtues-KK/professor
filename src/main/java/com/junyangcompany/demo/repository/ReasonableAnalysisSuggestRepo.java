package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.ReasonableAnalysisSuggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReasonableAnalysisSuggestRepo extends JpaRepository<ReasonableAnalysisSuggest, Long>, JpaSpecificationExecutor {
    ReasonableAnalysisSuggest findByUserIdAndEnrollBatch_Id(Long userId, Long batchId);

    List<ReasonableAnalysisSuggest> findByUserId(Long userId);
}
