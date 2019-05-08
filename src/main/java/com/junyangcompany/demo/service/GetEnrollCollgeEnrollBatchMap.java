package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * author:pan le
 * Date:2019/5/8
 * Time:10:38
 */
@Component
public class GetEnrollCollgeEnrollBatchMap implements CommandLineRunner  {

    private final CollegeLevelService collegeLevelService;

    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;

    private static Map<Long, ProfessionalEntity> enrollCollegeEnrollBatchMap = null;

    private final professionalBeanService professionalBeanService;

    @Autowired
    public GetEnrollCollgeEnrollBatchMap(CollegeLevelService collegeLevelService, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, professionalBeanService professionalBeanService) {
        this.collegeLevelService = collegeLevelService;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.professionalBeanService = professionalBeanService;
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
