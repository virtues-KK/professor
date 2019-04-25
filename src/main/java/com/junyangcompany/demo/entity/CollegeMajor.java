package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.embed.CollegeMajorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class CollegeMajor {

    @EmbeddedId
    private CollegeMajorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("collegeId")
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("majorId")
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Major major;

    //专业标签（特色专业、国家重点）
    @ManyToMany(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<MajorType> majorType;

//    //专业下的院校排名名次
//    private Integer ranking;
//
//    //名次来源
//    private String source;

    private Integer networkRanking;

    private Integer appleRanking;

}