package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.bean.response.CollegeLine1;
import com.junyangcompany.demo.bean.request.FirstChoice;
import com.junyangcompany.demo.bean.response.ProfessionalBean;
import com.junyangcompany.demo.bean.response.SecondChoiceBean;
import com.junyangcompany.demo.entity.*;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.*;
import com.junyangcompany.demo.repository.*;
import com.junyangcompany.demo.service.*;
import com.junyangcompany.demo.utils.ExportExcelUtils;
import com.junyangcompany.demo.utils.pageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.junyangcompany.demo.config.PrepareCollegeData.enrollCollegeEnrollBatchMap;

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

    private final EnrollStudentPlanService enrollStudentPlanService;

    private final EnrollMajorScoreLineService enrollMajorScoreLineService;

    private final professionalBeanService professionalBeanService;

    private final ExamineeService examineeService;

    private final EnrollBatchService enrollBatchService;

    private final CollegeLevelService collegeLevelService;

    private final CollegeLineService collegeLineService;

    private final SecondChoiceService secondChoiceService;

    private final ProfessionalBeanRepo professionalBeanRepo;

    private final EnrollGuideService enrollGuideService;

    private final SecondChoiceRepo secondChoiceRepo;

    private final ExamineeRepo examineeRepo;

    private final ProvinceRepo provinceRepo;

    private final SecondChoiceEntityRepo secondChoiceEntityRepo;

    private final EnrollStudentPlanRepo enrollStudentPlanRepo;



    @Autowired
    public CollegeQueryController(CollegeProbabilityService collegeProbabilityService, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo, EnrollCollegeService enrollCollegeService, EnrollStudentPlanService enrollStudentPlanService, EnrollMajorScoreLineService enrollMajorScoreLineService, professionalBeanService professionalBeanService, ExamineeService examineeService, EnrollBatchService enrollBatchService, CollegeLevelService collegeLevelService, CollegeLineService collegeLineService, SecondChoiceService secondChoiceService, ProfessionalBeanRepo professionalBeanRepo, EnrollGuideService enrollGuideService, SecondChoiceRepo secondChoiceRepo, ExamineeRepo examineeRepo, ProvinceRepo provinceRepo, SecondChoiceEntityRepo secondChoiceEntityRepo, EnrollStudentPlanRepo enrollStudentPlanRepo) {
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
        this.professionalBeanRepo = professionalBeanRepo;
        this.enrollGuideService = enrollGuideService;
        this.secondChoiceRepo = secondChoiceRepo;
        this.examineeRepo = examineeRepo;
        this.provinceRepo = provinceRepo;
        this.secondChoiceEntityRepo = secondChoiceEntityRepo;
        this.enrollStudentPlanRepo = enrollStudentPlanRepo;
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


    @GetMapping(
            value = "/professionCollege"
    )
//    public Page<List<ProfessionalEntity>> professionCollege(@RequestBody ProfessionalEntity collegeCondition, Pageable pageable) {
    public Page<FirstChoice> professionCollege(ProfessionalBean collegeCondition, Pageable pageable) {
        List<ProfessionalBean> professionalEntities = new ArrayList<>();
        // get weici provinceId scienceArt from examineeId
        Optional<Examinee> byId = examineeRepo.findById(collegeCondition.getExamineeId());
        ScienceAndArt scienceAndArt = ScienceAndArt.文科;
        Long weiCi = 1L;
        Long provinceId = 110000L;
        if (byId.isPresent()){
            scienceAndArt = byId.get().getScienceAndArt();
            weiCi = byId.get().getWeiCi();
            provinceId = byId.get().getProvinceId();
        }else {
            throw new RuntimeException("查询考生异常");
        }
        if (Objects.nonNull(collegeCondition.getSeq()) && collegeCondition.getSeq() != null){
            weiCi = collegeCondition.getSeq();
        }
        List<CollegeProbability> collegeProbabilities = collegeProbabilityService.getAll(provinceId,
                scienceAndArt, weiCi, null, false);
        for (CollegeProbability collegeProbability : collegeProbabilities) {
            ProfessionalBean professionalEntity = new ProfessionalBean();
            professionalEntities.add(professionalEntity);
            Long bid = collegeProbability.getCollegeId();
            professionalEntity.setEnrollCollegeEnrollBatch(bid);
            professionalEntity.setProbability(collegeProbability.getProbalility());
            ProfessionalBean professionalEntity1 = enrollCollegeEnrollBatchMap.get(bid);
            if (Objects.isNull(professionalEntity1)) {
                continue;
            }
            professionalEntity.setBatchNames(professionalEntity1.getBatchNames());
            professionalEntity.setCollegeName(professionalEntity1.getCollegeName());
            professionalEntity.setCollegeCode(professionalEntity1.getCollegeCode());
            professionalEntity.setProvinceIdForCollege(professionalEntity1.getProvinceIdForCollege());
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
//            professionalEntity.setEnrollCollegeEnrollBatch(professionalEntity1.getEnrollCollegeEnrollBatch());
            professionalEntity.setRank(professionalEntity1.getRank());
            // initialize collegeLevel
//            professionalEntity.setLevels(professionalEntity1.getLevels().stream().map(collegeLevelService::save).collect(Collectors.toList()));
            professionalEntity.setLevels(professionalEntity1.getLevels());
            professionalEntity.setProvinceId(collegeCondition.getProvinceId());
            professionalEntity.setScienceAndArt(collegeCondition.getScienceAndArt());
            professionalEntity.setSeq(collegeCondition.getSeq());
            professionalEntity.setExamineeId(collegeCondition.getExamineeId());
        }
        professionalBeans_static = professionalEntities;
        if (collegeCondition.getType() != null && !collegeCondition.getType().equals("")) {
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                    professionalBean.getType().equals(collegeCondition.getType())
            ).collect(Collectors.toList());
        }
        // 加一个筛选大学所在省份
        if (Objects.nonNull(collegeCondition.getProvinceIdForColleges()) &&collegeCondition.getProvinceIdForColleges().size() != 0){
            professionalEntities = professionalEntities.stream().filter(professionalBean ->
                collegeCondition.getProvinceIdForColleges().contains(professionalBean.getProvinceIdForCollege())
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
        if (Objects.nonNull(collegeCondition.getKeyword())) {
            professionalEntities = professionalEntities.stream().filter(professionalBean
                    -> Objects.nonNull(professionalBean.getCollegeName())
                    && professionalBean.getCollegeName().contains(collegeCondition.getKeyword())
            ).collect(Collectors.toList());
        }
        // put in FirstChoice
        List<FirstChoice> firstChoices = new ArrayList<>();
        professionalEntities.forEach(professionalBean -> {
            FirstChoice firstChoice = new FirstChoice();
            String provinceName = provinceRepo.findById(professionalBean.getProvinceIdForCollege()).get().getName();
            firstChoice.setCollegeProvince(provinceName);
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
//        if (data == null || data.isEmpty()) {
//            return new PageImpl();
//            return null;
//        }
//        PageRequest of = PageRequest.of(pageable.getPageNumber(), firstChoices.size());
        Page page = new PageImpl(data, pageable, firstChoices.size());
        return page;
    }

    /**
     * 保存初选结果
     */
    @PutMapping
    @Transactional
    public void saveFirstChoiceResult(@RequestBody List<Long> enrollCollegeEnrollBatchIds, @RequestParam Long examineeId) {
        // 筛选表格中不存在初选结果,再去保存,不需要删除所有记录
        List<Long> enrollCollegeEnrollBatchId = professionalBeanRepo.findAllByExamineeId(examineeId).stream().map(ProfessionalEntity::getEnrollCollegeEnrollBatch).collect(Collectors.toList());
        List<Long> filterParam = enrollCollegeEnrollBatchIds.stream().filter(en->!enrollCollegeEnrollBatchId.contains(en)).collect(Collectors.toList());
        // 需要删除表格中存在但是请求参数中不存在的记录,手动删除精选中的结果
        List<Long> filterDeleteParam = enrollCollegeEnrollBatchId.stream().filter(en->!enrollCollegeEnrollBatchIds.contains(en)).collect(Collectors.toList());
        if (filterDeleteParam.size() != 0) {
            secondChoiceEntityRepo.deleteByEnrollCollegeEnrollBatches(filterDeleteParam);
            professionalBeanRepo.deleteByEnrollCollegeEnrollBatchId(filterDeleteParam);
        }
        List<ProfessionalBean> collect = professionalBeans_static.stream()
                .filter(professionalBean -> filterParam.contains(professionalBean.getEnrollCollegeEnrollBatch())
                ).collect(Collectors.toList());
        List<ProfessionalEntity> professionalEntities = new ArrayList<>();
        collect.forEach(firstChoice -> {
            ProfessionalEntity professionalEntity = new ProfessionalEntity();
            List<CollegeLine> collegeLines = new ArrayList<>();
            professionalEntities.add(professionalEntity);
            BeanUtils.copyProperties(firstChoice, professionalEntity);
            if (Objects.nonNull(firstChoice.getCollegeLines()) && firstChoice.getCollegeLines().size() != 0) {
                firstChoice.getCollegeLines().forEach(collegeLine -> {
                    CollegeLine collegeLine1 = CollegeLine.builder()
                            .minRank(collegeLine.getMinRank())
                            .minScore(collegeLine.getMinScore())
                            .enrollCount(collegeLine.getEnrollCount())
                            .year(collegeLine.getYear()).build();
                    CollegeLine collegeLine2 = collegeLineService.save(collegeLine1);
                    collegeLines.add(collegeLine2);
                });
            }
            professionalEntity.setEnrollCollegeId(enrollStudentPlanRepo.findAllByEnrollCollegeEnrollBatchId(professionalEntity.getEnrollCollegeEnrollBatch()));
            // collegeLine 的设置没起作用
            professionalEntity.setCollegeLines(collegeLines);
            Optional<EnrollBatch> enrollBatchOptional = enrollBatchService.findByName(firstChoice.getBatchNames().get(0));
            enrollBatchOptional.ifPresent(enrollBatch -> professionalEntity.setBatchNames(new ArrayList<>(Collections.singletonList(enrollBatch))));
            examineeService.getExaminee(examineeId).ifPresent(professionalEntity::setExaminee);
            if (!examineeService.getExaminee(examineeId).isPresent()){
                throw new RuntimeException("考生不存在");
            }
            professionalEntity.setLevels(null);
            if (Objects.nonNull(firstChoice.getLevels()) && firstChoice.getLevels().size() != 0) {
                collegeLevelService.findByName(firstChoice.getLevels()).ifPresent(professionalEntity::setLevels);
            }
        });
        List<ProfessionalEntity> professionalEntities1 = professionalBeanService.saveAll(professionalEntities);
    }

    /**
     * 根据examinee查询初选结果
     */
    @GetMapping("searchFirstChoiceByExaminee")
    public List<ProfessionalEntity> searchByExaminee(Long examineeId) {
        List<ProfessionalEntity> professionalEntities = professionalBeanService.searchByExamineeId(examineeId);
        professionalEntities.forEach(professionalEntity -> {
            professionalEntity.setEnrollCollegeId(enrollStudentPlanRepo.findAllByEnrollCollegeEnrollBatchId(professionalEntity.getEnrollCollegeEnrollBatch()));
        });
        // 这里的懒加载属性需要手动触发一下,因为Jackson里面配置了
        professionalEntities.forEach(professionalEntity -> {
            professionalEntity.getLevels().forEach(collegeLevel -> {
                String level = collegeLevel.getName();
            });
            professionalEntity.getCollegeLines().forEach(collegeLine -> {
                String name = collegeLine.toString();
            });
        });
        return professionalEntities;
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

    /**
     * 查询给定大学id的所有专业
     *
     * @param pageable
     * @param list
     * @return
     */
    @GetMapping("major")
    public Slice<List<QueryEnrollCollegeMajorBean_demo>> demo(@PageableDefault(value = 20, sort = "maxScore", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam List<Long> list) {
        return enrollMajorScoreLineService.getMajorScoreLine(list, pageable);
    }

    /**
     * 保存精选专业结果
     */
//    @PutMapping("saveSecondChoice")
//    public List<QueryEnrollCollegeMajorBean_demo> saveSecondChoice(@RequestBody List<QueryEnrollCollegeMajorBean_demo> secondChoices, HttpServletRequest httpServletRequest) {
//         return secondChoiceService.save(secondChoices,httpServletRequest);
//    }

//    /**
//     * 根据考生id查询所有精选结果
//     */
//    @GetMapping("searchMajors")
//    public void searchMajors(@RequestParam Long examineeId){
//        List<Long>
//        professionalBeanService.searchByExamineeId(examineeId).forEach(professionalEntity -> {
//
//        });
//    }

    /**
     * 根据考生id查询精选结果
     */
    @GetMapping("searchSecondChoice")
    public List<SecondChoiceBean> searchSecondChoice(@RequestParam Long examineeId) {
        List<SecondChoiceBean> lists = new ArrayList<>();
        secondChoiceService.search(examineeId).forEach(queryEnrollCollegeMajorBean_demo -> {
            SecondChoiceBean secondChoiceBean = new SecondChoiceBean();
            BeanUtils.copyProperties(queryEnrollCollegeMajorBean_demo,secondChoiceBean);
            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollBatch()) ) {
                secondChoiceBean.setEnrollBatch(queryEnrollCollegeMajorBean_demo.getEnrollBatch().getName());
            }
            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollCollege()) && Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollCollege().getName())){
                secondChoiceBean.setCollegeName(queryEnrollCollegeMajorBean_demo.getEnrollCollege().getName());
            }
//            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollCollege()) && Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollCollege().getEnrollCollegeGuides())){
////                secondChoiceBean.setEnrollCollegeGuides(queryEnrollCollegeMajorBean_demo.getEnrollCollege().getEnrollCollegeGuides());
//            }
            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getProfessionalEntity()) && Objects.nonNull(queryEnrollCollegeMajorBean_demo.getProfessionalEntity().getCollegeName())){
                secondChoiceBean.setCollegeName(queryEnrollCollegeMajorBean_demo.getProfessionalEntity().getCollegeName());
            }
//            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollMajorScoreLine_id())){
//                secondChoiceBean.setEnrollMajorScoreLine_id(queryEnrollCollegeMajorBean_demo.getEnrollMajorScoreLine_id());
//            }
            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getEnrollCollege())){
                secondChoiceBean.setEnrollCollege(queryEnrollCollegeMajorBean_demo.getEnrollCollege());
            }
            lists.add(secondChoiceBean);
        });
        return lists;
    }


    /**
     * 导出精选专业结果为excel
     */
    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse, @RequestParam Long examineeId) throws FileNotFoundException {
        String deskName = "精选专业表";
        String sheetName = "精选结果";
        List<String> field = new ArrayList<>(Arrays.asList("专业名", "所属学校", "年份(year)", "学费", "学制", "招生批次", "最低分数(minScore)", "最低位次(minRank)", "平均分(averageScore)"
                , "招生人数(enrollCount)", "线差(scoreLineDiff)"
        ));
        List<List> listList = new ArrayList<>();

        List<QueryEnrollCollegeMajorBean_demo> search = secondChoiceService.search(examineeId);
        if (search.size() == 0){
            throw new RuntimeException("该考生不存在精选结果");
        }
        for (QueryEnrollCollegeMajorBean_demo secondChoiceBean : search) {
            List list = new ArrayList();
            list.add(secondChoiceBean.getName());
            list.add(secondChoiceBean.getEnrollCollege().getName());
            list.add(secondChoiceBean.getYear());
            list.add(secondChoiceBean.getPrice());
            list.add(secondChoiceBean.getYearOfStudy());
            list.add(secondChoiceBean.getEnrollBatch().getName());
            list.add(secondChoiceBean.getMinScore());
            list.add(secondChoiceBean.getMinRank());
            list.add(secondChoiceBean.getAverageScore());
            list.add(secondChoiceBean.getEnrollCount());
            list.add(secondChoiceBean.getScoreLineDiff());
//            list.add(secondChoiceBean.getEnrollCollege().getEnrollCollegeGuides());
            listList.add(list);
        }
        ExportExcelUtils.export(deskName, httpServletResponse, sheetName, field, listList);
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

    /**
     * 查询大学招生简章
     */
    @GetMapping("guides")
    public List<EnrollGuide> getGuide(@RequestParam Long enrollCollegeEnrollBatchId){
        return enrollGuideService.fromEnrollCollegeId(enrollStudentPlanService.getEnrollCollegeId(enrollCollegeEnrollBatchId).get(0));
    }
}
