package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: MinuteAudioSpecial
 * @Description: 知涯志愿专题
 * @author: panle
 * @date: 2018/9/11
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleSpecial implements Serializable {

    /**
     * 专题编号
     */
    @Id
    private Long id;

    /**
     * 专题标题
     */
    private String title;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 操作人
     */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
