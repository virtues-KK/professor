package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.FavoriteMajor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FavoriteMajorRepo extends JpaRepository<FavoriteMajor, Long> {

    /*专业的关注状态*/
    Boolean existsByMajor_IdAndUserId(Long majorId, Long userId);

    /*删除关注专业*/
    @Transactional
    void deleteByMajor_IdAndUserId(Long majorId, Long userId);

    /**
     * 更具userId 分页查询关注专业
     *
     * @param pageable
     * @param userId
     * @return
     */
    Page<FavoriteMajor> findByUserId(Pageable pageable, Long userId);

    /**
     * 更具userId 统计关注专业数量
     *
     * @param userId
     * @return
     */
    Long countByUserId(Long userId);
}
