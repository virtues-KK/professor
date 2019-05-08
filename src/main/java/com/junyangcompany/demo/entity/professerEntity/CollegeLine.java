package com.junyangcompany.demo.entity.professerEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:10:17
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Integer minScore;
    public Integer minRank;
    public Integer enrollCount;
    public Integer year;

    @ManyToOne
    @JsonIgnore
    private ProfessionalEntity professionalEntity;

    @ManyToOne
    @JoinColumn(name = "planLine_id")
    private PlanLine planLine;
}
