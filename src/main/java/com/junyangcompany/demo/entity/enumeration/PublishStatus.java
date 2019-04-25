package com.junyangcompany.demo.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PublishStatus {

    UNPUBLISHED(0,"数据还未发布"),
    DATAPUBLISHED(1,"招生计划已发布"),
    SCOREPUBLISHED(2,"高考录取分已发布");

    private int id;

    private String name;
    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static PublishStatus fromId(String value){
        if (value ==null || value.equals("")){
            return null;
        }
        final int id = Integer.valueOf(value);
        for (PublishStatus publishStatus : values()) {
            if (publishStatus.id == id)
                return publishStatus;
        }
        return null;
    }

}
