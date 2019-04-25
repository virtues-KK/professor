package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 大学与专业关联表
 * 某大学开设的专业与省、文理科和年份有关
 * @author zxy
 * @date 2018-10-18 09:50
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class CollegeMajorInfo {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private College college;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Major major;

    private Integer year;

    @ManyToOne
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

    @Enumerated
    private ScienceAndArt scienceArt;

}
