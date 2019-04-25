package com.junyangcompany.demo.service;

import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.entity.EnrollCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface EnrollCollegeService {
    Page<EnrollCollege> getAllEnrollCollege(QueryEnrollCollegeCondition condition, Pageable pageable);
}
