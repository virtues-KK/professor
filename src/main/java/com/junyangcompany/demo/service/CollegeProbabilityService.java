package com.junyangcompany.demo.service;


import com.junyangcompany.demo.bean.CollegeMajorProbability;
import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.EnrollStudentPlanProbability;
import com.junyangcompany.demo.bean.ProbabilityCountBean;
import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;

import java.util.List;

public interface CollegeProbabilityService {

    ProbabilityCountBean getCount(Long provienceId, Long sequence, ScienceAndArt scienceAndArt);

    List<CollegeProbability> getAll(Long provienceId, ScienceAndArt scienceAndArt, Long sequence, List<ChongShouBao> chongShouBaos, Boolean desc);

    List<CollegeProbability> getList(Long provienceId, ScienceAndArt scienceAndArt, Long sequence, List<Long> collegeIds, ChongShouBao chongShouBao, Boolean desc);

    List<EnrollStudentPlanProbability> getEnrollStudentPlanIdsList(Long provienceId, Long Sequence, ScienceAndArt scienceAndArt, List<CollegeMajorProbability> collegeMajorProbabilities, List<ChongShouBao> chongShouBaos, Boolean desc);
}
