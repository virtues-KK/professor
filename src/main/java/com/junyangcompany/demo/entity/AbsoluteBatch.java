package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 *
 * 功能描述: 绝对批次实体
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/16 10:04
 */
@Data
@Entity
@Cacheable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsoluteBatch {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "absoluteBatch", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<AbsoluteBatchEnrollBatch> enrollBatches;


}
