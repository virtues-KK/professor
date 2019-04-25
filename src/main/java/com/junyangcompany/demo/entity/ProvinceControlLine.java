package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 省控线
 * @author zxy
 * @date 2018-11-02 14:58
 */
@Data
@Entity
@Cacheable
public class ProvinceControlLine {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

    private String batchName;

    @ManyToOne
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private AbsoluteBatch absoluteBatch;
    /**
     * 分数线
     */
    @Column(nullable = false)
    private Integer scoreLine;

    @Enumerated
    private ScienceAndArt scienceArt;
    /**
     * 年份
     */
    private Integer year;
}
