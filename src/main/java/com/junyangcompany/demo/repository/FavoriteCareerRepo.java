package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.FavoriteCareer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: MajorRepo
 * @Description: 专业模块repo处理
 * @author: LZA
 * @date: 2018-09-13 15:23
 */
public interface FavoriteCareerRepo extends JpaRepository<FavoriteCareer, Long> {

    /**
     * 根据职业id  用户id  判断 该用户是否关注该职业
     *
     * @param careerId
     * @param userId
     * @return
     * @Author zhongxin
     */
    boolean existsByCareer_IdAndUserId(Long careerId, Long userId);

    /**
     * 根据用户id   职业id来删除 数据     取消关注
     *
     * @param careerId
     * @param userId
     * @Author zhongxin
     */
    @Transactional
    void deleteByCareer_IdAndUserId(Long careerId, Long userId);

    /**
     * 更具userId 分页查询关注职业
     *
     * @param pageable
     * @param userId
     * @return
     * @Author zhongxin
     */
    Page<FavoriteCareer> findByUserId(Pageable pageable, Long userId);

    /**
     * 更具userId 统计关注职业数量
     *
     * @param userId
     * @return
     * @Author zhongxin
     */
    Long countByUserId(Long userId);

    /**
     * @author zxy
     * @date 2018-09-19 14:21
     */
    boolean existsByUserIdAndCareer_Id(long userId, long careerId);

    void deleteByUserIdAndCareer_Id(long userId, long careerId);

}
