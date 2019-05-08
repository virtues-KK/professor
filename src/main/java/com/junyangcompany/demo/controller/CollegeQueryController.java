package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.bean.response.CollegeLine1;
import com.junyangcompany.demo.bean.response.FirstChoice;
import com.junyangcompany.demo.bean.response.ProfessionalBean;
import com.junyangcompany.demo.bean.response.SecondChoiceBean;
import com.junyangcompany.demo.entity.*;
import com.junyangcompany.demo.entity.professerEntity.CollegeLine;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import com.junyangcompany.demo.repository.EnrollCollegeScoreLineRepo;
import com.junyangcompany.demo.service.*;
import com.junyangcompany.demo.utils.ExportExcelUtils;
import com.junyangcompany.demo.utils.pageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
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

    private final EnrollBatchService enrollBatchService;

    private final CollegeLevelService collegeLevelService;

    private final CollegeLineService collegeLineService;

    private final SecondChoiceService secondChoiceService;


    @Autowired
    public CollegeQueryController(CollegeProbabilityService collegeProbabilityService, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo, EnrollCollegeService enrollCollegeService, EnrollStudentPlanService enrollStudentPlanService, EnrollMajorScoreLineService enrollMajorScoreLineService, professionalBeanService professionalBeanService, ExamineeService examineeService, EnrollBatchService enrollBatchService, CollegeLevelService collegeLevelService, CollegeLineService collegeLineService, SecondChoiceService secondChoiceService) {
        this.collegeProbabilityService = collegeProbabilityService;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.enrollCollegeScoreLineRepo = enrollCollegeScoreLineRepo;
        this.enrollCollegeService = enrollCollegeService;
        this.enrollStudentPlanService = enrollStudentPlanService;
        this.enrollMajorScoreLineService = enrollMajorScoreLineService;
        this.professionalBeanService = professionalBeanService;
        this.examineeService = examineeService;
        this.enrollBatchService = enrollBatchService;
        this.collegeLevelService = collegeLevelService;
        this.collegeLineService = collegeLineService;
        this.secondChoiceService = secondChoiceService;
    }

    private Map<Long, ProfessionalBean> getEnrollCollegeEnrollBatchMap() {
        if (enrollCollegeEnrollBatchMap == null) {
            enrollCollegeEnrollBatchMap = new HashMap<>();
            List<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatches = enrollCollegeEnrollBatchRepo.findAll();
            for (EnrollCollegeEnrollBatch enrollCollegeEnrollBatch : enrollCollegeEnrollBatches) {
                String name = enrollCollegeEnrollBatch.getEnrollCollege().getName();
                ArrayList<String> strings = new ArrayList<>();
                strings.add(enrollCollegeEnrollBatch.getEnrollBatch().getName());
                ProfessionalBean professionalEntity = new ProfessionalBean();
                professionalEntity.setBatchNames(strings);
                professionalEntity.setCollegeName(name);
                professionalEntity.setCollegeCode(enrollCollegeEnrollBatch.getEnrollCollege().getCode());
                EnrollCollege enrollCollege = enrollCollegeEnrollBatch.getEnrollCollege();
                if (enrollCollege.getCollegeType() != null)
                    professionalEntity.setType(enrollCollege.getCollegeType().getName());
                professionalEntity.setRank(enrollCollege.getNetRank());
                if (enrollCollege.getCollegeLevel() != null) {
//                    List<CollegeLevel> collegeLevels = collegeLevelService.saveAll(enrollCollege.getCollegeLevel());
//                    professionalEntity.setLevels(collegeLevels);
                    professionalEntity.setLevels(enrollCollege.getCollegeLevel().stream().map(CollegeLevel::getName).collect(Collectors.toList()));
//                    professionalBeanService.save(professionalEntity)
                }
                enrollCollegeEnrollBatchMap.put(enrollCollegeEnrollBatch.getId(), professionalEntity);
            }
        }
        return enrollCollegeEnrollBatchMap;
    }

    /**
     * 定义全局静态返回大学数据
     */
    private static List<ProfessionalBean> professionalBeans_static = new ArrayList<>();

    //TODO: 待检查
    @PostMapping("search")
    public List<ProfessionalBean> searchCollege(@RequestBody ProfessionalBean professionalEntity_) {
        log.info(String.valueOf(professionalBeans_static.size()));
        return professionalBeans_static.stream().filter(professionalBean ->
                professionalBean.getCollegeName().contains(professionalEntity_.getCollegeName())
        ).collect(Collectors.toList());
    }


    @PostMapping(
            value = "/professionCollege"
    )
