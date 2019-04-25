package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldx
 * @Description:
 * @date 2018/12/24 10:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCollegeMajorInfoCondition {
    private Long majorId;
    private Integer year; //考生年份
    private Long stuProvince; //考生省份id
    private ScienceAndArt scienceAndArt; //文科理科
    private Long provinceId; //学校地区
    private Long collegeTypeId;
    private Long gradeId;
    private Long collegeLevelId; //大学等级id
}
