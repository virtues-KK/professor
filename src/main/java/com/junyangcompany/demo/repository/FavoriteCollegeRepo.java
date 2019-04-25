package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.FavoriteCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface FavoriteCollegeRepo extends JpaRepository<FavoriteCollege, Long> {
    /**
     * 志愿状态
     */
    Boolean existsByCollege_IdAndUserId(Long collegeId, Long userId);

    /**
     * 删除大学关注
     */
    @Transactional
    void deleteByCollege_IdAndUserId(Long collegeId, Long userId);

    /**
     * 更具userId  分页查询
     *
     * @param pageable
     * @param userId
     * @return
     * @Author zhongxin
     */
    Page<FavoriteCollege> findByUserId(Pageable pageable, Long userId);

    /**
     * 更具userId 查询 关注的大学数量
     *
     * @param userId
     * @return
     * @Author zhongxin
     */
    Long countFavoriteCollegeByUserId(Long userId);
}
