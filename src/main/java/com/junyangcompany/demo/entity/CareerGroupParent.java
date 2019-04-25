package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: CareerGroupParent
 * @Description: 职业族群大类
 * @author: chenshui
 * @date: 2018-12-13 16:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerGroupParent {
    // 编号
    @Id
    private Long id;
    // 标题
    private String title;
    // 图片
    private String picture;
    // 简介s
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("sequence ASC")
    private List<CareerGroupIntroduce> careerGroupIntroduces;
    // 工作内容s
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("sequence ASC")
    private List<CareerGroupWork> careerGroupWorks;
    // 知识技能s
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("sequence ASC")
    private List<CareerGroupSkill> careerGroupSkills;
    // 发展方向s
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("sequence ASC")
    private List<CareerGroupDevelop> careerGroupDevelops;
    // 排序
    private Integer sequence;

}
