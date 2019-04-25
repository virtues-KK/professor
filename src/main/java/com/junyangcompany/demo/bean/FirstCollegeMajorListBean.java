package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
 *
 * 功能描述: 简化优先选大学列表的bean
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2018/10/30 14:38
 */
@Data
public class FirstCollegeMajorListBean implements Serializable {
    //招生计划id
    private Long id;
    //招生计划名称
    private String name;
    //专业代码
    private String code;
    //大学录取分数线
    private String scoreLines;
    //招生计划
    private String studentPlan;
    //是否已加入备选库  true 是  false 否
    private boolean isChoose;
    //计划数量
    private String planCount;
    //录取分数线最低分数
    private Integer minScore;
    //录取分数线最低位次
    private Integer minRank;
    //当前年份
    private Integer year;
    //冲守保标识
    private com.junyangcompany.demo.entity.enumeration.ChongShouBao ChongShouBao;
    //概率
    private Integer probability;
    //招生专业分数线集合
    List<EnrollMajorScoreLine> enrollMajorScoreLines;
    //专本科
    private String grade;
    //学制
    private String yearOfStudy;
    //价格
    private String price;
}
