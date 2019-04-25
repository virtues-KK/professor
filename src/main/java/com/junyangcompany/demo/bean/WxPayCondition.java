package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.TradeTypeEnum;
import lombok.Data;

@Data
public class WxPayCondition {
    // 卡号
    private String cardNumber;
    // 绑定手机号
    private String phoneNumber;
    // 支付模式
    private TradeTypeEnum tradeType;
    // 卡类型
    private UserInfo.Level level;
    //

}
