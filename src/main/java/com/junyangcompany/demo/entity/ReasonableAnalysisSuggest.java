package com.junyangcompany.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 合理化分析建议表
 * @author zxy
 * @date 2018-12-20 14:13
 */
@Entity
@Data
public class ReasonableAnalysisSuggest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String collegeSteady;

    private String collegeProbability;

    /**
     * 大学是否按照冲守保来配置
     */
    private String collegeProbabilityDeficiency;

    /**
     * 大学冲守保是否填满
     */
    private String collegeChongShouBaoSeq;

    /**
     * 大学是否填满
     */
    private String collegeFull;

    /**
     * 专业录取概率顺序是否从低到高
     */
    private String majorProbability;
    /**
     * 专业是否存在比较难预测的学校（概率缺失）
     */

    private String majorProbabilityDeficiency;

    /**
     * 专业是否填满
     */
    private String majorFull;

    /**
     * 可优化大学选项
     */
    private Integer collegeCnt;

    /**
     * 可优化专业选项
     */
    private Integer majorCnt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EnrollBatch enrollBatch;


}
