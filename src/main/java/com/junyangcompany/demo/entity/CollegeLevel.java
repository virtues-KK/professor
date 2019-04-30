package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    @ManyToOne
    private ProfessionalBean professionalBean;

    @Override
    public int compareTo(CollegeLevel o) {
        return (int) (this.id - o.getId());
    }
}
