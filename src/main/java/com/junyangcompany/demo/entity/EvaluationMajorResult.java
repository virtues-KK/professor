package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author ldx
 * @Description: top20专业结果表
 * @Date 2019/1/18 18:51
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationMajorResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long majorId;

    private Long userId;

    private Double score;
}
