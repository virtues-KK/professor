package com.junyangcompany.demo.entity.embed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * @author zxy
 * @date 2018-08-06 15:56
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerDetail {

    /**
     * 描述
     */
    @Lob
    private String description;

    /**
     * 知识背景
     */
    @Lob
    private String knowledgeBackground;

    /**
     * 基本技能
     */
    @Lob
    private String basicSkill;

    /**
     * 职业技能
     */
    @Lob
    private String vocationalSkills;

    /**
     * 工作经验
     */
    @Lob
    private String workExperience;

    /**
     * 从业资格
     */
    @Lob
    private String occupationalRequirements;

    /**
     * 发展路径
     */
    @Lob
    private String developmentPath;

    /**
     * 发展前景
     */
    @Lob
    private String prospects;

    /**
     * 工作内容
     */
    @Lob
    private String jobContent;

    /**
     * 概况图片路径
     */
    private String descriptionImage;

    /**
     * 工作环境
     */
    @Lob
    private String workEnv;

    /**
     * 工作环境图片路径
     */
    private String workEnvImage;

    /**
     * 学历要求
     */
    private String educationalRequirements;

}