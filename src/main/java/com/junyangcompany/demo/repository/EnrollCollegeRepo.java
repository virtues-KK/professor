package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.bean.QueryEnrollCollegeCondition;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldx
 * @Description:
 * @Date 2019/2/18 14:05
 * @TODO Because there is a dream, so dare to rush!
 */
public interface EnrollCollegeRepo extends JpaRepository<EnrollCollege, Long>, JpaSpecificationExecutor<EnrollCollege> {
    /**
     * 根据普通大学id查询招生大学
     */
    List<EnrollCollege> findAllByCollege_IdAndEnrollProvince_Id(Long collegeId, Long provinceId);

    /**
     * 通过code和enrollProvinceId 来
     */



    /**
     * 根据多个条件来动态筛选招生大学
     */
    default Page<EnrollCollege> queryEnrollCollege(QueryEnrollCollegeCondition condition, Pageable pageable) {
        Specification<EnrollCollege> specification = (Specification<EnrollCollege>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (condition.getAppRank() != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("appRank"));
                in.value(condition.getAppRank());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getCollegeLevel().size() != 0) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("collegeLevel"));
                for (CollegeLevel collegeLevel : condition.getCollegeLevel()) {
                    in.value(collegeLevel);
                }
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getProvince() != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("Province").get("id"));
                in.value(condition.getProvince().getId());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getEnrollProvince() != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("enrollProvince").get("id"));
                in.value(condition.getEnrollProvince().getId());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getCollegeType() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("collegeType").get("id"));
                in.value(condition.getCollegeType().getId());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getIsPublic() !=null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("isPublic"));
                in.value(condition.getIsPublic());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getNetRank() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("netRank"));
                in.value(condition.getNetRank());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getKeyDiscipline() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("keyDiscipline"));
                in.value(condition.getKeyDiscipline());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getDoctorStation() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("doctorStation"));
                in.value(condition.getDoctorStation());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getMasterPilot() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("masterPilot"));
                in.value(condition.getMasterPilot());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getNumberOfSpecialty() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("numberOfSpecialty"));
                in.value(condition.getNumberOfSpecialty());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getCreatedDate() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("createdDate"));
                in.value(condition.getCreatedDate());
                predicates.add(criteriaBuilder.and(in));
            }
            if (condition.getKeyLaboratory() != null){
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("keyLaboratory"));
                in.value(condition.getKeyLaboratory());
                predicates.add(criteriaBuilder.and(in));
            }
            return criteriaQuery
                    .multiselect(root.get("name"), root.get("id"))
                    .distinct(true)
//                    .groupBy(root.get("name"))
                    .where(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]))
                    .getRestriction();

        };
        return this.findAll(specification,pageable);

    }
}
