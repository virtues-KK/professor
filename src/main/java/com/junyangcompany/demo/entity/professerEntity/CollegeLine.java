package com.junyangcompany.demo.entity.professerEntity;

import lombok.Data;

import javax.persistence.*;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:10:17
 */
@Entity
@Data
public class CollegeLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Integer minScore;
    public Integer minRank;
    public Integer enrollCount;
    public Integer year;

    @ManyToOne
    private ProfessionalBean professionalBean;

    @ManyToOne
    private PlanLine planLine;
}
