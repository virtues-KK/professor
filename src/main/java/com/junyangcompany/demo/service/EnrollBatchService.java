package com.junyangcompany.demo.service;

import com.junyangcompany.demo.entity.EnrollBatch;

import java.util.Optional;

public interface EnrollBatchService {
    Optional<EnrollBatch> findByName(String name);
}
