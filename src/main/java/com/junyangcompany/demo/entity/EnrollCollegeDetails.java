package com.junyangcompany.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 招生大学描述信息表
 */
@Entity
@Data
public class EnrollCollegeDetails {
    @Id
    private Long id;
    @Lob
    private String description;  //概况

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

}
