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
 * @Description: 职业性格量表实体类
 * @Date 2019/1/18 18:47
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long majorId;

    private Double IE;

    private Double SN;

    private Double FT;

    private Double PJ;

}
