package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.repository.CollegeLevelRepo;
import com.junyangcompany.demo.service.CollegeLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:9:56
 */
@Service
public class CollegeLevelServiceImpl implements CollegeLevelService {

    private final CollegeLevelRepo collegeLevelRepo;

    @Autowired
    public CollegeLevelServiceImpl(CollegeLevelRepo collegeLevelRepo) {
        this.collegeLevelRepo = collegeLevelRepo;
    }

    @Override
    public Optional<List<CollegeLevel>> findByName(List<String> names) {
        return collegeLevelRepo.findByName(names);
    }

    @Override
    public CollegeLevel save(CollegeLevel collegeLevel) {
        return collegeLevelRepo.save(collegeLevel);
    }

    @Override
    public List<CollegeLevel> saveAll(List<CollegeLevel> collegeLevels) {
        return collegeLevelRepo.saveAll(collegeLevels);
    }
}
