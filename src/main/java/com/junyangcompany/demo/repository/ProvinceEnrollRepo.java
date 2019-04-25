package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.ProvinceEnroll;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface ProvinceEnrollRepo extends JpaRepository<ProvinceEnroll,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<ProvinceEnroll> findByAndProvinceControlLine_IdAndYearGreaterThan(Long id, Integer year, Sort sort);
}