//    public Page<List<ProfessionalEntity>> professionCollege(@RequestBody ProfessionalEntity collegeCondition, Pageable pageable) {
    public Page<List<FirstChoice>> professionCollege(@RequestBody ProfessionalBean collegeCondition, Pageable pageable) {
        List<ProfessionalBean> professionalEntities = new ArrayList<>();
        List<CollegeProbability> collegeProbabilities = collegeProbabilityService.getAll(collegeCondition.getProvinceId(),
                collegeCondition.getScienceAndArt(), collegeCondition.getSeq(), null, false);
        for (CollegeProbability collegeProbability : collegeProbabilities) {
            ProfessionalBean professionalEntity = new ProfessionalBean();
            professionalEntities.add(professionalEntity);
            Long bid = collegeProbability.getCollegeId();
            professionalEntity.setEnrollCollegeEnrollBatch(bid);
            professionalEntity.setProbability(collegeProbability.getProbalility());
            ProfessionalBean professionalEntity1 = getEnrollCollegeEnrollBatchMap().get(bid);
            // initialize ProfessionalEntity
//            ProfessionalEntity professionalEntity1 = professionalBeanService.save(professionalBean2);
            //professionalEntity.setEnrollCollegeEnrollBatch(enrollCollegeEnrollBatch);
            //initialize  enrollBatch
//            List<EnrollBatch> enrollBatchs = new ArrayList<>();
//            professionalEntity1.getBatchNames().forEach(enrollBatch -> {
//                enrollBatchService.findByName(enrollBatch.getName())
//                        .ifPresent(enrollBatchs::add);
//            });
            if (Objects.isNull(professionalEntity1)) {
                continue;
            }
            professionalEntity.setBatchNames(professionalEntity1.getBatchNames());
            professionalEntity.setCollegeName(professionalEntity1.getCollegeName());
            professionalEntity.setCollegeCode(professionalEntity1.getCollegeCode());
            List<EnrollCollegeScoreLine> enrollCollegeScoreLines = enrollCollegeScoreLineRepo.findByyearAndEnrollCollegeEnrollBatch(collegeCondition.getScienceAndArt(),
                    bid, 2015);
            //initialize collegeLine
            List<CollegeLine1> collegeLines = new ArrayList<>();
            for (EnrollCollegeScoreLine enrollCollegeScoreLine : enrollCollegeScoreLines) {
                CollegeLine1 collegeLine = new CollegeLine1();
                collegeLine.enrollCount = enrollCollegeScoreLine.getEnrollCount();
                collegeLine.minRank = enrollCollegeScoreLine.getMinRank();
                collegeLine.minScore = enrollCollegeScoreLine.getMinScore();
                collegeLine.year = enrollCollegeScoreLine.getYear();
                collegeLines.add(collegeLine);
            }
            professionalEntity.setCollegeLines(collegeLines);
//            professionalEntity.setCollegeLines(collegeLineService.saveAll(collegeLines));
            professionalEntity.setType(professionalEntity1.getType());
            professionalEntity.setRank(professionalEntity1.getRank());
            // initialize collegeLevel
//            professionalEntity.setLevels(professionalEntity1.getLevels().stream().map(collegeLevelService::save).collect(Collectors.toList()));
            professionalEntity.setLevels(professionalEntity1.getLevels());
            professionalEntity.setProvinceId(collegeCondition.getProvinceId());
            professionalEntity.setScienceAndArt(collegeCondition.getScienceAndArt());
            professionalEntity.setSeq(collegeCondition.getSeq());
            professionalEntity.setExaminee(collegeCondition.getExaminee());
        }
        professionalBeans_static = professionalEntities;
        if (collegeCondition.getType() != null && !collegeCondition.getType().equals("")) {
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    professionalBean.getType().equals(collegeCondition.getType())
            ).collect(Collectors.toList());
        }

        // 在选择批次的时候,不能做比较,只能确定某些批次来筛选
        if (collegeCondition.getBatchNames().size() != 0) {
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    collegeCondition.getBatchNames()
                            .containsAll(professionalBean.getBatchNames())
            ).collect(Collectors.toList());
        }
        //turn list<String> to list<collegeLevel>
        if (collegeCondition.getLevels() != null && collegeCondition.getLevels().size() != 0) {
            List<String> condition_levels = collegeCondition.getLevels();
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    collegeCondition.getLevels().size() != 0 &&
                            professionalBean.getLevels().containsAll(condition_levels)
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxRank() != null) {
            professionalEntities = professionalEntities.stream().filter(professionalBean -> collegeCondition.getMaxRank() > professionalBean.getRank() && collegeCondition.getMinRank() < professionalBean.getRank()
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMaxProbability() != null) {
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    professionalBean.getProbability() <= collegeCondition.getMaxProbability()
                            && professionalBean.getProbability() >= 0
            ).collect(Collectors.toList());
        }
        if (collegeCondition.getMinProbability() != null) {
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    professionalBean.getProbability() >= collegeCondition.getMinProbability() &&
                            professionalBean.getProbability() <= 100
            ).collect(Collectors.toList());
        }
        if (Objects.nonNull(collegeCondition.getKeyWord())) {
            professionalEntities = professionalEntities.stream().filter(professionalBean
                    -> Objects.nonNull(professionalBean.getCollegeName())
                    && professionalBean.getCollegeName().contains(collegeCondition.getKeyWord())
            ).collect(Collectors.toList());
        }
        // put in FirstChoice
        List<FirstChoice> firstChoices = new ArrayList<>();
        professionalEntities.forEach(professionalBean -> {
            FirstChoice firstChoice = new FirstChoice();
            List<com.junyangcompany.demo.bean.response.CollegeLine> collegeLines = new ArrayList<>();
            BeanUtils.copyProperties(professionalBean, firstChoice);
            if (professionalBean.getBatchNames().size() >= 1) {
                firstChoice.setBatchName(professionalBean.getBatchNames().get(0));
            }
            if (Objects.nonNull(professionalBean.getCollegeLines()) && professionalBean.getCollegeLines().size() >= 1) {
                professionalBean.getCollegeLines().forEach(collegeLine1 -> {
                    com.junyangcompany.demo.bean.response.CollegeLine collegeLine = new com.junyangcompany.demo.bean.response.CollegeLine();
                    collegeLine.setMinRank(collegeLine1.getMinRank());
                    collegeLine.setMinScore(collegeLine1.getMinScore());
                    collegeLine.setEnrollCount(collegeLine1.getEnrollCount());
                    collegeLine.setYear(collegeLine1.getYear());
                    collegeLines.add(collegeLine);
                });
            }
            firstChoice.setCollegeLines(collegeLines);
            firstChoice.setLevels(professionalBean.getLevels());
            firstChoices.add(firstChoice);
        });
        List<FirstChoice> data = pageUtils.getPageSizeDataForRelations(firstChoices, pageable.getPageSize(), pageable.getPageNumber());
        if (data == null || data.isEmpty()) {
            return new PageImpl(new ArrayList(Arrays.asList(new FirstChoice())), pageable, 0);
//            return null;
        }
        PageRequest of = PageRequest.of(pageable.getPageNumber(), firstChoices.size());
        Page page = new PageImpl(data, of, data.size());
        return page;
    }

    /**
     * 保存初选结果
     *
     * @param firstChoices
     * @return
     */
    @PutMapping
    public List<ProfessionalEntity> saveFirstChoiceResult(@RequestBody List<FirstChoice> firstChoices) {
        List<ProfessionalEntity> professionalEntities = new ArrayList<>();
        firstChoices.forEach(firstChoice -> {
            ProfessionalEntity professionalEntity = new ProfessionalEntity();
            List<CollegeLine> collegeLines = new ArrayList<>();
            professionalEntities.add(professionalEntity);
            BeanUtils.copyProperties(firstChoice, professionalEntity);
            firstChoice.getCollegeLines().forEach(collegeLine -> {
                CollegeLine collegeLine1 = CollegeLine.builder()
                        .minRank(collegeLine.getMinRank())
                        .minScore(collegeLine.getMinScore())
                        .enrollCount(collegeLine.getEnrollCount())
                        .year(collegeLine.getYear()).build();
                CollegeLine collegeLine2 = collegeLineService.save(collegeLine1);
                collegeLines.add(collegeLine2);
            });
            professionalEntity.setCollegeLines(collegeLines);
            Optional<EnrollBatch> enrollBatchOptional = enrollBatchService.findByName(firstChoice.getBatchName());
            enrollBatchOptional.ifPresent(enrollBatch -> professionalEntity.setBatchNames(new ArrayList<>(Collections.singletonList(enrollBatch))));
            examineeService.getExaminee(firstChoice.getExamineeId()).ifPresent(professionalEntity::setExaminee);
            collegeLevelService.findByName(firstChoice.getLevels()).ifPresent(professionalEntity::setLevels);
        });
        return professionalBeanService.saveAll(professionalEntities);
    }

    /**
     * 根据examinee查询初选结果
     */
    @GetMapping("searchFirstChoiceByExaminee")
    public List<ProfessionalEntity> searchByExaminee(Long examineeId) {
        return professionalBeanService.searchByExamineeId(examineeId);
    }


    /**
     * @param condition
     * @param pageable
     * @return 未被使用
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
     *
     * @param pageable
     * @param list
     * @return
     */
    @GetMapping("major")
    public Slice<List<QueryEnrollCollegeMajorBean_demo>> demo(@PageableDefault(value = 3, sort = "maxScore", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam List<Long> list) {
        return enrollMajorScoreLineService.getMajorScoreLine(list, pageable);
    }

    /**
     * 保存精选专业结果
     */
    @PutMapping("saveSecondChoice")
    public List<QueryEnrollCollegeMajorBean_demo> saveSecondChoice(@RequestBody List<QueryEnrollCollegeMajorBean_demo> secondChoices) {
        return secondChoiceService.save(secondChoices);
    }

    /**
     * 导出精选专业结果为excel
     */
    @PostMapping("exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse, @RequestBody List<SecondChoiceBean> lists) throws FileNotFoundException {
        String deskName = "精选专业表";
        String sheetName = "精选结果";
        List<String> field = new ArrayList<>(Arrays.asList("专业名","所属学校","年份(year)", "学费",  "学制", "招生批次", "最低分数(minScore)", "最低位次(minRank)", "平均分(averageScore)"
                , "招生人数(enrollCount)", "线差(scoreLineDiff)",  "招生简章"
        ));
        List<List> listList = new ArrayList<>();
        List list = new ArrayList();
        for (SecondChoiceBean secondChoiceBean : lists) {
            list.add(secondChoiceBean.getName());
            list.add(secondChoiceBean.getCollegeName());
            list.add(secondChoiceBean.getYear());
            list.add(secondChoiceBean.getCharge());
            list.add(secondChoiceBean.getYearOfStudy());
            list.add(secondChoiceBean.getEnrollBatch());
            list.add(secondChoiceBean.getMinScore());
            list.add(secondChoiceBean.getMinRank());
            list.add(secondChoiceBean.getAverageScore());
            list.add(secondChoiceBean.getEnrollCount());
            list.add(secondChoiceBean.getScoreLineDiff());
            list.add(secondChoiceBean.getEnrollCollegeGuides());
        }
        listList.add(list);
        ExportExcelUtils.export(deskName,httpServletResponse,sheetName,field,listList);
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
