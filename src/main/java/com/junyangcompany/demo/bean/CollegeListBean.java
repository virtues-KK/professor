package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.CollegeLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 内容：简化大学列表的bean
 * 时间：2018-08-30
 * 作者：LinDX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeListBean {
    //大学id
    private Long collegeId;
    //大学名称
    private String collegeName;
    //大学校徽
    private String schoolBadge;
    //大学所属城市
    private String city;
    //大学层次
    private List<CollegeLevel> collegeLevelList;
    //学校性质
    private String collegeTypeName;

}
