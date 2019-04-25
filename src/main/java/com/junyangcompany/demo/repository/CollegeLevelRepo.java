package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CollegeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface CollegeLevelRepo extends JpaRepository<CollegeLevel, Long> {

    @Query(value = "SELECT l.* FROM college_college_level lc LEFT JOIN college_level l ON lc.college_level_id = l.id WHERE lc.college_id= :collegeId", nativeQuery = true)
    List<CollegeLevel> findAllByCollegeId(@Param("collegeId") Long collegeId);

    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    @Override
    List<CollegeLevel> findAll();

}
