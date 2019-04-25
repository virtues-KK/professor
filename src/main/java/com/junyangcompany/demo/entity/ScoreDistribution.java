package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 分数分布
 *
 * @author zxy
 * @date 2018-11-06 17:01
 */
@Entity
@Data
@Cacheable
public class ScoreDistribution {

    @GeneratedValue
    @Id
    private Long id;

    /**
     * 省
     */
    @ManyToOne
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;

    /**
     * 最低分
     */
    private Integer minScore;

    /**
     * 最高分
     */
    private Integer maxScore;

    /**
     * 出现最多的分数
     */
    private Integer modeScore;

    /**
     * w文理科
     **/
    @Enumerated
    private ScienceAndArt scienceArt;

}
