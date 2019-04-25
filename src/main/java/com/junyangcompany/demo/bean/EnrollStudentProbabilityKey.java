package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollStudentProbabilityKey {

    public Long provienceId;
    public Long sequence;
    public ScienceAndArt scienceAndArt;


}
