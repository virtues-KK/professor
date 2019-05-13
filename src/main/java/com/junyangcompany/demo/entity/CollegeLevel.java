package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class CollegeLevel implements Comparable<CollegeLevel> {

    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinColumn(name = "collegeLevel_id")
    @JsonIgnore
    private List<ProfessionalEntity> professionalEntitys;

    @Override
    public int compareTo(CollegeLevel o) {
        return (int) (this.id - o.getId());
    }
}
