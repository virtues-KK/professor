package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ExamineeService {
    /**
     * 添加考生用户
     * @param examinee
     */
    ResponseEntity addExaminee(Examinee examinee);

    /**
     * 删除考生用户
     */
    void deleteExaminee(Examinee examinee);
}
