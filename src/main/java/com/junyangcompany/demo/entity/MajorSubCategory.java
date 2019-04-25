package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: MajorSubCategory
 * @Description: 专业子类（实体类）
 * @author: LZA
 * @date: 2018-08-02 14:55:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Cacheable
public class MajorSubCategory implements Serializable {

    //专业子类id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //专业子类名称
    private String name;

    //专业
    @OneToMany(
            mappedBy = "majorSubCategory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<Major> majors;

    //专业大类
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnore
    private MajorCategory majorCategory;

    /**
     * 对应的霍兰德组合
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "holland_type_group_major_sub_category")
    @Column(name = "holland_type_group",length = 200)
    private List<String> hollandTypeGroups;

}
