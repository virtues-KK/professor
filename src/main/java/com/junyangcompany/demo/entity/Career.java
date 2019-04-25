package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.embed.CareerDetail;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 职业表
 *
 * @author zxy
 * @date 2018-08-02 02:55
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Cacheable
public class Career implements Serializable {

    @Id
    private Long id;
    /**
     * 职业名称
     */
    @Basic(fetch = FetchType.LAZY)
    private String name;

    /**
     * 职业详细信息
     * 在需要的时候触发懒加载获取
     */
    @Embedded
    @Basic(fetch = FetchType.LAZY)
    private CareerDetail careerDetail;

    /**
     * 适合的专业
     */
//    @ManyToMany(fetch = FetchType.LAZY)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private List<Major> majors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private CareerCategory careerCategory;

}