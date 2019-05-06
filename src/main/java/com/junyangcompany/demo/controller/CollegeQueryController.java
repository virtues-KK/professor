package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.bean.response.FirstChoice;
import com.junyangcompany.demo.entity.*;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.CollegeLine;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalBean;
import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import com.junyangcompany.demo.repository.EnrollCollegeScoreLineRepo;
import com.junyangcompany.demo.repository.ProfessionalBeanRepo;
import com.junyangcompany.demo.service.*;
import com.junyangcompany.demo.utils.pageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/4/16
 * Time:22:14
 */
@Slf4j
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

    private final professionalBeanService professionalBeanService;

    private final ExamineeService examineeService;

    @Autowired
    public CollegeQueryController(CollegeProbabilityService collegeProbabilityService, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo, EnrollCollegeService enrollCollegeService, EnrollStudentPlanService enrollStudentPlanService, EnrollMajorScoreLineService enrollMajorScoreLineService, professionalBeanService professionalBeanService, ExamineeService examineeService) {
        this.collegeProbabilityService = collegeProbabilityService;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.enrollCollegeScoreLineRepo = enrollCollegeScoreLineRepo;
        this.enrollCollegeService = enrollCollegeService;
        this.enrollStudentPlanService = enrollStudentPlanService;
        this.enrollMajorScoreLineService = enrollMajorScoreLineService;
        this.professionalBeanService = professionalBeanService;
        this.examineeService = examineeService;
    }

    private Map<Long, ProfessionalBean> getEnrollCollegeEnrollBatchMap() {
        if (enrollCollegeEnrollBatchMap == null) {
            enrollCollegeEnrollBatchMap = new HashMap<>();
            List<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatches = enrollCollegeEnrollBatchRepo.findAll();
            for (EnrollCollegeEnrollBatch enrollCollegeEnrollBatch : enrollCollegeEnrollBatches) {
                String name = enrollCollegeEnrollBatch.getEnrollCollege().getName();
                ArrayList<EnrollBatch> strings = new ArrayList<>();
                strings.add(enrollCollegeEnrollBatch.getEnrollBatch());
                ProfessionalBean professionalBean = new ProfessionalBean();
                professionalBean.setBatchNames(strings);
                professionalBean.setCollegeName(name);
                professionalBean.setCollegeCode(enrollCollegeEnrollBatch.getEnrollCollege().getCode());
                EnrollCollege enrollCollege = enrollCollegeEnrollBatch.getEnrollCollege();
                if (enrollCollege.getCollegeType() != null)
                    professionalBean.setType(enrollCollege.getCollegeType().getName());
                professionalBean.setRank(enrollCollege.getNetRank());
                if (enrollCollege.getCollegeLevel() != null)
                    professionalBean.setLevels(enrollCollege.getCollegeLevel());
                enrollCollegeEnrollBatchMap.put(enrollCollegeEnrollBatch.getId(), professionalBean);
            }
        }
        return enrollCollegeEnrollBatchMap;
    }

    /**
     * 定义全局静态返回大学数据
     */
    private static List<ProfessionalBean> professionalBeans_static = new ArrayList<>();

    @PostMapping("search")
    public List<ProfessionalBean> searchCollege(@RequestBody ProfessionalBean professionalBean_) {
        log.info(String.valueOf(professionalBeans_static.size()));
        return professionalBeans_static.stream().filter(professionalBean ->
                professionalBean.getCollegeName().contains(professionalBean_.getCollegeName())
        ).collect(Collectors.toList());
    }


    @PostMapping(
            value = "/professionCollege"
    )
