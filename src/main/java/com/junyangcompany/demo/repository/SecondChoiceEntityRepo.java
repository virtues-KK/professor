package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.SecondChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecondChoiceEntityRepo extends JpaRepository<SecondChoiceEntity,Long> {

    @Query(value = "from SecondChoiceEntity where examinee = ?1")
    List<SecondChoiceEntity> findByExaminee(Examinee examinee);

    @Modifying
    @Query(value = "delete from SecondChoiceEntity where examinee = ?1")
    void deleteByExaminee(Examinee examinee);

    @Modifying
    @Query(value = "delete from SecondChoiceEntity where enrollCollegeEnrollBatch in ?1")
    void deleteByEnrollCollegeEnrollBatches(List<Long> filterDeleteParam);
}
