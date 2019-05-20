package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.bean.response.Response;
import com.junyangcompany.demo.bean.response.SecondMajors;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import com.junyangcompany.demo.repository.EnrollMajorScoreLineRepo;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.utils.pageUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collector;
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

    @Autowired
    public MajorController(EnrollMajorScoreLineRepo enrollMajorScoreLineRepo, ExamineeRepo examineeRepo, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo) {
        this.enrollMajorScoreLineRepo = enrollMajorScoreLineRepo;
        this.examineeRepo = examineeRepo;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
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
        String majorName = null;
        Response response = new Response();
        List<Map<String,Object>> mapList = new ArrayList<>();
        List<Response> list = new ArrayList<>();
        for (SecondMajors major : majorScoreLine) {
            if ( ! major.getName().equals(majorName)) {
                majorName = major.getName();
                PutInResponce(list,major);
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("year",major.getYear());
                map.put("price",major.getPrice());
                map.put("minScore",major.getMinScore());
                map.put("minRank",major.getMinRank());
                map.put("averageScore",major.getAverageScore());
                map.put("enrollCount",major.getEnrollCount());
                map.put("scoreLineDiff",major.getScoreLineDiff());
                list.get(list.size() - 1).getScoreInformation().add(map);
//                list.get(list.size() - 1).setScoreInformation(scoreInformation);
            }
        }
        List<Response> data = pageUtils.getPageSizeDataForRelations(list, pageable.getPageSize(), pageable.getPageNumber());
        Page<List<Response>> page = new PageImpl(data,pageable,list.size());
        return page;
    }

    private void PutInResponce(List<Response> responses, SecondMajors major) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Response re = new Response();
        re = null;
        list.clear();
        list.add(map);
        map.put("year",major.getYear());
        map.put("price",major.getPrice());
        map.put("minScore",major.getMinScore());
        map.put("minRank",major.getMinRank());
        map.put("averageScore",major.getAverageScore());
        map.put("enrollCount",major.getEnrollCount());
        map.put("scoreLineDiff",major.getScoreLineDiff());
        re = Response.builder().name(major.getName()).enrollCollege(major.getEnrollCollege()).scoreInformation(list).build();
        responses.add(re);
    }
}
