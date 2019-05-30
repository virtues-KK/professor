package com.junyangcompany.demo.bean.response;

import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/5/20
 * Time:10:49
 *  根据enrollCollegeEnrollBatch 查询专业的投影类
 */

@Data
@AllArgsConstructor
public class SecondMajors {

    private Long id;
    private String name;
    private Integer year;
    private ScienceAndArt scienceArt;
    private String price;
    private EnrollBatch enrollBatch;
    private Integer minScore;
    private Integer minRank;
    private Double averageScore;
    private Integer enrollCount;
    private Integer scoreLineDiff;
    private EnrollCollege  enrollCollege;
    /**
     * 学年
     */
    private Integer yearOfStudy;
    private EnrollStudentPlan enrollStudentPlan;


}
