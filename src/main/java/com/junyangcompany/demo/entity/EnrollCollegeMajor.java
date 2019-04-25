package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.embed.EnrollCollegeMajorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class EnrollCollegeMajor {

    @EmbeddedId
    private EnrollCollegeMajorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("enrollCollegeEnrollBatchId")
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("majorId")
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Major major;

    private Integer networkRanking;

    private Integer appleRanking;

}