package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.Grade;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: MajorCategory
 * @Description: 专业大类（实体类）
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
public class MajorCategory implements Serializable {

    //专业大类ID
    @Id
    private Long id;

    //专业大类名称
    private String name;

    //专业子类
    @OneToMany(
            mappedBy = "majorCategory",
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<MajorSubCategory> majorSubCategories;

    /**
     * 年级标识
     * 本科、专科
     */
    private Grade grade;

}
