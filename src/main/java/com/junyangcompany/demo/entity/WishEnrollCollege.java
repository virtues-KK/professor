package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.List;

/**
 * 志愿表中填的大学
 * @author zxy
 * @date 2018-10-25 15:12
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishEnrollCollege {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedBy
    private Long userId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Province province;

    /**
     * 大学的录取批次
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private EnrollBatch enrollBatch;


    @Column(name = "serial_number")
    private Integer number;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    /**
     * 填报的专业
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "wish_college_id")
    @OrderBy("serial_number ASC ")
    private List<WishEnrollMajor> wishEnrollMajors;

    /**
     * 此大学的录取概率
     */
    private Integer probability;
    @Enumerated
    private ChongShouBao chongShouBao;

}
