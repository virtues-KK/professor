package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.EnrollCollege;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * author:pan le
 * Date:2019/4/18
 * Time:14:04
 *  生涯测评师精选结果
 */
@Data
@NoArgsConstructor
@Entity
public class QueryEnrollCollegeMajorBean_demo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long enrollMajorScoreLine_id;

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
     * 学年
     */
    private Integer year;

    private String price;

    private String name;

    private Integer yearOfStudy;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;

    @ManyToOne
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollege enrollCollege;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProfessionalEntity professionalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Examinee examinee;



    public QueryEnrollCollegeMajorBean_demo(Long enrollMajorScoreLine_id,Integer maxScore, Integer minScore, Integer minRank, Double averageScore, Integer enrollCount, Integer scoreLineDiff, Integer year, String price, String name, Integer yearOfStudy, EnrollBatch enrollBatch,EnrollCollege enrollCollege) {
        this.enrollMajorScoreLine_id = enrollMajorScoreLine_id;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.minRank = minRank;
        this.averageScore = averageScore;
        this.enrollCount = enrollCount;
        this.scoreLineDiff = scoreLineDiff;
        this.year = year;
        this.price = price;
        this.name = name;
        this.yearOfStudy = yearOfStudy;
        this.enrollBatch = enrollBatch;
        this.enrollCollege = enrollCollege;
    }
}
