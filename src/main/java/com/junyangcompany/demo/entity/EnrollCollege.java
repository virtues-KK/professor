package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class EnrollCollege {
    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;                //大学所属省份

    @ManyToOne(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province enrollProvince;                //大学招生省份

    @ManyToMany(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<CollegeLevel> collegeLevel;  //大学层级

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private CollegeType collegeType;          //大学类型

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "enroll_college_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<EnrollStudentPlan> enrollStudentPlans;


    @OneToMany(mappedBy = "enrollCollegeEnrollBatch", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<EnrollCollegeMajor> enrollCollegeMajor;

    private String city;           //大学所属城市

    private String code;                       //代码
    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;
    /**
     * 金苹果排名
     */
    @Column
    private Integer appRank;

    /**
     * 校友网排名
     */
    @Column
    private Integer netRank;

    private String collegeInitials;             //大学拼音首字母


    private String schoolBadge;  //校徽

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private EnrollCollegeDetails enrollCollegeDes;


    @Enumerated
    private Grade grade;

    /**
     * 是否是公立学校
     */
    @ColumnDefault("1")
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean isPublic;
    /**
     * 招生简章
     */
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private List<EnrollCollegeGuide> enrollCollegeGuides;

    //基础大学
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private College college;

    /**
     * 计划招生总数量
     */
    private Integer planCount;
}

