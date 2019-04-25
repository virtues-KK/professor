package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.CollegeLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhongxin
 * @Date: 2018/9/18 0018 17:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteCollegeBean {
    private Long id;
    /**
     * 大学名称
     */
    private String name;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 大学类型
     */
    private String collegeTypeName;
    /**
     * 大学层级
     */
    private List<CollegeLevel> collegeLevel;
    /**
     * 大学校徽
     */
    private String schoolBadge;

}
