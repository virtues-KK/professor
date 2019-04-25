package com.junyangcompany.demo.bean;

import lombok.Builder;
import lombok.Data;

/**
 * 专业列表数据结构
 * @author zxy
 * @date 2018-08-10 13:51
 */
@Data
@Builder
public class MajorItemView {

    /**
     * 专业id
     */
    private Long id;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 学制 单位：年
     */
    private Integer schoolingTime;

    /**
     * 此专业的开设院校
     */
    private Long collegeCount;

}
