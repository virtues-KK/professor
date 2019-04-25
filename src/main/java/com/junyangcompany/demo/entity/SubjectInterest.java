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
 * @Description: 学科兴趣量表实体类
 * @Date 2019/1/18 17:41
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SubjectInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long majorId;
    private Double YW;
    private Double WY;
    private Double DL;
    private Double ZZ;
    private Double LS;
    private Double MS;
    private Double SX;
    private Double WL;
    private Double SW;
    private Double HX;
    private Double YY;
    private Double TY;
    private Double JS;

}
