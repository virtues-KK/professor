package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.security.mapping.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ExamineeService {
    /**
     * 添加考生用户
     * @param examinee
     */
    Examinee addExaminee(Examinee examinee, HttpServletRequest httpServletRequest);

    /**
     * 删除考生用户
     */
    void deleteExaminee(List<Long> examinee_id, User user);

    Optional<Examinee> getExaminee(Long id);
}
