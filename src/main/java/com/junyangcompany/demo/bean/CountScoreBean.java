package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldx
 * @Description: 保存major4个量表所得分bean
 * @Date 2019/1/21 9:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountScoreBean {
    private Long majorId;
    private Double countScore;
}
