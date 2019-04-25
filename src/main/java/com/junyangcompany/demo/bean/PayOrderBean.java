package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 交易订单
 *
 * @author zhongxin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderBean {

    private Long id;

    /**
     * 买卡用户id   与用户中心id 对应
     */
    private Long userId;

    /**
     * 买卡用户的电话号码 与用户中心对应
     */
    private String phoneNumber;

    /**
     * 订单价格
     */
    private String price;

    /**
     * 备注
     */
    private String remark;
    /**
     * 支付描述
     */
    private String payDescription;

    /**
     * 订单状态
     */
    private String payCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 购买渠道  1  app 购买   2  web网站购买  3  线下购买
     */
    private Integer buyWay;

    /**
     * 支付方式   1  微信支付  2  支付宝方式
     */
    private Integer payWay;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 用户名称
     */
    private String userName;
}
