package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollStudentPlanService {
    List<EnrollStudentPlan> getAllEnrollCollege(EnrollCollegeEnrollBatch enrollCollegeEnrollBatch);
}
