package com.junyangcompany.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.List;

/**
 * 专业优先备选项
 * @author zxy
 * @date 2018-10-23 10:21
 */
@Entity
@Data
public class MajorFirstAlternative {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedBy
    private Long userId;

    @ManyToOne
    private Major major;

    @OneToMany
    @JoinColumn(name = "major_first_alternative_id")
    private List<MajorFirstAlternativeColleges> majorFirstAlternativeColleges;

}
