package com.junyangcompany.demo.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 霍兰德评测类型对象
 * 该枚举类会被序列化成object
 * @author zxy
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HollandTypeEnum {

    S("S", "社会型"),
    E("E", "企业型"),
    C("C", "常规型"),
    I("I", "调研型"),
    A("A", "艺术型"),
    R("R", "实际型");

    private final String code;

    private final String name;

}
