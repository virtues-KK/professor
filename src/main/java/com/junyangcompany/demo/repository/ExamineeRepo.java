package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.security.mapping.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamineeRepo extends JpaRepository<Examinee,Long> {
    @Override
    void deleteInBatch(Iterable<Examinee> iterable);

    @Modifying
    @Query(value = "DELETE from Examinee where id in :along and user = :user ")
    void deleteByIds(@Param("along") List<Long> aLong, @Param("user")User user);

    @Override
    Optional<Examinee> findById(Long aLong);
}
