package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalBean;

import java.util.List;

public interface professionalBeanService {
    List<ProfessionalBean> saveAll(List<ProfessionalBean> professionalBeans);
    List<ProfessionalBean> searchByExamineeId(Long examineeId);

}
