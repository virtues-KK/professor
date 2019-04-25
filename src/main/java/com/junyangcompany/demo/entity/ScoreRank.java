package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 分数位次表
 * @author zxy
 * @date 2018-11-13 17:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ScoreRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private Integer sort;

    @Enumerated(EnumType.ORDINAL)
    private ScienceAndArt scienceArt;

    private Long provinceId;

}
