package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.CareerGroupChild;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: CareerGroupParentRepo
 * @Description: 职业族群小类JPA操作
 * @author: chenshui
 * @date: 2018-12-13 16:34:00
 */
public interface CareerGroupChildRepo extends JpaRepository<CareerGroupChild, Long> {

}
