package com.junyangcompany.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.entity.enumeration.Grade;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * author:pan le
 * Date:2019/4/18
 * Time:14:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryEnrollCollegeMajorBean {

    private String EnrollCollegeName;

    /**
     * 招生代码
     */
    private String code;

    /**
     * 专业名称
     */
    private String name;
    /**
     * 专业方向名称
     */
    @Column(nullable = false,length = 500)
    private String majorName;
    /**
     * 最高分
     */
    private Integer maxScore;

    /**
     * 最低分
     */
    private Integer minScore;

    /**
     * 最低位次
     */
    private Integer minRank;
    /**
     * 平均分
     */
    private Double averageScore;
    /**
     * 录取人数
     */
    private Integer enrollCount;
    /**
     * 线差
     */
    private Integer scoreLineDiff;

    private EnrollBatch enrollBatch;

    /**
     * 计划数量
     */
    private Integer planCount;

    /**
     * 学年
     */
    private Integer yearOfStudy;

    @Enumerated
    private ScienceAndArt scienceArt;

    @Enumerated
    private Grade grade;

    @ManyToOne
    @JoinColumn
    private Province province;

    /**
     * 学费
     */
    private String price;

    /**
     * 年份
     */
    private Integer year;

}
