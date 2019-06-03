package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.embed.CollegeInfo;
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
public class College {

    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;                //大学所属省份

    @ManyToMany(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<CollegeLevel> collegeLevel;  //大学层级

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private CollegeType collegeType;          //大学类型

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<CollegeMajor> collegeMajors;

    private String city;           //大学所属城市

    private String code;                       //代码

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

    @Embedded
    @Basic(fetch = FetchType.LAZY)
    private CollegeInfo collegeInfo;//大学属性可嵌入式设计

    @Enumerated
    private Grade grade;

    /**
     * 是否是公立学校
     */
    @ColumnDefault("1")
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean isPublic;

    /**
     * 招生计划
     */
//    @OneToMany(fetch = FetchType.LAZY)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private List<EnrollStudentPlan> enrollStudentPlans;

    /**
     * 招生简章
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<EnrollGuide> enrollGuides;

    /**
     * 招生大学
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<EnrollCollege> enrollColleges;
}

