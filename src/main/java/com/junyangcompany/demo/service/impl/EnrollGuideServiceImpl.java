package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.EnrollGuide;
import com.junyangcompany.demo.repository.EnrollGuideRepo;
import com.junyangcompany.demo.service.EnrollGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/13
 * Time:19:00
 */
@Service
public class EnrollGuideServiceImpl implements EnrollGuideService {

    private final EnrollGuideRepo enrollGuideRepo;

    @Autowired
    public EnrollGuideServiceImpl(EnrollGuideRepo enrollGuideRepo) {
        this.enrollGuideRepo = enrollGuideRepo;
    }

    @Override
    public List<EnrollGuide> fromEnrollCollegeId(Long enrollCollegeId) {
        return enrollGuideRepo.findByEnrollCollegeId(enrollCollegeId);
    }
}
