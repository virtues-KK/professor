package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.HollandResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface HollandResultRepo extends JpaRepository<HollandResult, String> {

//    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
//    @Override
//    <S extends HollandResult> S save(S s);

    /**
     * 根据用户id查出二级分类的列表
     */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    HollandResult findByUserId(Long id);

    /**
     * 是否存在某个用户的测评结果
     */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    boolean existsByUserId(Long id);

    /**
     * 根据用户id删除霍兰德结果
     * @param id 用户ID
     */
    void deleteByUserId(Long id);
}
