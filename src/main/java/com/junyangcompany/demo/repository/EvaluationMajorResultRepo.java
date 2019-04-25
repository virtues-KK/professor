package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EvaluationMajorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author ldx
 * @Description: top20专业的repo
 * @Date 2019/1/18 19:06
 */
public interface EvaluationMajorResultRepo extends JpaRepository<EvaluationMajorResult,Long> {
    /* 查询当前用户的top20专业 */
    Page<EvaluationMajorResult> findAllByUserId(Long userId, Pageable pageable);
    List<EvaluationMajorResult> findAllByUserId(Long userId);

    Boolean existsAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
