package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

/**
 * 产品表
 *
 * @Author: zhongxin
 * @Date: 2018/8/9 0009 15:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsProductBean {

    @Id
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 模块列表
     */
    private List<OmsSpecialTopicBean> specialTopics;
}
