package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

/**
 * author:pan le
 * Date:2019/5/5
 * Time:15:39
 */
public interface ProfessionalBeanRepo extends JpaRepository<ProfessionalEntity,Long> {

    @Query(value = "SELECT * from professional_entity where examinee_id = ?1 ", nativeQuery = true)
    List<ProfessionalEntity> findByExamineeId(Long examineeId);

    @Modifying
    @Query(value = "delete from professional_entity where examinee_id = ?1",nativeQuery = true)
    void deleteAllByExamineeId(Long ExamineeId);


    @Query(value = "select * from professional_entity where examinee_id = ?1",nativeQuery = true)
    List<ProfessionalEntity> findAllByExamineeId(Long examineeId);

    @Modifying
    @Query(value = "delete from professional_entity where enroll_college_enroll_batch in ?1",nativeQuery = true)
    void deleteByEnrollCollegeEnrollBatchId(List<Long> filterDeleteParam);
}
