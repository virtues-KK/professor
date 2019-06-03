package com.junyangcompany.demo.bean.response;

import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.entity.professerEntity.CollegeLine;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.PlanLine;
import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 生涯测评师初选结果
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalBean {

    private Long enrollCollegeEnrollBatch;

    private Long seq; // 位次

    private ScienceAndArt scienceAndArt; // 文理科

    private Long provinceId;

    /**
     * 大学所在的城市
     */
    private String City;

    private Long provinceIdForCollege;

    // 条件中的大学所在省份
    private List<Long> provinceIdForColleges;

    private String collegeName;

    private List<String> batchNames = new ArrayList<>();

    private String collegeCode;

    private List<CollegeLine1> collegeLines;

    private Integer probability;

    private Integer maxProbability;

    private Integer minProbability;

    private List<String> types;

    private String type;

    private Integer rank;

    private Integer minRank;

    private Integer maxRank;

    private List<String> levels;

    private String batchName;

    /**
     * 搜索关键字
     */
    private String keyword;

//    private List<PlanLine> planLInes;

//    private List<QueryEnrollCollegeMajorBean_demo> secondChoices;

    /**
     * 考生信息
     */
    private Long examineeId;
}




