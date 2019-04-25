package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.Career;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;


public interface CareerRepo extends JpaRepository<Career, Long>, JpaSpecificationExecutor<Career> {

    /**
     * 根据关键词查询专业数量
     *
     * @param keyword 关键词
     * @return 数量
     * @author zxy
     */
    long countByNameContaining(String keyword);

    /**
     * 根据关键词查询
     *
     * @param keyword  关键词
     * @param pageable
     * @return 分页
     * @author zxy
     */
    Page<Career> findAllByNameContaining(String keyword, Pageable pageable);

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE,value = "true"))
    List<Career> findAll();
}