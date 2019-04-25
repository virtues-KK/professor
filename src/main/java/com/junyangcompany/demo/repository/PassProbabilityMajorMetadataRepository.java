package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.PassProbabilityMajorMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author zxy
 * @date 2018-12-10 18:18
 */
public interface PassProbabilityMajorMetadataRepository extends JpaRepository<PassProbabilityMajorMetadata,Long> {

    List<PassProbabilityMajorMetadata> findAllByEnrollStudentPlan_IdIn(Collection<Long> enrollStudentPlan_id);

}
