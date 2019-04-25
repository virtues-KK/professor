package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EnrollCollegeGuide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * author zhongxin
 * title 招生大学的招生简章
 */

public interface EnrollCollegeGuideRepo extends JpaRepository<EnrollCollegeGuide,Long> {
    /** 按年份查询招生大学的招生简章 */
    @Query(value = "select * from enroll_college_guide  where enroll_college_id = ?1 and year(publish_date) = ?2  order by publish_date desc",nativeQuery = true)
    Page<EnrollCollegeGuide> queryEnrollCollegeGuide(Long enrollCollegeId, Integer year, Pageable pageable);

    /** 招生简章详情 */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    EnrollCollegeGuide findAllByEnrollCollege_IdAndId(Long EnrollCollegeId, Long id);

    @Query(value = "select * from enroll_college_guide  where enroll_college_id = ?1 and id=?2",nativeQuery = true)
    EnrollCollegeGuide findByEnrollCollege_IdAndId(Long enrollCollegeId, Long id);
}
