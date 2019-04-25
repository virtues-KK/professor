package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.Grade;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

/**
 * 招生信息
 * @author zxy
 * @date 2018-09-10 16:44
 */
@Data
@Entity
@Cacheable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollStudentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 招生代码
     */
    private String code;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 招生批次
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
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
    @JsonIgnore
    private Province province;

    /**
     * 学费
     */
    private String price;

    /**
     * 年份
     */
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enroll_college_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
    private EnrollCollege enrollCollege;

    @ManyToMany(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
    private Set<Major> majors;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="id")
    @JsonIgnore
    private EnrollStudentPlanDetails enrollStudentPlanDes;

    @ManyToOne(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JsonIgnore
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    @Override
    public String toString() {
        return "EnrollStudentPlan{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", enrollBatch=" + enrollBatch +
                ", planCount=" + planCount +
                ", yearOfStudy=" + yearOfStudy +
                ", scienceArt=" + scienceArt +
                ", grade=" + grade +
                ", province=" + province +
                ", price='" + price + '\'' +
                ", year=" + year +
                '}';
    }
}