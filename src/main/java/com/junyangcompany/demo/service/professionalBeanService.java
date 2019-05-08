package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;

import java.util.List;

public interface professionalBeanService {
    List<ProfessionalEntity> saveAll(List<ProfessionalEntity> professionalEntities);
    List<ProfessionalEntity> searchByExamineeId(Long examineeId);
    ProfessionalEntity save(ProfessionalEntity professionalEntity);

}
