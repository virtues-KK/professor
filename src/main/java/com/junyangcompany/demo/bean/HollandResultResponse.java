package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.bean.response.MajorCategoryItemResponse;
import com.junyangcompany.demo.bean.response.MajorSubCategoryItemResponse;
import com.junyangcompany.demo.entity.HollandResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zxy
 * @date 2018-07-27 14:01
 * @description 霍兰德测评结果页数据
 */
@Data
@AllArgsConstructor
@Builder
public class HollandResultResponse {

    private HollandResult.Scores scores;

    private String detail;

    /**
     * 所有类型数据
     */
    private List<HollandType> types;

    /**
     * 专业小类数据
     */
    private List<MajorSubCategoryItemResponse> items;

    /**
     * 特殊说明
     */
    private String specialExplain;

    /**
     * 专业大类数据
     */
    private List<MajorCategoryItemResponse> majorCategories;

}
