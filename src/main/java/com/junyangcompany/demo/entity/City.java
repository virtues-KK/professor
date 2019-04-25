package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @className:city
 * @time:7/19
 * @author:潘乐
 * @description:城市的基本类,多对一向上关联省份
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class City {

    /**
     * 城市编号
     */
    @Id
    private Long id;

    /**
     * 城市名
     */
    private String name;

    /**
     * 关联的省份
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Province province;
}

