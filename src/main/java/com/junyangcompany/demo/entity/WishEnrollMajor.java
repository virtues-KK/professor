package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.Data;

import javax.persistence.*;

/**
 * 志愿表中填的专业
 * @author zxy
 * @date 2018-10-25 15:18
 */
@Entity
@Data
public class WishEnrollMajor {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EnrollStudentPlan enrollStudentPlan;

    /**
     * 此招生展业的录取概率
     */
    private Integer probability;

    @Enumerated
    private ChongShouBao chongShouBao;


    /**
     * 序号用于排序
     */
    @Column(name = "serial_number")
    @OrderBy("serial_number ASC ")
    private Integer number;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wish_college_id")
    private WishEnrollCollege wishEnrollCollege;

}
