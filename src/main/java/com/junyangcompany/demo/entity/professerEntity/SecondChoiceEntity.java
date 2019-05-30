package com.junyangcompany.demo.entity.professerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * author:pan le
 * Date:2019/5/21
 * Time:17:17
 * 精选结果的entity
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecondChoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long enrollStudentPlanId;

    private Long enrollCollegeId;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProfessionalEntity professionalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Examinee examinee;

    private Long enrollCollegeEnrollBatch;

    private Long enrollMajorScoreLineId;

}
