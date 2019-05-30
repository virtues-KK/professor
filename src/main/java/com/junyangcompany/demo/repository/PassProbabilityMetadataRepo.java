package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.PassProbabilityMetadata;
import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zxy
 * @date 2018-11-13 15:29
 */
public interface PassProbabilityMetadataRepo extends JpaRepository<PassProbabilityMetadata, Long>, JpaSpecificationExecutor<PassProbabilityMetadata> {

    @Query("from PassProbabilityMetadata p where p.province.id = :provinceId and p.scienceArt = :scienceArt and :number < p.max order by p.batchSequence,p.min")
    List<PassProbabilityMetadata> findByProvinceAndSA(@Param("provinceId") Long provinceId, @Param("scienceArt") ScienceAndArt scienceAndArt, @Param("number") Integer number);

    @Query("from PassProbabilityMetadata p where p.province.id = :provinceId and p.scienceArt = :scienceArt and p.batchSequence = :batch and p.min >= :number")
    List<PassProbabilityMetadata> findByProvinceAndSAAndBatch(@Param("provinceId") Long provinceId, @Param("scienceArt") ScienceAndArt scienceAndArt, @Param("batch") Long batch, @Param("number") Integer number);


    List<PassProbabilityMetadata> findByProvince_IdAndScienceArtAndEnrollCollegeEnrollBatchIdIn(long provinceId, ScienceAndArt scienceArt, List<Long> collegeIdsList);

    /**
     * 根据省id和文理科和批次大学的对应关系查询录取率
     * @param provinceId 省id
     * @param scienceArt 文理科
     * @param collegeWithBatchMap 一个大学id为值和批次id为键的map
     *                            结果必须要满足这些对应关系中的一种
     * @return 录取率的list
     */
    default List<PassProbabilityMetadata> findByProvince_IdAndScienceArtAndCollegeWithBatch(long provinceId, ScienceAndArt scienceArt, Map<Long, Long> collegeWithBatchMap) {
        return this.findAll((Specification<PassProbabilityMetadata>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<PassProbabilityMetadata, Province> provinceJoin = root.join("province");
            List<Predicate> predicates = new ArrayList<>(3);
            predicates.add(criteriaBuilder.equal(provinceJoin.get("id"), provinceId));
            predicates.add(criteriaBuilder.equal(root.get("scienceArt"), scienceArt));
            if (!collegeWithBatchMap.isEmpty()) {
                Join<PassProbabilityMetadata, EnrollBatch> enrollBatchJoin = root.join("enrollBatch");
                predicates.add(criteriaBuilder.or(collegeWithBatchMap.entrySet().stream().map(entry -> criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("enrollCollegeEnrollBatchId"), entry.getKey()),
                        criteriaBuilder.equal(enrollBatchJoin.get("id"), entry.getValue())
                )).toArray(Predicate[]::new)));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        });
    }

}
