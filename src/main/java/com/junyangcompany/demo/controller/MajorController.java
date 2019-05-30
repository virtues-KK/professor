package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.request.SecondBean;
import com.junyangcompany.demo.bean.response.Response;
import com.junyangcompany.demo.bean.response.SecondMajors;
import com.junyangcompany.demo.entity.*;
import com.junyangcompany.demo.entity.enumeration.Grade;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import com.junyangcompany.demo.entity.professerEntity.SecondChoiceEntity;
import com.junyangcompany.demo.repository.*;
import com.junyangcompany.demo.service.ExportRxcelService;
import com.junyangcompany.demo.service.SecondChoiceService;
import com.junyangcompany.demo.service.professionalBeanService;
import com.junyangcompany.demo.utils.pageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/5/20
 * Time:10:19
 */
@RestController
@RequestMapping("secondChoice")
public class MajorController {

    private final EnrollMajorScoreLineRepo enrollMajorScoreLineRepo;

    private final ExamineeRepo examineeRepo;

    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;

    private final SecondChoiceEntityRepo secondChoiceEntityRepo;

    private final professionalBeanService professionalBeanService;

    private final ExportRxcelService exportRxcelService;

    private final SecondChoiceService secondChoiceService;

    private final ProfessionalBeanRepo professionalBeanRepo;


    @Autowired
    public MajorController(EnrollMajorScoreLineRepo enrollMajorScoreLineRepo, ExamineeRepo examineeRepo, SecondChoiceEntityRepo secondChoiceEntityRepo, professionalBeanService professionalBeanService, ExportRxcelService exportRxcelService, SecondChoiceService secondChoiceService, ProfessionalBeanRepo professionalBeanRepo, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, EnrollStudentPlanRepo enrollStudentPlanRepo) {
        this.enrollMajorScoreLineRepo = enrollMajorScoreLineRepo;
        this.examineeRepo = examineeRepo;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.secondChoiceEntityRepo = secondChoiceEntityRepo;
        this.professionalBeanService = professionalBeanService;
        this.exportRxcelService = exportRxcelService;
        this.secondChoiceService = secondChoiceService;
        this.professionalBeanRepo = professionalBeanRepo;
        this.enrollStudentPlanRepo = enrollStudentPlanRepo;
    }


    private final EnrollStudentPlanRepo enrollStudentPlanRepo;

