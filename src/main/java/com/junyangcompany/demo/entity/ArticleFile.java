package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: MinuteAudioFile
 * @Description: 知涯志愿文章文件
 * @author: panle
 * @date: 2018-08-31 10:00:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleFile implements Serializable {

    /**
     * 文章编号
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 知涯志愿专题
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleSpecial articleSpecial;

    /**
     * 状态   1   已发布    2  草稿    3   已下架   4  未发布()定时发布时间未到
     */
    private Integer status;

    /**
     * 操作人
     */
    private String userName;

    /**
     * 阅读数
     */
    private Integer totalReadNum;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 封面图片
     */
    private String image;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
