package com.junyangcompany.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author zxy
 * @date 2018-09-11 16:18
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Cacheable
public class CareerCategory {

    @Id
    private Long id;

    private String name;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "career_category_id")
    private List<Career> careers;

}