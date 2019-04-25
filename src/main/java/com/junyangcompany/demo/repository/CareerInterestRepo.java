package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.CareerInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CareerInterestRepo extends JpaRepository<CareerInterest, Long>, JpaSpecificationExecutor<CareerInterest> {



}
