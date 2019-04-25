package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.bean.QueryCollegeMajorInfoCondition;
import com.junyangcompany.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface CollegeMajorInfoRepo extends JpaRepository<CollegeMajorInfo,Long>, JpaSpecificationExecutor {

    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    @Override
    Page findAll(Specification specification, Pageable pageable);

    /**
     * 动态多虑大学列表--开设院校
     */
    default Page<CollegeMajorInfo> queryCollege(QueryCollegeMajorInfoCondition condition, Pageable pageable) {
        //构建条件规格对象
        Specification<CollegeMajorInfo> specification = (Specification<CollegeMajorInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();//条件列表
            Join<CollegeMajorInfo, College> collegeJoin = root.join(root.getModel().getSingularAttribute("college", College.class), JoinType.LEFT);
            /** 专业条件 */
            if (condition.getMajorId() != null) {
                Join<CollegeMajorInfo, Major> majorJoin = root.join(root.getModel().getSingularAttribute("major", Major.class), JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(majorJoin.get("id"), condition.getMajorId()));
            }
            /** 年份条件 */
            if (condition.getYear() != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), condition.getYear()));
            }
            /** 考生信息的省份 */
            if (condition.getStuProvince() != null) {
                Join<CollegeMajorInfo, Province> stuProvinceJoin = root.join(root.getModel().getSingularAttribute("province", Province.class), JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(stuProvinceJoin.get("id"), condition.getStuProvince()));
            }
            /** 文理科条件 */
            if (condition.getScienceAndArt() != null) {
                predicates.add(criteriaBuilder.equal(root.get("scienceArt"), condition.getScienceAndArt()));
            }
            /** 大学所在省份 */
            if (condition.getProvinceId() != null) {
                Join<College, Province> provinceJoin = collegeJoin.join("province", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(provinceJoin.get("id"), condition.getProvinceId()));
            }
            /** 大学类型 */
            if (condition.getCollegeTypeId() != null) {
                Join<College, CollegeType> collegeTypeJoin = collegeJoin.join("collegeType", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(collegeTypeJoin.get("id"), condition.getCollegeTypeId()));
            }
            /** 学校级别 */
            if (condition.getGradeId() != null) {
                predicates.add(criteriaBuilder.equal(collegeJoin.get("grade"), condition.getGradeId()));
            }
            /** 著名学校条件 */
            if (condition.getCollegeLevelId() != null) {
                Join<College, CollegeLevel> collegeLevelJoin = collegeJoin.join("collegeLevel", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(collegeLevelJoin.get("id"), condition.getCollegeLevelId()));
            }
//            Subquery<Long> collegeIdQuery = criteriaQuery.subquery(Long.class);
//            Root<CollegeMajorScoreLine> collegeMajorScoreLineRoot = collegeIdQuery.from(CollegeMajorScoreLine.class);
//            Join<CollegeMajorScoreLine,College> collegeMajorScoreLineCollegeJoin = collegeMajorScoreLineRoot.join("college");
//            collegeIdQuery.groupBy(collegeMajorScoreLineCollegeJoin.get("id"));
//            collegeIdQuery.select(collegeMajorScoreLineRoot.get("id"));
//            CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("id"));
//            in.value(collegeIdQuery);
//            predicates.add(in);
            //将条件接在where子句之后
            return criteriaQuery
                    .distinct(true)
                    .where(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]))
                    .getRestriction();
        };
        return this.findAll(specification,pageable);
    }

}
