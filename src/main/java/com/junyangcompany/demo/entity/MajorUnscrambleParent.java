package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: MajorUnscrambleParent
 * @Description: 专业类解读大类
 * @author: chenshui
 * @date: 2018-12-26 10:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorUnscrambleParent {
    // 编号
    @Id
    private Long id;

    // 名称
    private String name;

    // 内容
    private String content;

    // 封面
    private String picture;
}
