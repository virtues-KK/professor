package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *
 * 功能描述:招生大学分数线
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/16 10:30
 */
@Entity
@Data
@Cacheable
public class EnrollCollegeScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 招生大学名称
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
    private EnrollCollege enrollCollege;

    /**
     * 省名称
     */
    @ManyToOne
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;

    /**
     * 文理科标识
     */
    @Column(nullable = false)
    @Enumerated
    private ScienceAndArt scienceArt;

    /**
     * 批次名称
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
    private EnrollBatch enrollBatch;

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
     * 招生大学批次
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

}
