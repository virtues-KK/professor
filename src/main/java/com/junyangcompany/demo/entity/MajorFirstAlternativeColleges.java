package com.junyangcompany.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * author:panle
 * Date:2018/12/11
 * Time:17:54
 */
@Entity
@Data
public class MajorFirstAlternativeColleges {

    @javax.persistence.Id
    @GeneratedValue
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private EnrollStudentPlan enrollStudentPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private MajorFirstAlternative majorFirstAlternative;

}
