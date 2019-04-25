package com.junyangcompany.demo.bean;


import com.junyangcompany.demo.entity.enumeration.HollandTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 霍兰德评测结果 单字母类型
 * @author zxy
 * @date 2018-08-02 02:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HollandType {

    /**
     * 枚举类型
     */
    private HollandTypeEnum value;

    /**
     * 类型描述
     */
    private String description;

    /**
     * 职业选择倾向
     */
    private String careerOrientation;

    /**
     * 相关职业
     */
    private String relatedCareer;

}