    // 根据enrollCollegeEnrollBatch和scienceAndArt查询专业
    @GetMapping("Majors")
    public Page<List<Response>> getMajors(@RequestParam List<Long> enrollCollegeEnrollBatchIds,
                                          @RequestParam Long examineeId, Pageable pageable) {
        List<EnrollCollegeEnrollBatch> allById = enrollCollegeEnrollBatchRepo.findAllById(enrollCollegeEnrollBatchIds);

        Optional<Examinee> byId = examineeRepo.findById(examineeId);
        ScienceAndArt scienceAndArt = ScienceAndArt.理科;
        Long enrollStudentPlanId = 0L;
        List<Response> list = new ArrayList<>();
        if (byId.isPresent()) {
            scienceAndArt = byId.get().getScienceAndArt();
        }
        List<SecondMajors> majorScoreLine = enrollMajorScoreLineRepo.getMajorOptions(allById, scienceAndArt);
        if (majorScoreLine.size() == 0) {
            List<EnrollStudentPlan> enrollStudentPlans = enrollStudentPlanRepo.findAllByEnrollCollegeEnrollBatchIdAndScienceArt(enrollCollegeEnrollBatchIds.get(0), scienceAndArt);
            for (EnrollStudentPlan enrollStudentPlan : enrollStudentPlans) {
                // 代表没有专业分数线,显示专业名称即可
                List<Map<String, Object>> list1 = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                //get professionalEntity
                Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, enrollCollegeEnrollBatchIds.get(0));
                SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(enrollCollegeEnrollBatchIds.get(0))
                        .enrollCollegeId(enrollStudentPlan.getEnrollCollege().getId())
                        .enrollStudentPlanId(enrollStudentPlan.getId())
                        .examineeId(examineeId)
                        .professionalEntityId(professionEntityBeanId)
                        .enrollMajorScoreLineId(enrollStudentPlanId)
                        .build();
                map.put("year", enrollStudentPlan.getYear());
                map.put("yearOfStudy", enrollStudentPlan.getYearOfStudy());
                map.put("enrollCount", enrollStudentPlan.getPlanCount());
                list1.add(map);
                Response response = Response.builder().scienceAndArt(scienceAndArt).name(enrollStudentPlan.getName()).scoreInformation(list1).secondBean(secondBean).enrollMajorScoreLine_id(enrollStudentPlanId).build();
                list.add(response);
                enrollStudentPlanId++;
            }
//            List<Response> data = pageUtils.getPageSizeDataForRelations(list, pageable.getPageSize(), pageable.getPageNumber());
//            Page<List<Response>> page = new PageImpl(data, pageable, list.size());
//            return page;
        }
        else {
            for (SecondMajors major : majorScoreLine) {
                Long majorEnrollStudenrPlanId = major.getEnrollStudentPlan().getId();
                if (Objects.nonNull(major.getEnrollStudentPlan()) && !majorEnrollStudenrPlanId.equals(enrollStudentPlanId)) {
                    enrollStudentPlanId = majorEnrollStudenrPlanId;
                    //get professionalEntity
                    Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId());
                    // create secondBean for save
                    SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId())
                            .enrollCollegeId(major.getEnrollCollege().getId())
                            .enrollStudentPlanId(majorEnrollStudenrPlanId)
                            .examineeId(examineeId)
                            .professionalEntityId(professionEntityBeanId)
                            .enrollMajorScoreLineId(major.getId())
                            .build();
                    PutInResponce(list, major, scienceAndArt, secondBean);
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("majorName", major.getName());
                    map.put("enrollMajorScoreLinId", major.getId());
                    map.put("year", major.getYear());
                    map.put("price", major.getPrice());
                    map.put("minScore", major.getMinScore());
                    map.put("minRank", major.getMinRank());
                    map.put("averageScore", major.getAverageScore());
                    map.put("enrollCount", major.getEnrollCount());
                    map.put("scoreLineDiff", major.getScoreLineDiff());
                    map.put("yearOfStudy", major.getYearOfStudy());
                    if (list.size() != 0) {
                        list.get(list.size() - 1).getScoreInformation().add(map);
                    }
                }
            }
        }
        List<Response> data = pageUtils.getPageSizeDataForRelations(list, pageable.getPageSize(), pageable.getPageNumber());
        Page<List<Response>> page = new PageImpl(data, pageable, list.size());
        return page;
    }

    private void PutInResponce(List<Response> responses, SecondMajors major, ScienceAndArt scienceAndArt, SecondBean secondBean) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Response re;
        list.clear();
        list.add(map);
        map.put("majorName", major.getName());
        map.put("enrollMajorScoreLinId", major.getId());
        map.put("year", major.getYear());
        map.put("price", major.getPrice());
        map.put("minScore", major.getMinScore());
        map.put("minRank", major.getMinRank());
        map.put("averageScore", major.getAverageScore());
        map.put("enrollCount", major.getEnrollCount());
        map.put("scoreLineDiff", major.getScoreLineDiff());
        map.put("yearOfStudy", major.getYearOfStudy());
        re = Response.builder().name(major.getName()).scienceAndArt(scienceAndArt).enrollMajorScoreLine_id(major.getId())
                .enrollCollege(major.getEnrollCollege()).EnrollStudentPlanId(major.getEnrollStudentPlan().getId()).scoreInformation(list).secondBean(secondBean)
                .build();
        responses.add(re);
    }

    //保存精选结果
    @PutMapping
    @Transactional
    public void saveSecondChoice(@RequestBody List<SecondBean> secondBeans, @RequestParam Long examineeId) {
        secondChoiceService.saveSecondChoice(secondBeans, examineeId);
    }

    //根据考生id查找精选结果
    @GetMapping("searchSecondChoice")
    public List<Response> searchSecondChoiceByExaminee(Long examineeId) {
        List<Response> list = new ArrayList<>();
        //get scienceArt
        Examinee examinee = examineeRepo.findById(examineeId).isPresent() ? examineeRepo.findById(examineeId).get() : null;
        ScienceAndArt scienceAndArt = examinee.getScienceAndArt();
        // get SecondEnrollCollegeEnrollBatch
        List<SecondChoiceEntity> secondChoiceEntities = secondChoiceEntityRepo.findByExamineeId(examineeId);
        //get
        for (SecondChoiceEntity entity : secondChoiceEntities) {
            List<SecondMajors> majorOption = enrollMajorScoreLineRepo.getMajorOption(entity.getEnrollCollegeEnrollBatch(), scienceAndArt);
            // get from enrollStudentPlan
            if (majorOption.size() == 0){
                Optional<EnrollStudentPlan> enrollStudentPlanOptional = enrollStudentPlanRepo.findById(entity.getEnrollStudentPlanId());
                EnrollStudentPlan enrollStudentPlan = enrollStudentPlanOptional.orElse(null);
                    // 代表没有专业分数线,显示专业名称即可
                    List<Map<String, Object>> list1 = new ArrayList<>();
                    Map<String, Object> map = new HashMap<>();
                    //get professionalEntity
                Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, entity.getEnrollCollegeEnrollBatch());
                SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(entity.getEnrollCollegeEnrollBatch())
                            .enrollCollegeId(enrollStudentPlan.getEnrollCollege().getId())
                            .enrollStudentPlanId(enrollStudentPlan.getId())
                            .examineeId(examineeId)
                            .professionalEntityId(professionEntityBeanId)
                            .enrollMajorScoreLineId(entity.getEnrollMajorScoreLineId())
                            .build();
                    map.put("year", enrollStudentPlan.getYear());
                    map.put("yearOfStudy", enrollStudentPlan.getYearOfStudy());
                    map.put("enrollCount", enrollStudentPlan.getPlanCount());
                    list1.add(map);
                    Response response = Response.builder().scienceAndArt(scienceAndArt).name(enrollStudentPlan.getName()).scoreInformation(list1).secondBean(secondBean).enrollMajorScoreLine_id(entity.getEnrollMajorScoreLineId()).build();
                    list.add(response);
            }
            //get from enrollMajorScoreLine
            else {
                Long enrollStudentPlanId = 0L;
                for (SecondMajors major : majorOption) {
                    if (!major.getEnrollStudentPlan().getId().equals(enrollStudentPlanId)) {
                        enrollStudentPlanId = major.getEnrollStudentPlan().getId();
                        //get professionalEntity
                        Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId());
                        // create secondBean for save
                        SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId())
                                .enrollCollegeId(major.getEnrollCollege().getId())
                                .enrollStudentPlanId(major.getEnrollStudentPlan().getId())
                                .examineeId(examineeId)
                                .professionalEntityId(professionEntityBeanId)
                                .build();
                        //test
                        String name = major.getEnrollCollege().getName();
                        PutInResponce(list, major, scienceAndArt, secondBean);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("year", major.getYear());
                        map.put("price", major.getPrice());
                        map.put("minScore", major.getMinScore());
                        map.put("minRank", major.getMinRank());
                        map.put("averageScore", major.getAverageScore());
                        map.put("enrollCount", major.getEnrollCount());
                        map.put("scoreLineDiff", major.getScoreLineDiff());
                        map.put("yearOfStudy", major.getYearOfStudy());
                        list.get(list.size() - 1).getScoreInformation().add(map);
                    }
                }
            }
        }
        return list;
    }


