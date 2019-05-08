package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.CollegeLevel;

import java.util.List;
import java.util.Optional;

public interface CollegeLevelService {
    Optional<List<CollegeLevel>> findByName(List<String> names);
    CollegeLevel save(CollegeLevel collegeLevel);
    List<CollegeLevel> saveAll(List<CollegeLevel> collegeLevels);
}
