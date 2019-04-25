package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Auther: xieyue
 * @Date: 2018/10/26 15:24
 * @Description:
 */
@Data
public class UserScoreInfoBean {
    //是否展示修改按钮 true 展示  false 不可展示
    private Boolean isEdit;
    //是否是有效用户 true 是  false 否  备：体验用户不可修改分数信息
    private Boolean isValid;
    //高考开始时间
    private LocalDate startTime;
    //高考结束时间
    private LocalDate endTime;
    //志愿时间内可修改次数
    private Integer editCnt;
    //体验分数
    private Integer experienceScore;
    //体验位次
    private Integer experienceLocation;
    //体验文理科标识
    private ScienceAndArt experienceScienceArt;
    //体验省份
    private Province province;
    //分数信息
    private UserInfo userInfo;
    //不支持省份
    private String notDataProvinceIds;
}
