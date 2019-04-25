package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BannerRepo extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {

    /**
     * 根据id  list  查询多条
     *
     * @param ids
     * @return
     */
    List<Banner> findByIdIn(List<Long> ids);


    /**
     * 根据id  list  删除多条
     *
     * @param ids
     * @return
     */
    @Transactional
    List<Banner> deleteByIdIn(List<Long> ids);


    Page<Banner> findByStatus(Integer status, Pageable pageable);
}
