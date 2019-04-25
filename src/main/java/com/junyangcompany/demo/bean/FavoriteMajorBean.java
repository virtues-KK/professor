package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhongxin
 * @Date: 2018/9/18 0018 17:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMajorBean {
    /**
     * 专业id
     */
    private Long id;
    /**
     * 专业名称
     */
    private String name;
    /**
     * 专业类型
     */
    private String subCategoryName;


}
