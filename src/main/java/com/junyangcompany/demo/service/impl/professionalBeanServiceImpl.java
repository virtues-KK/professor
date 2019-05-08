package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import com.junyangcompany.demo.repository.ProfessionalBeanRepo;
import com.junyangcompany.demo.service.professionalBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:pan le
 * Date:2019/5/5
 * Time:16:16
 */
@Service
public class professionalBeanServiceImpl implements professionalBeanService {

    private final ProfessionalBeanRepo professionalBeanRepo;

    @Autowired
    public professionalBeanServiceImpl(ProfessionalBeanRepo professionalBeanRepo) {
        this.professionalBeanRepo = professionalBeanRepo;
    }

    /**
     *  更新并保存
     * @param professionalEntities
     */
    @Override
    @Transactional
    public List<ProfessionalEntity> saveAll(List<ProfessionalEntity> professionalEntities) {
        return professionalBeanRepo.saveAll(professionalEntities);
    }

    @Override
    public List<ProfessionalEntity> searchByExamineeId(Long examineeId) {
        return professionalBeanRepo.findByExamineeId(examineeId);
    }

    @Override
    public ProfessionalEntity save(ProfessionalEntity professionalEntity) {
        return professionalBeanRepo.save(professionalEntity);
    }
}
