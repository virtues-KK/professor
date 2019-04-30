package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.entity.EnrollStudentPlan;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:10:17
 */
@Entity
@Data
public class PlanLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    public EnrollStudentPlan enrollStudentPlan;

    @OneToMany
    public List<CollegeLine> collegeLines;

    @ManyToOne
    private ProfessionalBean professionalBean;
}