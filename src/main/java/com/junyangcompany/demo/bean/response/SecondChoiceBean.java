package com.junyangcompany.demo.bean.response;

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
    private String year;
    private String charge;
    private String yearOfStudy;
    private String enrollBatch;
    private String minScore;
    private String minRank;
    private String averageScore;
    private String enrollCount;
    private String scoreLineDiff;
    private String enrollCollegeGuides;
}
