package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;

import java.util.List;

@Data
public class QueryCollegeMajorCondition {

    List<Long> provinceIdList; //省份id--需要多选

    List<Long> collegeTypeIdList; //大学类型id

    Long gradeId;   //大学级别id -- 专科，本科

    List<Long> collegeLevelIdList; //大学等级id

    Long majorId;//专业id

    Integer year; //考生年份

    Long stuProvince; //考生省份id

    ScienceAndArt scienceArt; //  文科-理科-不限

    String chongShouBao;//冲守保

    Long batchId;//批次编号

    Integer sortType;
}
