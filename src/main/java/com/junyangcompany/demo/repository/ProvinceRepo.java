package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface ProvinceRepo extends JpaRepository<Province,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Override
    List<Province> findAll();

}
