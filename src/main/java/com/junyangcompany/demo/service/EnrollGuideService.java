package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.EnrollGuide;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/13
 * Time:18:59
 */
public interface EnrollGuideService {
    List<EnrollGuide> fromEnrollCollegeId(Long enrollCollegeId);
}
