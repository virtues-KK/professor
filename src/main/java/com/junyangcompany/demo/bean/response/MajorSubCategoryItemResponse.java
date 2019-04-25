package com.junyangcompany.demo.bean.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zxy
 * @date 2018-10-16 16:21
 */
@Data
@Builder
public class MajorSubCategoryItemResponse {

    private long id;

    private String name;

    private List<MajorItemResponse> majors;

    private Long majorCount;

}
