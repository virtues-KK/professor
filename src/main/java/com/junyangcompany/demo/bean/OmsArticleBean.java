package com.junyangcompany.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * @Author: zhongxin
 * @Date: 2018/8/7 0007 15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsArticleBean {
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
     * 专题
     */
    @ManyToOne
    private OmsSpecialTopicBean specialTopic;

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
     * 产品   1 选科  2 一分钟
     */
    @OneToOne
    private OmsProductBean product;
    /**
     * 首页展示   1    展示     0    不展示
     */
    private Integer isHomePageShow;

    /**
     * 封面图片
     */
    private String image;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 音频文件
     */
    private String audioPath;
}
