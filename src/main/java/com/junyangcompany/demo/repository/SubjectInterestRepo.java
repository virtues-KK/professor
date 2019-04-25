package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.SubjectInterest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ldx
 * @Description: 学科兴趣量表Repo
 * @Date 2019/1/19 9:53
 */
public interface SubjectInterestRepo extends JpaRepository<SubjectInterest,Long> {
}
