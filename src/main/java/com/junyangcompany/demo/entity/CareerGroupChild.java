package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: CareerGroupChild
 * @Description: 职业族群小类
 * @author: chenshui
 * @date: 2018-12-13 16:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerGroupChild {
    // 编号
    @Id
    private Long id;
    // 标题
    private String title;
    // 图片
    private String picture;
    // 简介s
    @OneToMany(fetch = FetchType.LAZY)
    private List<CareerGroupIntroduce> careerGroupIntroduces;
    // 工作内容s
    @OneToMany(fetch = FetchType.LAZY)
    private List<CareerGroupWork> careerGroupWorks;
    // 知识技能s
    @OneToMany(fetch = FetchType.LAZY)
    private List<CareerGroupSkill> careerGroupSkills;
    // 就业前景s
    @OneToMany(fetch = FetchType.LAZY)
    private List<CareerGroupEmployment> careerGroupEmployments;
    //  就业前景职业
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Career> groupEmploymentCareers;
    // 排序
    private Integer sequence;

}
