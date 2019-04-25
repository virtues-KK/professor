package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 功能描述: 简化志愿大学列表的bean
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2018/11/7 19:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishEnrollCollegeBean {
    //大学编号
    private Long id;
    //大学名称
    private String collegeName;
    //冲守保标识
    private com.junyangcompany.demo.entity.enumeration.ChongShouBao ChongShouBao;
    //概率
    private Integer probability;
    //批次是否符合 true 符合  false 批次不符合
    private boolean isBatch;
    //是否被选择  true 已选择  false 未选择
    private boolean isChoose;
    //招生人数
    private String studentPlan;
}
