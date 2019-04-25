package com.junyangcompany.demo.entity.embed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * 大学属性表（嵌入大学表）
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeInfo {

    //zhongxin added
    @Lob
    private String description;  //概况

    private String schoolBadge;  //校徽

    private Integer keyDiscipline; // 重点学科数量

    private Integer doctorStation;  //博士点数量

    private Integer masterPilot;  ///硕士点数量

    private Integer numberOfSpecialty; //特色专业数量

    private String createdDate; //学校创建时间

    private Integer keyLaboratory;   //重点实验室数量

    @Lob
    private String keyDisciplineDesc;  //重点学科描述

    @Lob
    private String keyLaboratoryDesc;  //重点实验室描述

    @Lob
    private String majorDesc;  //特色专业

    @Lob
    private String doubleTopConstructionSpecialtyDesc;  //双一流建设专业

    private String address;  //地址

    private String phone;  //招生办电话

    private String officialWebsite;  //官网

    private String scholarship;   //奖学金

    private String povertyAssistance;   //贫困补助

    private String canteen;//食堂

    private String dormitory;  //宿舍

    private String publicFigure;  //社会名人

}
