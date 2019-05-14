package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollStudentPlanService {
    List<EnrollStudentPlan> getAllEnrollCollege(EnrollCollegeEnrollBatch enrollCollegeEnrollBatch);

    List<String> getEnrollMajorName(Long enrollCollegeEnrollBatchId);

    List<Long> getEnrollCollegeId(Long enrollCollegeEneollBatchId);

}
