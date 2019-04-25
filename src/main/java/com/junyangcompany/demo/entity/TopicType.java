package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 主题类型
 *
 * @Author zx
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class TopicType {

    @Id
    private Long id;

    private String name;    //主题类型名称

    @Override
    public String toString() {
        return "TopicType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
