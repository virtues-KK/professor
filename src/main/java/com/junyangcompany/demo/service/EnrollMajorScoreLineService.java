package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollMajorScoreLineService {
    List<EnrollMajorScoreLine> getAllEnrollMajorScoreLine(EnrollStudentPlan enrollStudentPlan);

    Page<List<QueryEnrollCollegeMajorBean_demo>> getMajorScoreLine(List<Long> enrollStudentId, Pageable pageable);

}
