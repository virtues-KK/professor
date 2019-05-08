package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondChoiceRepo extends JpaRepository<QueryEnrollCollegeMajorBean_demo,Long> {
}
