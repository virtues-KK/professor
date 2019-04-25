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
public enum PayStatus {
    //  SUCCESS—支付成功
    SUCCESS("SUCCESS","支付成功"),
    //  REFUND—转入退款
    REFUND("REFUND","转入退款"),
    //  NOTPAY—未支付
    NOTPAY("NOTPAY","未支付"),
    //  CLOSED—已关闭
    CLOSED("CLOSED","已关闭"),
    //  REVOKED—已撤销（刷卡支付）
    REVOKED("REVOKED","已撤销（刷卡支付）"),
    //  USERPAYING--用户支付中
    USERPAYING("USERPAYING","用户支付中"),
    //  PAYERROR--支付失败(其他原因，如银行返回失败)
    PAYERROR("PAYERROR","支付失败(其他原因，如银行返回失败)");

    private String id;

    private String name;

    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static PayStatus fromId(String value){
        if (value ==null){
            return null;
        }
        for (PayStatus payStatus : values()) {
            if (payStatus.id.equals(value))
                return payStatus;
        }
        return null;
    }
}
