package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldx
 * @Description: top20专业的bean
 * @Date 2019/1/21 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopTwentyMajorBean {
    private Long majorId;
    private String majorName;
    private String recommendations;
}
