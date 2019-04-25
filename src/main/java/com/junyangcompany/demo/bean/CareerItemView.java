package com.junyangcompany.demo.bean;

import lombok.Builder;
import lombok.Data;

/**
 * 职业列表数据结构
 * @author zxy
 * @date 2018-08-07 14:08
 */
@Data
@Builder
public class CareerItemView {

    /**
     * 职业id
     */
    private long id;

    /**
     * 职业名称
     */
    private String name;

    /**
     * 适合专业数量
     */
    private Long majorCount;

}
