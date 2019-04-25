package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.MajorCategory;
import com.junyangcompany.demo.entity.enumeration.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface MajorCategoryRepo extends JpaRepository<MajorCategory,Long> {

    /*根据专、本科查大类*/
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<MajorCategory> findByGrade(Grade grade);

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Override
    List<MajorCategory> findAll();
}
