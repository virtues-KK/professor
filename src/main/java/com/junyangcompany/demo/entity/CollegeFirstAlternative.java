package com.junyangcompany.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.List;

/**
 * 大学优先备选项
 * @author zxy
 * @date 2018-10-23 10:15
 */
@Entity
@Data
public class CollegeFirstAlternative {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    @CreatedBy
    private Long userId;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<EnrollStudentPlan> enrollStudentPlans;

}