package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import com.junyangcompany.demo.repository.EnrollMajorScoreLineRepo;
import com.junyangcompany.demo.service.EnrollMajorScoreLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:pan le
 * Date:2019/4/18
 * Time:14:24
 */
@Service
public class EnrollMajorScoreLineServiceImpl implements EnrollMajorScoreLineService {

    private final EnrollMajorScoreLineRepo enrollMajorScoreLineRepo;

    @Autowired
    public EnrollMajorScoreLineServiceImpl(EnrollMajorScoreLineRepo enrollMajorScoreLineRepo) {
        this.enrollMajorScoreLineRepo = enrollMajorScoreLineRepo;
    }

    @Override
    public List<EnrollMajorScoreLine> getAllEnrollMajorScoreLine(EnrollStudentPlan enrollStudentPlan) {
        return enrollMajorScoreLineRepo.findByEnrollStudentPlan(enrollStudentPlan);
    }


    @Override
    public Slice<List<QueryEnrollCollegeMajorBean_demo>> getMajorScoreLine(List<Long> enrollStudentId, Pageable pageable) {
        return enrollMajorScoreLineRepo.getMajorScoreLine(enrollStudentId,pageable);
    }
}
