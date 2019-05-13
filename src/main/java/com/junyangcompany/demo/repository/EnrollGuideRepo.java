package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EnrollGuide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;


public interface EnrollGuideRepo extends JpaRepository<EnrollGuide,Long> {
    /** 按年份查询 */
    @Query(value = "select * from enroll_guide  where college_id = ?1 and year(publish_date)=?2  order by publish_date desc",nativeQuery = true)
    Page<EnrollGuide> findByPublishDate_Year(Long collegeId, Integer year, Pageable pageable);

    /** 招生简章详情 */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    EnrollGuide findAllByCollege_IdAndId(Long collegeId, Long id);


    /** 按年份查询招生大学的招生简章 */
    @Query(value = "select g.* from enroll_guide g left join enroll_college_guide eg on g.id=eg.enroll_guide_id where eg.enroll_college_id = ?1 and year(g.publish_date) = ?2  order by g.publish_date desc",nativeQuery = true)
    Page<EnrollGuide> queryEnrollCollegeGuide(Long enrollCollegeId, Integer year, Pageable pageable);

    /**
     * 根据enrollCollegeId查询招生简章
     */
    @Query(value = "SELECT * from enroll_guide a where id in (SELECT enroll_guide_id from enroll_college_guide where enroll_college_id = ?1)",nativeQuery = true)
    List<EnrollGuide> findByEnrollCollegeId(Long enrollCollegeId);
}
