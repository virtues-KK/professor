package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.security.mapping.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * author:pan le
 * Date:2019/4/29
 * Time:16:33
 * 用户填入的考生信息实体
 */
@Data
@Entity
public class Examinee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * 性别
     */
    private Boolean gender;

    private ScienceAndArt scienceAndArt;

    private Integer score;

    /**
     * 位次
     */
    private Integer weiCi;

    /**
     * 测评师初选结果
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "professionalBean_id")
    private List<ProfessionalBean> professionalBeans;

    /**
     * 测评师精选结果
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "queryEnrollCollegeMajorBean_id")
    private List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBeanDemos;

    /**
     * 当前用户信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
