package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *
 * 功能描述: 绝对批次招生批次关联表
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2019/2/18 14:41
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class AbsoluteBatchEnrollBatch {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private AbsoluteBatch absoluteBatch;

    @ManyToOne
    @JoinColumn(nullable = false)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollBatch enrollBatch;

}
