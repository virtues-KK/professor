package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:15:20
 */
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
    public ResponseEntity addExaminee(Examinee examinee) {
        String authorization = httpRequest.getHeader("Authorization");
        if (Objects.nonNull(authorization)&&authorization.length() >15){
            String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authorization.substring(12));
            User userFromToken = jwtTokenUtil.getUserFromToken(usernameFromToken);
            if (Objects.isNull(userFromToken)){
                examinee.setUser(userFromToken);
                examineeRepo.save(examinee);
                return ResponseEntity.badRequest().body("没有该用户");
            }
            return ResponseEntity.ok("保存成功");
        }
        return new ResponseEntity("token出现问题", HttpStatus.INTERNAL_SERVER_ERROR);
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
