package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollegeScoreLine;
import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirstCollegeListBean {
    //招生专业编号
    private Long id;
    //大学id
    private Long collegeId;
    //大学名称
    private String collegeName;
    //大学校徽
    private String schoolBadge;
    //招生专业名称
    private String enrollStudentPlansName;
    //招生专业编号
    private Long enrollStudentPlansId;
    //大学所属城市
    private String city;
    //大学所属省份
    private String provinceName;
    //大学层次
    private List<CollegeLevel> collegeLevelList;
    //学校性质
    private String collegeTypeName;
    //冲守保标识
    private com.junyangcompany.demo.entity.enumeration.ChongShouBao ChongShouBao;
    //概率
    private Integer probability;
    //大学录取分数线（作废）
    private String scoreLines;
    //录取分数线最低分数
    private Integer minScore;
    //录取分数线最低位次
    private Integer minRank;
    //当前年份
    private Integer year;
    //是否已加入备选库  true 是  false 否
    private boolean isChoose;
    //招生计划人数
    private String studentPlan;
    //已选专业数量
    private Long maJorCnt;
    //已选专业集合
    private List<EnrollStudentPlan> enrollStudentPlans;
    //金苹果排名 ranking
    private String ranking;
    //金苹果排名 source
    private String source;
    //是否是公立学校 true 公立  false 私立
    private Boolean isPublic;
    //大学分数线
    List<EnrollCollegeScoreLine> EnrollCollegeScoreLines;
    //招生专业分数线集合  优选专业  大学列表使用
    List<EnrollMajorScoreLine> enrollMajorScoreLines;
    //招生批次
    String batchName;
    Long batchId;

}
