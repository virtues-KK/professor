package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.ProfessionalBean;
import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.bean.QueryEnrollCollegeMajorBean;
import com.junyangcompany.demo.entity.*;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import com.junyangcompany.demo.repository.EnrollCollegeScoreLineRepo;
import com.junyangcompany.demo.service.CollegeProbabilityService;
import com.junyangcompany.demo.service.EnrollCollegeService;
import com.junyangcompany.demo.service.EnrollMajorScoreLineService;
import com.junyangcompany.demo.service.EnrollStudentPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/4/16
 * Time:22:14
 */
@RestController
@RequestMapping("college")
public class CollegeQueryController {

    private final EnrollCollegeService enrollCollegeService;

    private final CollegeProbabilityService collegeProbabilityService;

    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;

    private final EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo;

    private static Map<Long, ProfessionalBean> enrollCollegeEnrollBatchMap = null;

    private final EnrollStudentPlanService enrollStudentPlanService;

    private final EnrollMajorScoreLineService enrollMajorScoreLineService;

    @Autowired
    public CollegeQueryController(CollegeProbabilityService collegeProbabilityService, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo, EnrollCollegeService enrollCollegeService, EnrollStudentPlanService enrollStudentPlanService, EnrollMajorScoreLineService enrollMajorScoreLineService) {
        this.collegeProbabilityService = collegeProbabilityService;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.enrollCollegeScoreLineRepo = enrollCollegeScoreLineRepo;
        this.enrollCollegeService = enrollCollegeService;
        this.enrollStudentPlanService = enrollStudentPlanService;
        this.enrollMajorScoreLineService = enrollMajorScoreLineService;
    }

    private Map<Long, ProfessionalBean> getEnrollCollegeEnrollBatchMap() {
        if (enrollCollegeEnrollBatchMap == null) {
            enrollCollegeEnrollBatchMap = new HashMap<>();
            List<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatches = enrollCollegeEnrollBatchRepo.findAll();
            for (EnrollCollegeEnrollBatch enrollCollegeEnrollBatch : enrollCollegeEnrollBatches) {
                String name = enrollCollegeEnrollBatch.getEnrollCollege().getName();
                String name1 = enrollCollegeEnrollBatch.getEnrollBatch().getName();
                ProfessionalBean professionalBean = new ProfessionalBean();
                professionalBean.setBatchName(name1);
                professionalBean.setCollegeName(name);
                professionalBean.setCollegeCode(enrollCollegeEnrollBatch.getEnrollCollege().getCode());
                EnrollCollege enrollCollege = enrollCollegeEnrollBatch.getEnrollCollege();
                if (enrollCollege.getCollegeType() != null)
                    professionalBean.setType(enrollCollege.getCollegeType().getName());
                professionalBean.setRank(enrollCollege.getNetRank());
                if (enrollCollege.getCollegeLevel() != null)
                    professionalBean.setLevels(enrollCollege.getCollegeLevel().stream().map(CollegeLevel::getName).collect(Collectors.toList()));
                enrollCollegeEnrollBatchMap.put(enrollCollegeEnrollBatch.getId(), professionalBean);
            }
        }
        return enrollCollegeEnrollBatchMap;
    }

