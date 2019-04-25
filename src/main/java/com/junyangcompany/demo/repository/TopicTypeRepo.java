package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 *
 * 功能描述: 
 *
 * @param: 
 * @return: 
 * @auther: xieyue
 * @date: 2018/11/5 11:25
 */
public interface TopicTypeRepo extends JpaRepository<TopicType,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Override
    List<TopicType> findAll();

}
