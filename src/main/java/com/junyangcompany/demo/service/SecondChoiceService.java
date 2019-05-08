package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:13:52
 */
public interface SecondChoiceService {
    List<QueryEnrollCollegeMajorBean_demo> save(List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBean_demos);
    void delete(List<Long> ids);
}
