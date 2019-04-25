package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.MajorFirstAlternativeColleges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MajorFirstAlternativeCollegesRepo extends JpaRepository<MajorFirstAlternativeColleges,Long>, JpaSpecificationExecutor<MajorFirstAlternativeColleges> {
    MajorFirstAlternativeColleges findByMajorFirstAlternative_IdAndEnrollCollegeEnrollBatch_IdAndEnrollStudentPlan_Id(Long cId, Long collegeId, Long enrollStudentPlansId);
}
