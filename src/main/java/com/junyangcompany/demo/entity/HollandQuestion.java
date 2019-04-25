package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.HollandTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 霍兰德题目
 * @author zxy
 * @date 2018-08-02 02:51
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class HollandQuestion {

    /**
     * 霍兰德题目编码
     * example: S-001
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 题目内容
     */
    @Column(nullable = false)
    private String content;

    /**
     * 是否为正向得分
     */
    @Column(nullable = false,updatable = false)
    private Boolean answer;

    /**
     * 题目类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private HollandTypeEnum type;

}
