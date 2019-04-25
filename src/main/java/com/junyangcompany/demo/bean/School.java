package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.City;
import com.junyangcompany.demo.entity.District;
import com.junyangcompany.demo.entity.Province;
import lombok.Data;

/**
 * @author zxy
 * @date 2018-09-20 11:11
 */
@Data
public class School {

    /**
     * 学校编号
     */
    private Long id;

    /**
     * 学校名
     */
    private String name;

    /**
     * 关联的区域
     */
    private District district;

    /**
     * 关联的城市
     */
    private City city;

    /**
     * 关联的省份
     */
    private Province province;
}
