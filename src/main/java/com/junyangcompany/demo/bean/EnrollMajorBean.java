package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ldx
 * @Description:招生专业bean
 * @Date 2019/2/19 14:17
 * @TODO Because there is a dream, so dare to rush!
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollMajorBean {
    private Long id;
    private String name;
    private List<EnrollMajorScoreLine> enrollMajorScoreLines;

    public EnrollMajorBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
