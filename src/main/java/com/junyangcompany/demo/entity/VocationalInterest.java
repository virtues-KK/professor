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
 * @Description: 职业兴趣实体类
 * @Date 2019/1/18 18:44
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VocationalInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long majorId;
    private Double E;
    private Double S;
    private Double C;
    private Double I;
    private Double R;
    private Double A;


}
