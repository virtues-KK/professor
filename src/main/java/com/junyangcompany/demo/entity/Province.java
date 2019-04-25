package com.junyangcompany.demo.entity;

import com.junyangcompany.demo.entity.enumeration.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * @time:7/19
 * @author:潘乐
 * @description:省份的基本类
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {

    /**
     * 省编号
     */
    @Id
    private Long id;

    /**
     * 省名
     */
    private String name;
    /**
     * 发布状态
     */
    @ColumnDefault("0")
    @Enumerated
    private PublishStatus publishStatus;
}
