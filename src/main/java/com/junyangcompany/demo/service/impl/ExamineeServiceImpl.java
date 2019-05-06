package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * author:pan le
 * Date:2019/4/30
 * Time:15:20
 */
@Service
@Transactional
public class ExamineeServiceImpl implements ExamineeService {

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
        Examinee save = examineeRepo.save(examinee);
        return save;
    }

    /**
     * 删除考生用户
     *
     * @param
     */
    @Override
    public void deleteExaminee(List<Long> examinee_id, User user) {
        examineeRepo.deleteByIds(examinee_id,user);
    }

    @Override
    public Optional<Examinee> getExaminee(Long id) {
        return examineeRepo.findById(id);
    }
}
