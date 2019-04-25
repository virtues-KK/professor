package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Desciption轮播图
 * @Author: zhongxin
 * @Date: 2018/10/23 0023 16:38
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    private Long id;

    /**
     * banner标题
     */
    private String title;
    /**
     * banner图片
     */
    private String image;
    /**
     * banner要跳转的链接地址
     */
    private String link;
    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;


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
     * 产品   支援app
     */
    private String productName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
