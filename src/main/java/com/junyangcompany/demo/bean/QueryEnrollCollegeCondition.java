package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.CollegeType;
import com.junyangcompany.demo.entity.Province;
import lombok.Data;

import java.util.List;

/**
 * author:pan le
 * Date:2019/4/17
 * Time:14:10
 */
@Data
public class QueryEnrollCollegeCondition {

    private Long seq; // 位次

    private Boolean scienceAndArt; // 文理科

    private Long provinceId;

    private Province province; // 大学所属省份

    private Province EnrollProvince; // 大学招生省份

    private List<CollegeLevel> collegeLevel;  //大学层级

    private CollegeType collegeType;          //大学类型

    private Boolean isPublic; //是否为公立学校

    private Integer netRank; //校友网排名

    private Integer appRank; // 金苹果排名

    private Integer keyDiscipline; // 重点学科数量

    private Integer doctorStation;  //博士点数量

    private Integer masterPilot;  ///硕士点数量

    private Integer numberOfSpecialty; //特色专业数量

    private String createdDate; //学校创建时间

    private Integer keyLaboratory;   //重点实验室数量
}
