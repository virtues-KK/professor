package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 通过概率元数据
 * @author zxy
 * @date 2018-11-13 15:23
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassProbabilityMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long collegeId;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;

    @Enumerated(EnumType.ORDINAL)
    private ScienceAndArt scienceArt;

    private Long batchSequence;

    @ManyToOne
    @JoinColumn
    private Province province;

    private Integer max;

    private Integer min;

    private Integer stand;

    private Integer mean;

    private Long enrollCollegeEnrollBatchId;

}