//    public Page<List<ProfessionalBean>> professionCollege(@RequestBody ProfessionalBean collegeCondition, Pageable pageable) {
    public Page<List<ProfessionalBean>> professionCollege(@RequestBody ProfessionalBean collegeCondition, Pageable pageable) {
        List<ProfessionalBean> professionalBeans = new ArrayList<>();
        List<CollegeProbability> collegeProbabilities = collegeProbabilityService.getAll(collegeCondition.getProvinceId(),
                collegeCondition.getScienceAndArt(), collegeCondition.getSeq(), null, false);
        for (CollegeProbability collegeProbability : collegeProbabilities) {
            ProfessionalBean professionalBean = new ProfessionalBean();
            professionalBeans.add(professionalBean);
            Long bid = collegeProbability.getCollegeId();
            professionalBean.setEnrollCollegeEnrollBatch(bid);
            professionalBean.setProbability(collegeProbability.getProbalility());
            ProfessionalBean professionalBean1 = getEnrollCollegeEnrollBatchMap().get(bid);
            //professionalBean.setEnrollCollegeEnrollBatch(enrollCollegeEnrollBatch);
            professionalBean.setBatchNames(professionalBean1.getBatchNames());
            professionalBean.setCollegeName(professionalBean1.getCollegeName());
            professionalBean.setCollegeCode(professionalBean1.getCollegeCode());
            List<EnrollCollegeScoreLine> enrollCollegeScoreLines = enrollCollegeScoreLineRepo.findByyearAndEnrollCollegeEnrollBatch(collegeCondition.getScienceAndArt(),
                    bid, 2015);
            List<CollegeLine> collegeLines = new ArrayList<>();
            for (EnrollCollegeScoreLine enrollCollegeScoreLine : enrollCollegeScoreLines) {
                CollegeLine collegeLine = new CollegeLine();
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
            professionalBean.setExaminee(collegeCondition.getExaminee());
        }
        professionalBeans_static = professionalBeans;
        if (collegeCondition.getType() != null && !collegeCondition.getType().equals("")) {
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getType().equals(collegeCondition.getType())
            ).collect(Collectors.toList());
        }

        // 在选择批次的时候,不能做比较,只能确定某些批次来筛选
        if (collegeCondition.getBatchNames().size() != 0) {
            professionalBeans.stream().forEach(professionalBean -> {
                List<String> collect = professionalBean.getBatchNames().stream().map(EnrollBatch::getName).collect(Collectors.toList());
                log.info(String.valueOf(collect.size()));
                collect.forEach(c->{
                    log.info(c);
                });
            });
            collegeCondition.getBatchNames().stream().map(EnrollBatch::getName).collect(Collectors.toList()).forEach(c->{
                System.out.println(c);
            });
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                            collegeCondition.getBatchNames().stream().map(EnrollBatch::getName).collect(Collectors.toList())
                            .containsAll(professionalBean.getBatchNames().stream().map(EnrollBatch::getName).collect(Collectors.toList()))
            ).collect(Collectors.toList());
        }
        //turn list<String> to list<collegeLevel>
        if (collegeCondition.getLevels() != null && collegeCondition.getLevels().size() != 0) {
            List<String> condition_levels = collegeCondition.getLevels().stream().map(CollegeLevel::getName).collect(Collectors.toList());
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    collegeCondition.getLevels().size() != 0 &&
                            professionalBean.getLevels().stream().map(CollegeLevel::getName).collect(Collectors.toList()).containsAll(condition_levels)
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxRank() != null) {
            professionalBeans = professionalBeans.stream().filter(professionalBean -> collegeCondition.getMaxRank() > professionalBean.getRank() && collegeCondition.getMinRank() < professionalBean.getRank()
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxProbability() != null) {
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getProbability() <= collegeCondition.getMaxProbability()
                            && professionalBean.getProbability() >= 0
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMinProbability() != null){
            professionalBeans = professionalBeans.stream().filter(professionalBean ->
                    professionalBean.getProbability() >= collegeCondition.getMinProbability() &&
                            professionalBean.getProbability() <= 100
            ).collect(Collectors.toList());
        }
        // put in FirstChoice
        List<FirstChoice> firstChoices = new ArrayList<>();
        professionalBeans.forEach(professionalBean -> {
            FirstChoice firstChoice = new FirstChoice();
            List<com.junyangcompany.demo.bean.response.CollegeLine> collegeLines = new ArrayList<>();
            BeanUtils.copyProperties(professionalBean,firstChoice);
            firstChoice.setBatchName(professionalBean.getBatchNames().get(0).getName());
            professionalBean.getCollegeLines().forEach(collegeLine1 -> {
                com.junyangcompany.demo.bean.response.CollegeLine collegeLine = new com.junyangcompany.demo.bean.response.CollegeLine();
                collegeLine.setMinRank(collegeLine1.getMinRank());
                collegeLine.setMinScore(collegeLine1.getMinScore());
                collegeLine.setEnrollCount(collegeLine1.getEnrollCount());
                collegeLine.setYear(collegeLine1.getYear());
                collegeLines.add(collegeLine);
            });
            firstChoice.setCollegeLines(collegeLines);
            List<String> collegeLevels = new ArrayList<>();
            professionalBean.getLevels().forEach(l->{
                collegeLevels.add(l.getName());
            });
            firstChoice.setLevels(collegeLevels);
            firstChoices.add(firstChoice);
        });
        List<FirstChoice> data = pageUtils.getPageSizeDataForRelations(firstChoices, pageable.getPageSize(), pageable.getPageNumber());
        if (data == null || data.isEmpty()){
            return null;
        }
        PageRequest of = PageRequest.of(pageable.getPageNumber(), firstChoices.size());
        Page page = new PageImpl(data,of,data.size());
        return page;
    }

    /**
     *  保存初选结果
     * @param firstChoices
     * @return
     */
    @PutMapping
    public List<ProfessionalBean> saveFirstChoiceResult(@RequestBody List<FirstChoice> firstChoices){
        List<ProfessionalBean> professionalBeans = new ArrayList<>();
        firstChoices.forEach(firstChoice -> {
            ProfessionalBean professionalBean = new ProfessionalBean();
            professionalBeans.add(professionalBean);
            BeanUtils.copyProperties(firstChoice,professionalBean);
            firstChoice.getCollegeLines().forEach(collegeLine -> {
                CollegeLine collegeLine1 = CollegeLine.builder()
                        .minRank(collegeLine.getMinRank())
                        .minScore(collegeLine.getMinScore())
                        .enrollCount(collegeLine.getEnrollCount())
                        .year(collegeLine.getYear()).build();
            });
            professionalBean.setBatchNames(new ArrayList<EnrollBatch>(Arrays.asList(EnrollBatch.builder().name(firstChoice.getBatchName()).build())));
            professionalBean.setExaminee(Examinee.builder().id(firstChoice.getExamineeId()).build());
            List<CollegeLevel> collegeLevels = new ArrayList<>();
            firstChoice.getLevels().forEach(collegeLevel->{
                collegeLevels.add(CollegeLevel.builder().name(collegeLevel).build());
            });
            professionalBean.setLevels(collegeLevels);
        });
        return professionalBeanService.saveAll(professionalBeans);
    }

    /**
     * 根据examinee查询初选结果
     */
    @GetMapping("searchByExaminee")
    public List<ProfessionalBean> searchByExaminee(Long examineeId){
//        return professionalBeanService.searchByExamineeId(examineeId);
        if (examineeService.getExaminee(examineeId).isPresent()){
            Examinee examinee = examineeService.getExaminee(examineeId).get();
            List<ProfessionalBean> professionalBeans = examinee.getProfessionalBeans();
            return professionalBeans;
        }
        return null;
    }


    /**
     * @param condition
     * @param pageable
     * @return
     */
    @PostMapping(
            value = "queryEnrollCollegeByCondition"
    )
    public Page<EnrollCollege> queryEnrollCollege(QueryEnrollCollegeCondition condition, Pageable pageable) {
        return enrollCollegeService.getAllEnrollCollege(condition, pageable);
    }

