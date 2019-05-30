package com.junyangcompany.demo.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * 学历等级
 * @author zxy
 * @date 2018-09-12 17:55
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Grade {
    /**
     * 本科
     */
    UNDERGRADUATE(0,"本科"),

    /**
     * 专科
     */
    SPECIALIST(1,"专科");

    private Integer id;

    private String name;

    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static Grade fromId(String value){
        if (value ==null){
            return null;
        }
        final int id = Integer.valueOf(value);
        for (Grade grade : values()) {
            if (grade.id == id)
                return grade;
        }
        return null;
    }

}
