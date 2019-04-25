package com.junyangcompany.demo.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:pan le
 * Date:2019/4/16
 * Time:19:44
 */
@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {

    @PostMapping
    public void demo(){
        log.info("filter.doSomeThing");
    }
}
