package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 招生批次
 * @author zxy
 * @date 2018-10-25 10:42
 */
@Data
@Entity
@Cacheable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollBatch {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "batchNames", fetch = FetchType.LAZY)
    private List<ProfessionalBean> professionalBeans = new ArrayList<>();

}
