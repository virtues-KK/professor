package com.junyangcompany.demo.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大学列表 数据结构
 * @author zxy
 * @date 2018-08-10 13:50
 */
@Data
@Builder
public class CollegeItemView {

    /**
     * 大学id
     */
    private long id;

    /**
     * 大学等级名称数组
     */
    private List<String> levelNames;

    /**
     * 大学名称
     */
    private String name;

    /**
     * 大学类型名称
     */
    private String typeName;

    /**
     * 大学校徽图片地址
     */
    private String schoolBadge;

}
