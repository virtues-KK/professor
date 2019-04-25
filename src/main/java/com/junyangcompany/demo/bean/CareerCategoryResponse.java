package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zxy
 * @date 2018-09-18 11:13
 */
@Data
@AllArgsConstructor
public class CareerCategoryResponse {

    private Long id;

    private String name;

    private List<CareerItemResponse> careers;

}
