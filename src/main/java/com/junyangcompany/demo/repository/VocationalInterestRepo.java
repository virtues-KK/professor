package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.VocationalInterest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ldx
 * @Description: 职业兴趣量表Repo
 * @Date 2019/1/19 9:54
 */
public interface VocationalInterestRepo extends JpaRepository<VocationalInterest,Long> {
}
