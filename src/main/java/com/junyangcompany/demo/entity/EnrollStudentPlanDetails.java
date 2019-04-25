package com.junyangcompany.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 招生专业描述信息表
 */
@Entity
@Data
public class EnrollStudentPlanDetails {
    @Id
    private Long id;
    //介绍
    @Column(columnDefinition = "text")
    private String description;

    /**
     * 专科：核心课程
     * 本科：主要课程
     */
    @Column(columnDefinition = "text")
    private String courses;

    //评价
    @Column(columnDefinition = "text")
    private String evaluate;

    /**
     * 本科：就业情况
     * 专科：就业面向
     */
    @Column(columnDefinition = "text")
    private String employment;

    /**
     * 本科：主要职业能力
     * 专科：获得知识与能力
     */
    @Column(columnDefinition = "text")
    private String knowledge;

}
