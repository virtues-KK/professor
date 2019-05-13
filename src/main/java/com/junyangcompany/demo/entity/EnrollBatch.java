package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 招生批次
 * @author zxy
 * @date 2018-10-25 10:42
 */
@Getter
@Setter
@Entity
@Cacheable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollBatch {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "batchNames", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProfessionalEntity> professionalEntities = new ArrayList<>();

}
