package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *
 * 功能描述: 简化优先选专业列表的bean
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2018/11/05 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirstMajorListBean {
    private Long id;
    //专业编号
    private Long majorId;
    //专业名称
    private String majorName;
    //学制
    private String schoolingTime;
    //学历等级
    private String grade;
    //是否已加入备选库  true 是  false 否
    private boolean isChoose;
    //备选库id
    private String changeid;
    //招收院校
    private String enrollCollege;
    //备选库大学集合
    private List<FirstCollegeListBean> firstCollegeListBeans;
    //备选库大学数量
    private Integer collegeCnt;
    //小类名称
    private String subCategoryName;
    //大类名称
    private String CategoryName;
}
