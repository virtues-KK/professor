package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.EnrollBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollBatchRepo extends JpaRepository<EnrollBatch,Long> {

    @Query(value = "from EnrollBatch where name = ?1")
    Optional<EnrollBatch> findByName(String name);

}
