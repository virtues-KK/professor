package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:18:07
 */
@RestController
@RequestMapping("examinee")
public class ExamineeController {

    private final ExamineeService examineeService;

    @Autowired
    public ExamineeController(ExamineeService examineeService) {
        this.examineeService = examineeService;
    }

    @PostMapping("add")
    public void addExaminee(@RequestBody Examinee examinee){
        examineeService.addExaminee(examinee);
    }
}
