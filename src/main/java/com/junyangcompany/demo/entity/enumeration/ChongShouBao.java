package com.junyangcompany.demo.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChongShouBao {

    CHONG(0,"冲"),
    SHOU(1,"守"),
    BAO(2,"保"),
    NAN(3,"难");

    private int id;

    private String name;
    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static ChongShouBao fromId(String value){
        if (value ==null || value.equals("")){
            return null;
        }
        final int id = Integer.valueOf(value);
        for (ChongShouBao chongShouBao : values()) {
            if (chongShouBao.id == id)
                return chongShouBao;
        }
        return null;
    }

}
