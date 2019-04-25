package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldx
 * @Description:
 * @Date 2019/1/19 16:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreBean {
    //得分分数
    private Double score;
    //维度编码
    private String dimensionCode;
    //维度名称
    private String dimensionName;
    //维度题目总分
    private Double scoreCnt;
}