    @PostMapping(
            value = "/professionCollege"
    )
    public ResponseEntity<List<ProfessionalBean>> professionCollege(@RequestBody ProfessionalBean collegeCondition,Sort sort) {
        List<ProfessionalBean> professionalBeans = new ArrayList<>();
        List<CollegeProbability> collegeProbabilities = collegeProbabilityService.getAll(collegeCondition.getProvinceId(), collegeCondition.getScienceAndArt() ? ScienceAndArt.理科 : ScienceAndArt.文科, collegeCondition.getSeq(), null, false);

        for (CollegeProbability collegeProbability : collegeProbabilities) {
            ProfessionalBean professionalBean = new ProfessionalBean();
            professionalBeans.add(professionalBean);
            Long bid = collegeProbability.getCollegeId();
            professionalBean.setEnrollCollegeEnrollBatch(EnrollCollegeEnrollBatch.builder().id(bid).build());
            professionalBean.setProbability(collegeProbability.getProbalility());
            ProfessionalBean professionalBean1 = getEnrollCollegeEnrollBatchMap().get(bid);
            //professionalBean.setEnrollCollegeEnrollBatch(enrollCollegeEnrollBatch);
            professionalBean.setBatchName(professionalBean1.getBatchName());
            professionalBean.setCollegeName(professionalBean1.getCollegeName());
            professionalBean.setCollegeCode(professionalBean1.getCollegeCode());
            List<EnrollCollegeScoreLine> enrollCollegeScoreLines = enrollCollegeScoreLineRepo.findByyearAndEnrollCollegeEnrollBatch(collegeCondition.getScienceAndArt() ? ScienceAndArt.理科 : ScienceAndArt.文科,
                    bid, 2015);
            List<ProfessionalBean.CollegeLine> collegeLines = new ArrayList<>();
            for (EnrollCollegeScoreLine enrollCollegeScoreLine : enrollCollegeScoreLines) {
                ProfessionalBean.CollegeLine collegeLine = professionalBean.new CollegeLine();
                collegeLine.enrollCount = enrollCollegeScoreLine.getEnrollCount();
                collegeLine.minRank = enrollCollegeScoreLine.getMinRank();
                collegeLine.minScore = enrollCollegeScoreLine.getMinScore();
                collegeLine.year = enrollCollegeScoreLine.getYear();
                collegeLines.add(collegeLine);
            }
            professionalBean.setCollegeLines(collegeLines);

            professionalBean.setType(professionalBean1.getType());
            professionalBean.setRank(professionalBean1.getRank());
            professionalBean.setLevels(professionalBean1.getLevels());
            professionalBean.setProvinceId(collegeCondition.getProvinceId());
            professionalBean.setScienceAndArt(collegeCondition.getScienceAndArt());
            professionalBean.setSeq(collegeCondition.getSeq());
        }
        if (collegeCondition.getType() != null && !collegeCondition.getType().equals("")) {
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getType().equals(collegeCondition.getType())
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getBatchName() != null && !collegeCondition.getBatchName().equals("")) {
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getBatchName().equals(collegeCondition.getBatchName())
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getLevels() != null && collegeCondition.getLevels().size() !=0 ){
            professionalBeans = professionalBeans.stream().filter(
                    professionalBean ->
                            professionalBean.getLevels().size() != 0 &&
                                    professionalBean.getLevels().containsAll(collegeCondition.getLevels())
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxRank() != null ){
            professionalBeans = professionalBeans.stream().filter(professionalBean -> collegeCondition.getMaxRank() > professionalBean.getRank() && collegeCondition.getMinRank() < professionalBean.getRank()
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxProbability() !=null){
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getProbability() < collegeCondition.getMaxProbability()
                    && professionalBean.getProbability() > collegeCondition.getMinProbability()
                    ).collect(Collectors.toList());
        }
        Collections.sort(professionalBeans);
        return ResponseEntity.ok(professionalBeans);
    }

    @PostMapping(
            value = "queryEnrollCollegeByCondition"
    )
    public Page<EnrollCollege> queryEnrollCollege(QueryEnrollCollegeCondition condition, Pageable pageable) {
        return enrollCollegeService.getAllEnrollCollege(condition, pageable);
    }

    @PostMapping("majors")
    public Page<List<List<QueryEnrollCollegeMajorBean>>> getAllMajors(@RequestBody List<EnrollCollegeEnrollBatch> list, Pageable pageable){
        List<List<QueryEnrollCollegeMajorBean>> lists = new ArrayList<>();
        List<QueryEnrollCollegeMajorBean> queryEnrollCollegeMajorBeans = new ArrayList<>();
        QueryEnrollCollegeMajorBean queryEnrollCollegeMajorBean = new QueryEnrollCollegeMajorBean();
        list.forEach(enrollCollegeEnrollBatch -> {
            List<EnrollStudentPlan> allEnrollCollege = enrollStudentPlanService.getAllEnrollCollege(enrollCollegeEnrollBatch);
            allEnrollCollege.forEach(enrollStudentPlan -> {
                BeanUtils.copyProperties(enrollStudentPlan,queryEnrollCollegeMajorBean);
                enrollMajorScoreLineService.getAllEnrollMajorScoreLine(enrollStudentPlan).forEach(enrollMajorScoreLine -> {
                    BeanUtils.copyProperties(enrollMajorScoreLine,queryEnrollCollegeMajorBean);
                    queryEnrollCollegeMajorBeans.add(queryEnrollCollegeMajorBean);
                });
            });
            lists.add(queryEnrollCollegeMajorBeans);
        });
        PageImpl<List<List<QueryEnrollCollegeMajorBean>>> lists1 = new PageImpl(lists, pageable, lists.size());
        return lists1;
    }
}
