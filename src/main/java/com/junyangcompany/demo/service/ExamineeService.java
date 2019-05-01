package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.Examinee;

import javax.servlet.http.HttpServletRequest;

public interface ExamineeService {
    /**
     * 添加考生用户
     * @param examinee
     */
    Examinee addExaminee(Examinee examinee, HttpServletRequest httpServletRequest);

    /**
     * 删除考生用户
     */
    void deleteExaminee(Examinee examinee);
}
