package com.junyangcompany.demo.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *
 * 功能描述: 招生大学批次表
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/16 10:37
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EnrollCollegeEnrollBatch {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollege enrollCollege;
    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

}
