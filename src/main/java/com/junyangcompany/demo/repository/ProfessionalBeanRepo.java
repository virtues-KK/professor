package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/5
 * Time:15:39
 */
public interface ProfessionalBeanRepo extends JpaRepository<ProfessionalEntity,Long> {

    @Query(value = "SELECT * from professional_bean where examinee_id = ?1 ", nativeQuery = true)
    List<ProfessionalEntity> findByExamineeId(Long examineeId);
}
