package com.junyangcompany.demo.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  支付状态
 * @author chenshui
 * @date 2018-11-08 09:55
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeTypeEnum {
    //  APP—支付成功
    APP("APP","APP支付"),
    //  NATIVE—扫码支付
    NATIVE("NATIVE","扫码支付"),
    //  MWEB—H5支付
    MWEB("MWEB","H5支付");

    private String id;

    private String name;

    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static TradeTypeEnum fromId(String value){
        if (value ==null){
            return null;
        }
        for (TradeTypeEnum payStatus : values()) {
            if (payStatus.id.equals(value))
                return payStatus;
        }
        return null;
    }
}
