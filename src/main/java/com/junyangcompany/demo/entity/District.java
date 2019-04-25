package com.junyangcompany.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @date 2018-08-13 10:43
 * @author:潘乐
 * @description:区域的基本类向上关联城市
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class District {

    /**
     * 区域编号
     */
    @Id
    private Long id;

    /**
     * 区域名
     */
    private String name;

    /**
     * 关联的城市
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;
}
