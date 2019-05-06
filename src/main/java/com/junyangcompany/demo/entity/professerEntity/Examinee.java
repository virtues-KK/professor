package com.junyangcompany.demo.entity.professerEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.security.mapping.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * author:pan le
 * Date:2019/4/29
 * Time:16:33
 * 用户填入的考生信息实体
 */
@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
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
    @JoinColumn(name = "examinee_id")
    @JsonIgnore
    private List<ProfessionalBean> professionalBeans;

    /**
     * 测评师精选结果
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "examinee_id")
    @JsonIgnore
    private List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBeanDemos;

    /**
     * 当前用户信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @CreatedBy
    private String createName;

    @CreatedDate
    private Date createTime;

}
