package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: CareerGroupWork
 * @Description: 职业族群工作内容
 * @author: chenshui
 * @date: 2018-12-13 16:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerGroupWork {
    // 编号
    @Id
    private Long id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 排序
    private Integer sequence;

}
