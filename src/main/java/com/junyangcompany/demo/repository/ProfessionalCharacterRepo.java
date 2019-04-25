package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.ProfessionalCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ldx
 * @Description: 职业性格量表Repo
 * @Date 2019/1/19 9:52
 */
public interface ProfessionalCharacterRepo extends JpaRepository<ProfessionalCharacter,Long> {
}
