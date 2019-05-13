package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:13:52
 */
public interface SecondChoiceService {
    List<QueryEnrollCollegeMajorBean_demo> save(List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBean_demos, HttpServletRequest httpServletRequest);
    void delete(List<Long> ids);
    List<QueryEnrollCollegeMajorBean_demo> search(Long examineeId);
}
