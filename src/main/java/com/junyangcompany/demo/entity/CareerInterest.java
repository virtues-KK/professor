package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @ClassName: CareerGroupParent
 * @Description: 职业兴趣分类
 * @author: chenshui
 * @date: 2018-12-13 16:34:00
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerInterest {
    // 兴趣ID
    @Id
    private Long id;
    // 兴趣名称
    private String name;
    // 兴趣英文名称
    private String englishName;
    // 兴趣标签图标
    private String picture;

    // 职业列表
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Career> careers;


}
