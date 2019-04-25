package com.junyangcompany.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @Author: LZA
 * @Date: 2018/9/13  16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class MajorBean {

    private Long id;
    private String name;
    @JsonIgnore
    private long majorSubCategoryId;

    @JsonIgnore
    private long majorCategoryId;
    //是否已加入备选库  true 是  false 否   添加v1.1版本需求  用于web端优先选专业列表
    private boolean isChoose;
}
