package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Examinee addExaminee(@RequestBody Examinee examinee, HttpServletRequest httpServletRequest){
        Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(examinee.getName());
        if (!matcher.find()){
            throw new RuntimeException("名字必须是中文");
        }
        return examineeService.addExaminee(examinee,httpServletRequest);
    }

    @GetMapping("search")
    public List<Examinee> searchExaminee(HttpServletRequest servletRequest){
        User user = jwtTokenUtil.getUserFromToken(servletRequest);
        List<Examinee> examinees = user.getExaminees();
        return examinees;
    }

    @DeleteMapping
    public void delete(@RequestParam List<Long> examinees,HttpServletRequest servletRequest){
        User userFromToken = jwtTokenUtil.getUserFromToken(servletRequest);
        examineeService.deleteExaminee(examinees,userFromToken);
    }
}
