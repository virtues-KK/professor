package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 志愿填报批次设置
 * @author zxy
 * @date 2018-10-25 11:11
 */
@Entity
@Data
@Cacheable
public class CollegeApplyBatchSetting {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 招生批次
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;

    /**
     * 考生所在省份
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

    /**
     * 可填报大学数量
     */
    private Integer collegeNumber;

    /**
     * 可填报专业数量
     */
    private Integer majorNumber;

    /**
     * 最低录取分数
     */
    private Double score;

    /**
     * 省控线ID
     */
    private Long provinceControlLineId;

    @Enumerated
    private ScienceAndArt scienceArt;

    /**
     * 帮助内容
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime time;
    /**
     * 年份
     */
    private Integer year;

}