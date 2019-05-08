package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.repository.SecondChoiceRepo;
import com.junyangcompany.demo.service.SecondChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:13:54
 */
@Service
public class SecondChoiceServiceImpl implements SecondChoiceService {

    private final SecondChoiceRepo secondChoiceRepo;

    @Autowired
    public SecondChoiceServiceImpl(SecondChoiceRepo secondChoiceRepo) {
        this.secondChoiceRepo = secondChoiceRepo;
    }

    @Override
    public List<QueryEnrollCollegeMajorBean_demo> save(List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBean_demos) {
         return secondChoiceRepo.saveAll(queryEnrollCollegeMajorBean_demos);
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
