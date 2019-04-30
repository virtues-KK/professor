package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamineeRepo extends JpaRepository<Examinee,Long> {
}