//    @PostMapping("majors")
//    public Page<List<List<QueryEnrollCollegeMajorBean>>> getAllMajors(@RequestBody List<EnrollCollegeEnrollBatch> list, Pageable pageable) {
//        List<List<QueryEnrollCollegeMajorBean>> lists = new ArrayList<>();
//        List<QueryEnrollCollegeMajorBean> queryEnrollCollegeMajorBeans = new ArrayList<>();
//        QueryEnrollCollegeMajorBean queryEnrollCollegeMajorBean = new QueryEnrollCollegeMajorBean();
//        list.forEach(enrollCollegeEnrollBatch -> {
//            List<EnrollStudentPlan> allEnrollCollege = enrollStudentPlanService.getAllEnrollCollege(enrollCollegeEnrollBatch);
//            allEnrollCollege.forEach(enrollStudentPlan -> {
//                BeanUtils.copyProperties(enrollStudentPlan, queryEnrollCollegeMajorBean);
//                enrollMajorScoreLineService.getAllEnrollMajorScoreLine(enrollStudentPlan).forEach(enrollMajorScoreLine -> {
//                    BeanUtils.copyProperties(enrollMajorScoreLine, queryEnrollCollegeMajorBean);
//                    queryEnrollCollegeMajorBeans.add(queryEnrollCollegeMajorBean);
//                });
//            });
//            lists.add(queryEnrollCollegeMajorBeans);
//        });
//        PageImpl<List<List<QueryEnrollCollegeMajorBean>>> lists1 = new PageImpl(lists, pageable, lists.size());
//        return lists1;
//    }

    /**
     * 查询给定大学id的所有专业
     * @param pageable
     * @param list
     * @return
     */
    @GetMapping("major")
    public Slice<List<QueryEnrollCollegeMajorBean_demo>> demo(@PageableDefault(value = 3, sort = "maxScore", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam List<Long> list) {
        return enrollMajorScoreLineService.getMajorScoreLine(list, pageable);
    }

    /**
     * 查询大学简单专业名称
     *
     * @param enrollCollegeEnrollBatchId
     * @return
     */
    @GetMapping(
            value = "getMajorName/{id}"
    )
    public List<String> queryMajorsName(@PathVariable("id") Long enrollCollegeEnrollBatchId) {
        return enrollStudentPlanService.getEnrollMajorName(enrollCollegeEnrollBatchId);
    }
}