//    //根据考生id查找精选结果
//    @GetMapping("searchSecondChoice")
//    public List<Response> searchSecondChoiceByExaminee(Long examineeId) {
//        Examinee examinee = examineeRepo.findById(examineeId).isPresent() ? examineeRepo.findById(examineeId).get() : null;
//        List<SecondMajors> collect;
//        List<SecondMajors> collects = new ArrayList<>();
//        ScienceAndArt scienceAndArt = ScienceAndArt.文科;
//        List<Response> list = new ArrayList<>();
//        Long enrollStudentPlanId = 0L;
//        if (Objects.nonNull(examinee)) {
//            scienceAndArt = examinee.getScienceAndArt();
//            List<Long> enrollCollegeEnrollBatch = professionalBeanService.searchByExamineeId(examineeId).stream().map(ProfessionalEntity::getEnrollCollegeEnrollBatch).collect(Collectors.toList());
//            List<SecondChoiceEntity> byExaminee = secondChoiceEntityRepo.findByExaminee(examinee);
//            if (byExaminee.size() != 0) {
//                for (SecondChoiceEntity b : byExaminee) {
////                    List<SecondMajors> majorOptions = enrollMajorScoreLineRepo.getMajorOptions(allById, scienceAndArt);
//                    for (Long collegeEnrollBatch : enrollCollegeEnrollBatch) {
//                        List<SecondMajors> majorOptions = enrollMajorScoreLineRepo.getMajorOption(collegeEnrollBatch,scienceAndArt);
//                        collect = majorOptions.stream().filter(examinee1 -> examinee1.getEnrollCollege().getId().equals(b.getEnrollCollegeId())
//                                && examinee1.getEnrollStudentPlan().getId().equals(b.getEnrollStudentPlanId())).collect(Collectors.toList());
//                        // 这里在enrollMajorScoreLine查不到,需要到enrollStudentPlan中查,把结果单独处理为response返回
//                        if (majorOptions.size() == 0) {
//                            //TODO:???
//                            for (Long collegeEnrollBatch1 : enrollCollegeEnrollBatch) {
//                                List<EnrollStudentPlan> enrollStudentPlans = enrollStudentPlanRepo.findAllByEnrollCollegeEnrollBatchIdAndScienceArt(collegeEnrollBatch, scienceAndArt);
//                                for (EnrollStudentPlan enrollStudentPlan : enrollStudentPlans) {
//                                    // 代表没有专业分数线,显示专业名称即可
//                                    List<Map<String, Object>> list1 = new ArrayList<>();
//                                    Map<String, Object> map = new HashMap<>();
//                                    //get professionalEntity
//                                    Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, collegeEnrollBatch);
//                                    SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(collegeEnrollBatch1)
//                                            .enrollCollegeId(enrollStudentPlan.getEnrollCollege().getId())
//                                            .enrollStudentPlanId(enrollStudentPlan.getId())
//                                            .examineeId(examineeId)
//                                            .professionalEntityId(professionEntityBeanId)
//                                            .enrollStudentPlanId(enrollStudentPlan.getId())
//                                            .build();
//                                    map.put("year", enrollStudentPlan.getYear());
//                                    map.put("yearOfStudy", enrollStudentPlan.getYearOfStudy());
//                                    map.put("enrollCount", enrollStudentPlan.getPlanCount());
//                                    list1.add(map);
//                                    Response response = Response.builder().scienceAndArt(scienceAndArt).name(enrollStudentPlan.getName()).scoreInformation(list1).secondBean(secondBean).enrollMajorScoreLine_id(enrollStudentPlanId++).build();
//                                    list.add(response);
//                                }
//                            }
//                        }
//                        for (SecondMajors secondMajors : collect) {
//                            collects.add(secondMajors);
//                        }
//                    }
//
//                }
//            }
//        }
//        for (SecondMajors major : collects) {
//            if (!major.getEnrollStudentPlan().getId().equals(enrollStudentPlanId)) {
//                enrollStudentPlanId = major.getEnrollStudentPlan().getId();
//                //get professionalEntity
//                Long professionEntityBeanId = professionalBeanRepo.findIdByExamineeIdAndEnrollCollegeEnrollBatch(examineeId, major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId());
//                // create secondBean for save
//                SecondBean secondBean = SecondBean.builder().enrollCollegeEnrollBatch(major.getEnrollStudentPlan().getEnrollCollegeEnrollBatch().getId())
//                        .enrollCollegeId(major.getEnrollCollege().getId())
//                        .enrollStudentPlanId(major.getEnrollStudentPlan().getId())
//                        .examineeId(examineeId)
//                        .professionalEntityId(professionEntityBeanId)
//                        .build();
//                //test
//                String name = major.getEnrollCollege().getName();
//                PutInResponce(list, major, scienceAndArt, secondBean);
//            } else {
//                Map<String, Object> map = new HashMap<>();
//                map.put("year", major.getYear());
//                map.put("price", major.getPrice());
//                map.put("minScore", major.getMinScore());
//                map.put("minRank", major.getMinRank());
//                map.put("averageScore", major.getAverageScore());
//                map.put("enrollCount", major.getEnrollCount());
//                map.put("scoreLineDiff", major.getScoreLineDiff());
//                map.put("yearOfStudy", major.getYearOfStudy());
//                list.get(list.size() - 1).getScoreInformation().add(map);
//            }
//        }
//        return list;
//    }

    @Autowired
    private ProvinceRepo provinceRepo;

    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse, Long examineeId) throws IOException {
        List<Response> responses = this.searchSecondChoiceByExaminee(examineeId);
        //为没有大学分数线的数据添加上大学名称
        for (Response respons : responses) {
            if (Objects.isNull(respons.getEnrollCollege())){
                Long professionalEntityId = respons.getSecondBean().getProfessionalEntityId();
                Optional<ProfessionalEntity> professionalEntityOptional = professionalBeanRepo.findById(professionalEntityId);
                if (professionalEntityOptional.isPresent()){
                    ProfessionalEntity professionalEntity = professionalEntityOptional.get();
                    respons.setEnrollCollege(EnrollCollege.builder()
                            .name(professionalEntity.getCollegeName())
                            .city(professionalEntity.getCity())
                            .province(Province.builder().name("未知").build())
                            .planCount(-999)
                            .code(professionalEntity.getCollegeCode()).build());
                    Map<String, Object> map = respons.getScoreInformation().get(0);
                    map.put("minScore","未知");
                    map.put("majorName", respons.getName());
                    map.put("enrollMajorScoreLinId", -999L);
                    map.put("price", -999);
                    map.put("minScore", -999);
                    map.put("minRank", -999);
                    map.put("averageScore", -999);
                    map.put("scoreLineDiff", -999);
                    respons.getScoreInformation().add(map);

                }
            }
        }

        Integer scienceAndArt1 = examineeRepo.findById(examineeId).get().getScienceAndArt() == ScienceAndArt.文科 ? 1 : 0;
        exportRxcelService.exportExcel(httpServletResponse, responses, scienceAndArt1);
    }

    // 根据大学省份区分batch
    @GetMapping("getBatchs")
    public List<String> getBatchsByProvince(Long examineeId) {
        Optional<Examinee> byId = examineeRepo.findById(examineeId);
        Long provinceId = 0L;
        if (byId.isPresent()) {
            provinceId = byId.get().getProvinceId();
        }
        return enrollCollegeEnrollBatchRepo.findEnrollBatchByProVinceId(provinceId).stream().map(EnrollCollegeEnrollBatch::getEnrollBatch).map(EnrollBatch::getName).distinct().collect(Collectors.toList());
    }
}
