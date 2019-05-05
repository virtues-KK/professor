package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import com.junyangcompany.demo.repository.EnrollStudentPlanRepo;
import com.junyangcompany.demo.service.EnrollStudentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:pan le
 * Date:2019/4/18
 * Time:10:39
 */
@Service
public class EnrollStudentPlanServiceImpl implements EnrollStudentPlanService {

    @Autowired
    private EnrollStudentPlanRepo enrollStudentPlanRepo;

    @Override
    public List<EnrollStudentPlan> getAllEnrollCollege(EnrollCollegeEnrollBatch enrollCollegeEnrollBatch) {
        return enrollStudentPlanRepo.findAllByEnrollCollegeEnrollBatch(enrollCollegeEnrollBatch);
    }

    @Override
    public List<String> getEnrollMajorName(Long enrollCollegeEnrollBatchId) {
        return enrollStudentPlanRepo.getEnrollStudentPlanNameByEnrollCollegeEnrollBatch(enrollCollegeEnrollBatchId);
    }
}
