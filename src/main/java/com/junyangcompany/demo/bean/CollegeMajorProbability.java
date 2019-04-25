package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeMajorProbability {
    /**
     * 大学编号
     */
    Long collegeId;
    /**
     * 招生计划编号
     */
    Long enrollStudentPlanId;
}
