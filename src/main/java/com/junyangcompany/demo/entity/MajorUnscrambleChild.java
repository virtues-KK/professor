package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName: MajorUnscrambleChild
 * @Description: 专业类解读小类
 * @author: chenshui
 * @date: 2018-12-26 10:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorUnscrambleChild {
    // 编号
    @Id
    private Long id;

    // 名称
    private String name;

    // 内容
    @Lob
    private String content;

    // 封面
    private String picture;

    // 职业解读大类
    @ManyToOne(fetch = FetchType.LAZY)
    private MajorUnscrambleParent majorUnscrambleParent;

}
