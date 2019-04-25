package com.junyangcompany.demo.bean.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 专业大类列表项返回数据
 * @author zxy
 * @date 2018年10月16日16:18
 */
@Data
@Builder
public class MajorCategoryItemResponse {

    private long id;

    private String name;

    private List<MajorSubCategoryItemResponse> majorSubCategories;

}
