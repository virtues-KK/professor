package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EnrollMajorScoreLine;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollMajorScoreLineRepo extends JpaRepository<EnrollMajorScoreLine,Long>, JpaSpecificationExecutor<EnrollMajorScoreLine> {

    @Query(value = "SELECT min_rank,min_score FROM enroll_major_score_line WHERE " +
            "YEAR= :year " +
            "AND enroll_student_plan_id= :enrollStudentPlanId "
            ,nativeQuery = true)
    List<Object []> queryEnrollMajorScoreLine(@Param("year") Integer year,
                                              @Param("enrollStudentPlanId") Long enrollStudentPlanId);

    List<EnrollMajorScoreLine> findByEnrollStudentPlan_IdAndYearGreaterThan(Long enrollStudentPlanId, Integer year, Sort sort);

    List<EnrollMajorScoreLine> findByEnrollStudentPlan(EnrollStudentPlan enrollStudentPlan);


    @Query("from EnrollMajorScoreLine p where p.enrollStudentPlan.id = :enrollStudentPlanId  and p.year >= :queryYear and p.year < :thisYear order by p.year desc, p.minScore desc")
    List<EnrollMajorScoreLine> findNotContainYearEnrollMajorScoreLine(@Param("enrollStudentPlanId") Long enrollStudentPlanId, @Param("queryYear") Integer queryYear, @Param("thisYear") Integer thisYear);

    /**
     *
     * 功能描述: 包含当年的招生专业分数线数据
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/26 17:54
     */
    @Query("from EnrollMajorScoreLine p where p.enrollStudentPlan.id = :enrollStudentPlanId  and p.year >= :queryYear order by p.year desc, p.minScore desc")
    List<EnrollMajorScoreLine> findContainYearEnrollMajorScoreLine(@Param("enrollStudentPlanId") Long enrollStudentPlanId, @Param("queryYear") Integer queryYear);
//    /**
//     * 院校详情下面的专业分数线-ldx
//     * 2018-12-12
//     */
//    default Page<EnrollMajorScoreLine> findAllByEnrollMajorScoreLine(Long collegeId, Long provinceId, ScienceAndArt scienceAndArt, Integer year, Pageable pageable) {
//        //构建条件规格对象
//        Specification<EnrollMajorScoreLine> specification = (Specification<EnrollMajorScoreLine>) (root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();//条件列表
//            Join<EnrollMajorScoreLine, EnrollStudentPlan> EnrollStudentJoin = root.join(root.getModel().getSingularAttribute("enrollStudentPlan", EnrollStudentPlan.class), JoinType.LEFT);
//            if (collegeId != null){
//                Join<EnrollStudentPlan, College> collegeJoin = EnrollStudentJoin.join("college", JoinType.LEFT);
//                predicates.add(criteriaBuilder.equal(collegeJoin.get("id"),collegeId));
//            }
//            if (provinceId != null){
//                Join<EnrollStudentPlan, Province> provinceJoin = EnrollStudentJoin.join("province", JoinType.LEFT);
//                predicates.add(criteriaBuilder.equal(provinceJoin.get("id"),provinceId));
//            }
//            if (scienceAndArt != null){
//                predicates.add(criteriaBuilder.equal(EnrollStudentJoin.get("scienceArt"),scienceAndArt));
//            }
//            if(year != null){
//                predicates.add(criteriaBuilder.equal(root.get("year"),year));
//            }
//            //将条件接在where子句之后
//            return criteriaQuery
//                    .distinct(true)
//                    .where(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]))
//                    .getRestriction();
//        };
//        return this.findAll(specification, pageable);
//    }
    //查询招生专业历年的分数
    List <EnrollMajorScoreLine> findAllByEnrollStudentPlan_IdAndYearBetween(Long enrollStudentPlanId, Integer maxYear, Integer minYear);

    List <EnrollMajorScoreLine> findByEnrollStudentPlan_Id(Long enrollStudentPlanId, Sort sort);
}
