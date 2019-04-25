package com.junyangcompany.demo.bean.response;

import lombok.Builder;
import lombok.Data;

/**
 * 专业列表项返回数据
 * @author zxy
 * @date 2018年10月16日16:12
 */
@Data
@Builder
public class MajorItemResponse {

    private long id;

    private String name;

}
