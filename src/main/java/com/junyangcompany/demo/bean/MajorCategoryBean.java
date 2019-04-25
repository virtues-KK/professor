package com.junyangcompany.demo.bean;

import lombok.*;

import java.util.List;

/**
 * @Author: LZA
 * @Date: 2018/9/13  16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class MajorCategoryBean {

    private Long id;
    private String name;
    private List<MajorSubCategoryBean> majorSubCategories;


}
