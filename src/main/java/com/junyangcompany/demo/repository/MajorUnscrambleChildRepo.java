package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.MajorUnscrambleChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MajorUnscrambleChildRepo extends JpaRepository<MajorUnscrambleChild, Long>, JpaSpecificationExecutor<MajorUnscrambleChild> {
    List<MajorUnscrambleChild> findMajorUnscrambleChildByMajorUnscrambleParent_Id(Long id);
}
