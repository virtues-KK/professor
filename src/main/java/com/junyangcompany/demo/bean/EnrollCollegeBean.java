package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldx
 * @Description: 简化招生大学的bean
 * @Date 2019/2/19 9:27
 * @TODO Because there is a dream, so dare to rush!
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollCollegeBean {
    private Long id;
    private String name;
}
