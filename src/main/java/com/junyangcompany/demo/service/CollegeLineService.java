package com.junyangcompany.demo.service;


import com.junyangcompany.demo.entity.professerEntity.CollegeLine;

import java.util.List;
import java.util.Optional;

public interface CollegeLineService {
    CollegeLine save(CollegeLine collegeLine);
    List<CollegeLine> saveAll(List<CollegeLine> collegeLines);

}
