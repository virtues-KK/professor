package com.junyangcompany.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Major
 * @Description: 专业（实体类）
 * @author: LZA
 * @date: 2018-08-02 14:55:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class Major implements Serializable {

    //专业id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //专业名称
    private String name;

    //学校专业
    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<CollegeMajor> collegeMajors;

    //学制
    private Integer schoolingTime;

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

    /**
     * 专科：实习实训
     * 本科：实践教学
     */
    private String practical;


    /**
     * 职业资格证书示例
     */
    private String jobQualificationCertificate;

    /**
     * 衔接中职专业示例
     */
    private String inheritSecondaryVocational;

    /**
     * 接续本科专业示例
     */
    private String inheritUndergraduate;

    //学位授予
    private String degree;

    //小类名称
    private String subCategoryName;

    //大类名称
    private String CategoryName;

    //专业子类
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private MajorSubCategory majorSubCategory;

    @Enumerated
    private Grade grade;

    /**
     * 相关职业
     */
//    @ManyToMany(fetch = FetchType.LAZY)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private List<Career> careers;

    //专业主题
    @ManyToMany
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<TopicType> topicTypes;

    //专业代码
    private String code;

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
