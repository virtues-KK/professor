package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.CollegeLine;
import com.junyangcompany.demo.repository.CollegeLineRepo;
import com.junyangcompany.demo.service.CollegeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:10:21
 */
@Service
public class CollegeLineServiceImpl implements CollegeLineService {

    private final CollegeLineRepo collegeLineRepo;

    @Autowired
    public CollegeLineServiceImpl(CollegeLineRepo collegeLineRepo) {
        this.collegeLineRepo = collegeLineRepo;
    }

    @Override
    public CollegeLine save(CollegeLine collegeLine) {
        return collegeLineRepo.save(collegeLine);
    }

    @Override
    public List<CollegeLine> saveAll(List<CollegeLine> collegeLines) {
        return collegeLineRepo.saveAll(collegeLines);
    }
}
