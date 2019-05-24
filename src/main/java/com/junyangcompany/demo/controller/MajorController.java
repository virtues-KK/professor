package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.request.SecondBean;
import com.junyangcompany.demo.bean.response.Response;
import com.junyangcompany.demo.bean.response.SecondMajors;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
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
    public MajorController(EnrollMajorScoreLineRepo enrollMajorScoreLineRepo, ExamineeRepo examineeRepo, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, SecondChoiceEntityRepo secondChoiceEntityRepo, professionalBeanService professionalBeanService, ExportRxcelService exportRxcelService, SecondChoiceService secondChoiceService, ProfessionalBeanRepo professionalBeanRepo) {
        this.enrollMajorScoreLineRepo = enrollMajorScoreLineRepo;
        this.examineeRepo = examineeRepo;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.secondChoiceEntityRepo = secondChoiceEntityRepo;
        this.professionalBeanService = professionalBeanService;
        this.exportRxcelService = exportRxcelService;
        this.secondChoiceService = secondChoiceService;
        this.professionalBeanRepo = professionalBeanRepo;
    }


    // 根据enrollCollegeEnrollBatch和scienceAndArt查询专业
    @GetMapping("Majors")
    public Page<List<Response>> getMajors(@RequestParam List<Long> enrollCollegeEnrollBatchIds,
                                              @RequestParam Long examineeId, Pageable pageable){
        List<EnrollCollegeEnrollBatch> allById = enrollCollegeEnrollBatchRepo.findAllById(enrollCollegeEnrollBatchIds);


        Optional<Examinee> byId = examineeRepo.findById(examineeId);
        ScienceAndArt scienceAndArt = ScienceAndArt.理科;
        if (byId.isPresent()){
            scienceAndArt = byId.get().getScienceAndArt();
        }
        List<SecondMajors> majorScoreLine = enrollMajorScoreLineRepo.getMajorOptions(allById,scienceAndArt);
        Long enrollStudentPlanId = 0L;
        List<Response> list = new ArrayList<>();
        for (SecondMajors major : majorScoreLine){
            if (!major.getEnrollStudentPlan().getId().equals(enrollStudentPlanId)){
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
                PutInResponce(list,major,scienceAndArt,secondBean);
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("majorName",major.getName());
                map.put("enrollMajorScoreLinId",major.getId());
                map.put("year",major.getYear());
                map.put("price",major.getPrice());
                map.put("minScore",major.getMinScore());
                map.put("minRank",major.getMinRank());
                map.put("averageScore",major.getAverageScore());
                map.put("enrollCount",major.getEnrollCount());
                map.put("scoreLineDiff",major.getScoreLineDiff());
                map.put("yearOfStudy",major.getYearOfStudy());
                list.get(list.size() - 1).getScoreInformation().add(map);
    }
}
        List<Response> data = pageUtils.getPageSizeDataForRelations(list, pageable.getPageSize(), pageable.getPageNumber());
        Page<List<Response>> page = new PageImpl(data,pageable,list.size());
        return page;
    }

    private void PutInResponce(List<Response> responses, SecondMajors major , ScienceAndArt scienceAndArt,SecondBean secondBean) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Response re;
        list.clear();
        list.add(map);
        map.put("majorName",major.getName());
        map.put("enrollMajorScoreLinId",major.getId());
        map.put("year",major.getYear());
        map.put("price",major.getPrice());
        map.put("minScore",major.getMinScore());
        map.put("minRank",major.getMinRank());
        map.put("averageScore",major.getAverageScore());
        map.put("enrollCount",major.getEnrollCount());
        map.put("scoreLineDiff",major.getScoreLineDiff());
        map.put("yearOfStudy",major.getYearOfStudy());
        re = Response.builder().name(major.getName()).scienceAndArt(scienceAndArt).enrollMajorScoreLine_id(major.getId())
                .enrollCollege(major.getEnrollCollege()).EnrollStudentPlanId(major.getEnrollStudentPlan().getId()).scoreInformation(list).secondBean(secondBean)
                .build();
        responses.add(re);
    }

    //保存精选结果
    @PutMapping
    @Transactional
    public void saveSecondChoice(@RequestBody List<SecondBean> secondBeans,@RequestParam Long examineeId){
        secondChoiceService.saveSecondChoice(secondBeans,examineeId);
    }

    //根据考生id查找精选结果
    @GetMapping("searchSecondChoice")
    public List<Response> searchSecondChoiceByExaminee(Long examineeId) {
        Examinee examinee = examineeRepo.findById(examineeId).isPresent() ? examineeRepo.findById(examineeId).get() : null;
        List<SecondMajors> collect ;
        List<SecondMajors> collects = new ArrayList<>();
        ScienceAndArt scienceAndArt = ScienceAndArt.文科;
        if (Objects.nonNull(examinee)) {
            scienceAndArt = examinee.getScienceAndArt();
            List<Long> enrollCollegeEnrollBatch = professionalBeanService.searchByExamineeId(examineeId).stream().map(ProfessionalEntity::getEnrollCollegeEnrollBatch).collect(Collectors.toList());
            List<EnrollCollegeEnrollBatch> allById = enrollCollegeEnrollBatchRepo.findAllById(enrollCollegeEnrollBatch);
            List<SecondChoiceEntity> byExaminee = secondChoiceEntityRepo.findByExaminee(examinee);
            if (byExaminee.size() != 0) {
                for (SecondChoiceEntity b : byExaminee) {
                    collect = enrollMajorScoreLineRepo.getMajorOptions(allById, scienceAndArt).stream().filter(examinee1 -> examinee1.getEnrollCollege().getId().equals(b.getEnrollCollegeId())
                            && examinee1.getEnrollStudentPlan().getId().equals(b.getEnrollStudentPlanId())).collect(Collectors.toList());
                    for (SecondMajors secondMajors : collect) {
                        collects.add(secondMajors);
                    }
                }
            }
        }
        List<Response> list = new ArrayList<>();
        Long enrollStudentPlanId =0L;
        for (SecondMajors major : collects){
            if (!major.getEnrollStudentPlan().getId().equals(enrollStudentPlanId)){
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
                PutInResponce(list,major,scienceAndArt,secondBean);
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("year",major.getYear());
                map.put("price",major.getPrice());
                map.put("minScore",major.getMinScore());
                map.put("minRank",major.getMinRank());
                map.put("averageScore",major.getAverageScore());
                map.put("enrollCount",major.getEnrollCount());
                map.put("scoreLineDiff",major.getScoreLineDiff());
                map.put("yearOfStudy",major.getYearOfStudy());
                list.get(list.size() - 1).getScoreInformation().add(map);
            }
        }
        return list;
    }

    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse,Long examineeId) throws IOException {
        List<Response> responses = this.searchSecondChoiceByExaminee(examineeId);
        String name = responses.get(0).getEnrollCollege().getName();
        Integer scienceAndArt1 = examineeRepo.findById(examineeId).get().getScienceAndArt() == ScienceAndArt.文科 ? 0 : 1;

        exportRxcelService.exportExcel(httpServletResponse,responses,scienceAndArt1);

    }



}
