package com.junyangcompany.demo.bean.response;

import com.junyangcompany.demo.entity.EnrollCollege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/5/8
 * Time:21:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondChoiceBean {
    private String name;
    private String collegeName;
    private Integer year;
    private Integer price;
    private Integer yearOfStudy;
    private String enrollBatch;
    private Integer minScore;
    private Integer minRank;
    private Double averageScore;
    private Integer enrollCount;
    private Integer scoreLineDiff;
    private Long enrollMajorScoreLine_id;
    private String enrollCollegeGuides;
    private EnrollCollege enrollCollege;
}
