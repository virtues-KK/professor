package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CollegeMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface CollegeMajorRepo extends JpaRepository<CollegeMajor,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<CollegeMajor> findCollegeMajorByCollege_Id(Long id);

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    Long countCollegeMajorByMajor_Id(Long majorId);

    @Query(value = "SELECT ranking,source FROM college_major WHERE college_id= :collegeId AND major_id= :majorId",nativeQuery = true)
    List<Object[]> queryRankingAndSource(@Param("majorId") Long majorId, @Param("collegeId") Long collegeId);
}
