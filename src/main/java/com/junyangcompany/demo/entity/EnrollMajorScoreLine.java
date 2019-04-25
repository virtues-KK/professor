package com.junyangcompany.demo.entity;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *
 * 功能描述:招生专业分数线
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/16 10:33
 */
@Entity
@Data
@Cacheable
public class EnrollMajorScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 招生计划
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollStudentPlan enrollStudentPlan;

    /**
     * 专业方向名称
     */
    @Column(nullable = false,length = 500)
    private String name;
    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;
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

    /**
     * 学费
     */
    private String price;

    /**
     * 批次名称
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;
    /**
     * 招生大学
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollege enrollCollege;


}
