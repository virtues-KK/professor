package com.junyangcompany.demo.entity.embed;

import com.junyangcompany.demo.entity.City;
import com.junyangcompany.demo.entity.District;
import com.junyangcompany.demo.entity.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * 地址类
 * 可嵌入式对象
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @ManyToOne
    @JoinColumn
    private Province province;//省

    @ManyToOne
    @JoinColumn
    private City city;//市

    @ManyToOne
    @JoinColumn
    private District district;//区

}
