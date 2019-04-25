package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.repository.EnrollCollegeRepo;
import com.junyangcompany.demo.service.EnrollCollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * author:pan le
 * Date:2019/4/17
 * Time:11:29
 */
@Service
public class EnrollCollegeServiceImpl implements EnrollCollegeService {

    @Autowired
    private EnrollCollegeRepo enrollCollegeRepo;

    @Override
    public Page<EnrollCollege> getAllEnrollCollege(QueryEnrollCollegeCondition condition, Pageable pageable) {
        return enrollCollegeRepo.queryEnrollCollege(condition,pageable);
    }
}
