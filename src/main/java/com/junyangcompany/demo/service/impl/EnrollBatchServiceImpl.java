package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.repository.EnrollBatchRepo;
import com.junyangcompany.demo.service.EnrollBatchService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:9:29
 */
@Service
public class EnrollBatchServiceImpl implements EnrollBatchService {

    private final EnrollBatchRepo enrollBatchRepo;

    @Autowired
    public EnrollBatchServiceImpl(EnrollBatchRepo enrollBatchRepo) {
        this.enrollBatchRepo = enrollBatchRepo;
    }

    @Override
    public Optional<EnrollBatch> findByName(String name) {
        return enrollBatchRepo.findByName(name);
    }
}
