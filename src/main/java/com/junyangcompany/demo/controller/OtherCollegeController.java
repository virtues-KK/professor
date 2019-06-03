package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.request.FirstChoice;
import com.junyangcompany.demo.bean.response.CollegeLine;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollCollegeScoreLine;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.repository.*;
import com.junyangcompany.demo.service.CollegeProbabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/5/30
 * Time:10:14
 */
@RestController
@RequestMapping("otherCollege")
public class OtherCollegeController {

    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;
    private final ExamineeRepo examineeRepo;
    private final CollegeProbabilityService collegeProbabilityService;
    private final EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo;
    private final EnrollCollegeRepo enrollCollegeRepo;
    private final EnrollBatchRepo enrollBatchRepo;
    private final CollegeTypeRepo collegeTypeRepo;

    @Autowired
    public OtherCollegeController(EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, ExamineeRepo examineeRepo, CollegeProbabilityService collegeProbabilityService, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo, EnrollCollegeRepo enrollCollegeRepo, EnrollBatchRepo enrollBatchRepo, CollegeTypeRepo collegeTypeRepo) {
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.examineeRepo = examineeRepo;
        this.collegeProbabilityService = collegeProbabilityService;
        this.enrollCollegeScoreLineRepo = enrollCollegeScoreLineRepo;
        this.enrollCollegeRepo = enrollCollegeRepo;
        this.enrollBatchRepo = enrollBatchRepo;
        this.collegeTypeRepo = collegeTypeRepo;
    }

    @GetMapping("findCollege")
    public List<FirstChoice> getOtherCollege(String enrollCollegeName,Long examineeId){
        Optional<Examinee> byId = examineeRepo.findById(examineeId);
        if (!byId.isPresent()){
            throw new RuntimeException("考生id不存在");
        }
        Long provinceId = byId.get().getProvinceId();
        ScienceAndArt scienceAndArt = byId.get().getScienceAndArt();
        Long weiCi = byId.get().getWeiCi();
        List<Long> enrollCollegeEnrollBatchIds = enrollCollegeEnrollBatchRepo.findIdByEnrollCollegeName1(enrollCollegeName, provinceId);
//        List<CollegeProbability> list = collegeProbabilityService.getList(provinceId, scienceAndArt, weiCi, enrollCollegeEnrollBatchIds, null, true);
//        List<FirstChoice> firstChoices = new ArrayList<>();
//        list.forEach(collegeProbability -> {
//            FirstChoice firstChoice = new FirstChoice();
//            firstChoices.add(firstChoice);
//            // scoreLine,province,city,collegeName,collegeCode
//            // collegeLine
//            Long enrollCollegeEnrollBatchId = collegeProbability.getCollegeId();
        List<FirstChoice> firstChoices = new ArrayList<>();
        for (Long enrollCollegeEnrollBatchId : enrollCollegeEnrollBatchIds) {
            FirstChoice firstChoice = new FirstChoice();
            firstChoices.add(firstChoice);
            List<EnrollCollegeScoreLine> enrollCollegeScoreLines = enrollCollegeScoreLineRepo.findByEnrollCollegeEnrollBatchAndProvinceAndScienceArt(enrollCollegeEnrollBatchId, provinceId, scienceAndArt);
            List<CollegeLine> CollegeLines = new ArrayList<>();
            firstChoice.setCollegeLines(CollegeLines);
            for (EnrollCollegeScoreLine enrollCollegeScoreLine : enrollCollegeScoreLines) {
                CollegeLine collegeLine = CollegeLine.builder().batchName(enrollCollegeScoreLine.getEnrollBatch().getName())
                        .enrollCount(enrollCollegeScoreLine.getEnrollCount())
                        .minRank(enrollCollegeScoreLine.getMinRank())
                        .minScore(enrollCollegeScoreLine.getMinScore())
                        .year(enrollCollegeScoreLine.getYear()).build();
                CollegeLines.add(collegeLine);
            }
            Optional<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatchOptional = enrollCollegeEnrollBatchRepo.findById(enrollCollegeEnrollBatchId);
            if (enrollCollegeEnrollBatchOptional.isPresent()) {
                EnrollCollegeEnrollBatch enrollCollegeEnrollBatch = enrollCollegeEnrollBatchOptional.get();
                Optional<EnrollCollege> enrollCollegeOptional = enrollCollegeRepo.findById(enrollCollegeEnrollBatch.getEnrollCollege().getId());
                if (enrollCollegeOptional.isPresent()) {
                    EnrollCollege enrollCollege = enrollCollegeOptional.get();
                    firstChoice.setCollegeProvince(enrollCollege.getProvince().getName());
                    firstChoice.setCity(enrollCollege.getCity());
                    firstChoice.setCollegeName(enrollCollege.getName());
//                    firstChoice.setProbability(collegeProbability.getProbalility());
                    String batchName = enrollCollegeEnrollBatch.getEnrollBatch().getName();
//                    String batchName = enrollBatchRepo.findById(collegeProbability.getBatchId()).get().getName();
                    firstChoice.setBatchName(batchName);
                    List<String> collegeLever = enrollCollege.getCollegeLevel().stream().map(CollegeLevel::getName).collect(Collectors.toList());
                    firstChoice.setLevels(collegeLever);
                    firstChoice.setCollegeCode(enrollCollege.getCode());
                    if (Objects.nonNull(enrollCollege.getCollegeType()) && Objects.nonNull(enrollCollege.getCollegeType().getName()))
                    firstChoice.setType(enrollCollege.getCollegeType().getName());
                    firstChoice.setRank(enrollCollege.getAppRank());
                }
            }
        }
        return firstChoices;
    }
}
