package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CareerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * @author zxy
 * @date 2018-09-18 10:29
 */
public interface CareerCategoryRepo extends JpaRepository<CareerCategory,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Override
    List<CareerCategory> findAll();

}
