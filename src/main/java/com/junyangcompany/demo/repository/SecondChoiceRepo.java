package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecondChoiceRepo extends JpaRepository<QueryEnrollCollegeMajorBean_demo,Long> {

    @Query(value = "select * from Query_Enroll_College_Major_Bean_demo where examinee_id = :examineeIds",nativeQuery = true)
    List<QueryEnrollCollegeMajorBean_demo> searchsecondChoice(Long examineeIds);

    @Modifying
    @Query(value = "DELETE from query_enroll_college_major_bean_demo where examinee_id = ?1 and professional_entity_id = ?2",nativeQuery = true)
    void deleteByExamineeAndProfessionalEntity(Long examineeId,Long professionalEntityId);

    @Modifying
    @Query(value = "DELETE from query_enroll_college_major_bean_demo WHERE professional_entity_id in ?1",nativeQuery = true)
    void deleteAllByProfessionalEntity(List<Long> ids);

    @Modifying
    @Query(value = "DELETE from query_enroll_college_major_bean_demo where professional_entity_id in (SELECT id from professional_entity where enroll_college_enroll_batch = ?1)\n",nativeQuery = true)
    void deleteByFirstChoiceEnrollCollegeEnrollId(List<Long> enrollCollegeEnrollBatchId);
}
