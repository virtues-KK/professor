package com.junyangcompany.demo.entity.professerEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.security.mapping.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "examinee_id")
    private List<ProfessionalEntity> professionalEntities;

    /**
     * 测评师精选结果
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public void setScienceAndArt(ScienceAndArt scienceAndArt) {
        this.scienceAndArt = scienceAndArt;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setWeiCi(Integer weiCi) {
        this.weiCi = weiCi;
    }

    public void setProfessionalEntities(List<ProfessionalEntity> professionalEntities) {
        this.professionalEntities = professionalEntities;
    }

    public void setQueryEnrollCollegeMajorBeanDemos(List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBeanDemos) {
        this.queryEnrollCollegeMajorBeanDemos = queryEnrollCollegeMajorBeanDemos;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
