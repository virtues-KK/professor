package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *
 * 功能描述: 招生大学招生简章
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/16 10:14
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
public class EnrollCollegeGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollege enrollCollege;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollGuide enrollGuide;
}
