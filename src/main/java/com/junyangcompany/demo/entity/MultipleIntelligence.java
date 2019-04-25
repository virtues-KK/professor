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
 * @Description: 多元智能表
 * @Date 2019/1/18 17:22
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MultipleIntelligence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long majorId;

    private Double YY;
    private Double LS;
    private Double SK;
    private Double YJ;
    private Double SD;
    private Double ZZ;
    private Double RJ;
    private Double ZR;

}
