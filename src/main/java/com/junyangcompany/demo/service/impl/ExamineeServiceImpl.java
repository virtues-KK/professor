package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:15:20
 */
@Service
public class ExamineeServiceImpl implements ExamineeService {

    private HttpServletRequest httpRequest;

    private final ExamineeRepo examineeRepo;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ExamineeServiceImpl(ExamineeRepo examineeRepo, JwtTokenUtil jwtTokenUtil) {
        this.examineeRepo = examineeRepo;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * 添加考生用户,需要绑定当前系统用户
     *
     * @param examinee
     */
    @Override
    public Examinee addExaminee(Examinee examinee,HttpServletRequest httpServletRequest) {
        User userFromToken = jwtTokenUtil.getUserFromToken(httpServletRequest);
        examinee.setUser(userFromToken);
        examineeRepo.save(examinee);
        return examinee;
    }

    /**
     * 删除考生用户
     *
     * @param examinee
     */
    @Override
    public void deleteExaminee(Examinee examinee) {

    }
}
