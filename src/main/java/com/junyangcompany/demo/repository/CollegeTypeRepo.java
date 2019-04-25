package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CollegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface CollegeTypeRepo extends JpaRepository<CollegeType,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Override
    List<CollegeType> findAll();
}
