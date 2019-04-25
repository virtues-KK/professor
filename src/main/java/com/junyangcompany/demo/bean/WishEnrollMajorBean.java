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
public class WishEnrollMajorBean {
    //专业编号
    private Long id;
    //专业名称
    private String majorName;
    //是否被选择  true 已选择  false 未选择
    private boolean isChoose;
    //冲守保标识
    private com.junyangcompany.demo.entity.enumeration.ChongShouBao ChongShouBao;
    //概率
    private Integer probability;
    //招生人数
    private String planCount;
}
