package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.repository.UserRepo;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:18:07
 */
@RestController
@RequestMapping("examinee")
public class ExamineeController {

    private final ExamineeService examineeService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ExamineeController(ExamineeService examineeService, JwtTokenUtil jwtTokenUtil) {
        this.examineeService = examineeService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("add")
    public void addExaminee(@RequestBody Examinee examinee, HttpServletRequest httpServletRequest){
        examineeService.addExaminee(examinee,httpServletRequest);
    }

    @GetMapping("search")
    public List<Examinee> searchExaminee(HttpServletRequest servletRequest){
        User user = jwtTokenUtil.getUserFromToken(servletRequest);
        List<Examinee> examinees = user.getExaminees();
        return examinees;
    }

    @PostMapping("delete")
    public void delete(@RequestBody List<Examinee> examinees){
        examineeService.deleteExaminee(examinees);
    }

}
