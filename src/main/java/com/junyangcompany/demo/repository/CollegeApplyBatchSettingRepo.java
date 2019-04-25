package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CollegeApplyBatchSetting;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface CollegeApplyBatchSettingRepo extends JpaRepository<CollegeApplyBatchSetting, Long>, JpaSpecificationExecutor {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<CollegeApplyBatchSetting> findAllByProvinceIdAndScienceArt(Long provinceId, ScienceAndArt scienceAndArt, Sort sort);

    CollegeApplyBatchSetting findAllByProvinceIdAndScienceArtAndEnrollBatch_Id(Long provinceId, ScienceAndArt scienceAndArt, Long batchId);

}
