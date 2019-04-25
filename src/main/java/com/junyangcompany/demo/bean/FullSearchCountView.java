package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 传给前端的对象
 */
@Data
@AllArgsConstructor
public class FullSearchCountView {

    /**
     * 大学统计数量
     */
    private long collegeCount;

    /**
     * 专业统计数量
     */
    private long majorCount;

    /**
     * 职业统计数量
     */
    private long careerCount;
}