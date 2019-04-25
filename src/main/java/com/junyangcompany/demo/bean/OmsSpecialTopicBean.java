package com.junyangcompany.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 专题表
 *
 * @Author: zhongxin
 * @Date: 2018/8/7 0007 14:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsSpecialTopicBean {

    @Id
    private Long id;
    /**
     * 专题名称
     */
    private String title;

    /**
     * 产品名称
     */
    private OmsProductBean product;
    /**
     * 操作人
     */
    private String userName;


    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 简介
     */
    private String introduction;

}
