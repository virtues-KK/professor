package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhongxin
 * @Date: 2018/9/17 0017 14:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleBean {

    private Long id;
    private String name;
    private String image;
}
