package com.junyangcompany.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zxy
 * @date 2018-12-17 11:05
 */
@Entity
@Data
public class ProvinceEnroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProvinceControlLine provinceControlLine;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 人数
     */
    private Integer count;

}
