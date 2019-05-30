package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeScoreLine;
import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface EnrollCollegeScoreLineRepo extends JpaRepository<EnrollCollegeScoreLine,Long>, JpaSpecificationExecutor<EnrollCollegeScoreLine> {
    /**
     * 内容：招生院校分数线接口
     * 作者：Lindx
     * 时间：2018-09-14
     */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<EnrollCollegeScoreLine> findAllByEnrollCollege_IdAndScienceArtAndYearBetween(Long enrollCollege, ScienceAndArt scienceAndArt, Integer queryYear, Integer year);


    Page<EnrollCollegeScoreLine> findTopByEnrollCollege_IdAndProvince_IdAndScienceArt(Long enrollCollegeId, Long provinceId, ScienceAndArt scienceAndArt, Pageable pageable);

    //    default Page<EnrollCollegeScoreLine> queryEnrollCollegeScoreLine(Long enrollCollegeEnrollBatchId, Long provinceId, Long scienceAndArt, Pageable pageable) {
//        //构建条件规格对象
//        Specification<EnrollCollegeScoreLine> specification = (Specification<EnrollCollegeScoreLine>) (root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();//条件列表
//            /** 添加过滤添加 */
//
//            if (enrollCollegeEnrollBatchId != null) {
//                Join<EnrollCollegeScoreLine, College> collegeJoin = root.join("college", JoinType.LEFT);
//                predicates.add(criteriaBuilder.equal(collegeJoin.get("id"), enrollCollegeEnrollBatchId));
//            }
//
//            if (provinceId != null) {
//                predicates.add(criteriaBuilder.equal(root.get("collegeType").get("id"), condition.getCollegeTypeId()));
//            }
//            if (scienceAndArt != null) {
//                predicates.add(criteriaBuilder.equal(root.get("grade"), condition.getGradeId()));
//            }
//            //过滤著名学校条件
//            if (condition.getCollegeLevelId() != null) {
//                predicates.add(criteriaBuilder.equal(CollegeLevelJoin.get("id"), condition.getCollegeLevelId()));
//            }
//            //将条件接在where子句之后
//            return criteriaQuery
//                    .distinct(true)
//                    .where(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]))
//                    .getRestriction();
//        };
//        return this.findAll(specification, pageable);
//    }

    /**
     * 功能描述: 去年院校分数线接口
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/10/30 16:29
     */
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    List<EnrollCollegeScoreLine> findAllByEnrollCollegeAndProvinceAndScienceArtAndYear(EnrollCollege college, Province province, ScienceAndArt scienceAndArt, Integer year);

    List<EnrollCollegeScoreLine> findAllByEnrollCollegeAndProvinceAndScienceArtAndYearGreaterThan(EnrollCollege college, Province province, ScienceAndArt scienceAndArt, Integer year, Sort sort);

    /**
     * 功能描述: 不包含当年的院校分数线数据
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/26 15:56
     */
    @Query("from EnrollCollegeScoreLine p where p.province.id = :provinceId and p.scienceArt = :scienceArt and p.year >= :queryYear and p.year < :thisYear and p.enrollCollegeEnrollBatch.id= :collegeId order by p.year desc, p.minScore desc")
    List<EnrollCollegeScoreLine> findNotContainYearCollegeScoreLine(@Param("provinceId") Long provinceId, @Param("scienceArt") ScienceAndArt scienceAndArt, @Param("collegeId") Long collegeId, @Param("queryYear") Integer queryYear, @Param("thisYear") Integer thisYear);

    /**
     * 功能描述: 包含当年的院校分数线数据
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/26 17:54
     */
    @Query("from EnrollCollegeScoreLine p where p.province.id = :provinceId and p.scienceArt = :scienceArt and p.year >= :queryYear and p.enrollCollegeEnrollBatch.id= :collegeId order by p.year desc, p.minScore desc")
    List<EnrollCollegeScoreLine> findContainYearCollegeScoreLine(@Param("provinceId") Long provinceId, @Param("scienceArt") ScienceAndArt scienceAndArt, @Param("collegeId") Long collegeId, @Param("queryYear") Integer queryYear);

    /**
     * 功能描述: 招生大学的院校分数线数据
     *
     * @param:
     * @return:
     * @auther: zhongxin
     * @date: 2018/12/26 17:54
     */
    @Query("from EnrollCollegeScoreLine p where  p.scienceArt = :scienceArt and p.year >= :queryYear and p.enrollCollegeEnrollBatch.id= :enrollCollegeEnrollBatchId order by p.year desc, p.minScore desc")
    List<EnrollCollegeScoreLine> findByyearAndEnrollCollegeEnrollBatch(@Param("scienceArt") ScienceAndArt scienceAndArt, @Param("enrollCollegeEnrollBatchId") Long enrollCollegeEnrollBatchId, @Param("queryYear") Integer queryYear);

    @Query("from EnrollCollegeScoreLine p where  p.scienceArt = :scienceArt and p.year >= :queryYear and p.enrollCollege.id= :enrollCollegeId order by p.year desc, p.minScore desc")
    List<EnrollCollegeScoreLine> findByYearAndEnrollCollege(@Param("scienceArt") ScienceAndArt scienceAndArt, @Param("enrollCollegeId") Long enrollCollegeId, @Param("queryYear") Integer queryYear);


    @Query(value = "select * from enroll_college_score_line where enroll_college_id = ?1 and science_art = ?2 ",nativeQuery = true)
    List<EnrollCollegeScoreLine> findByEnrollCollegeEnrollBatchAndScienceArt(Long enrollCollegeEnrollBatchId, Integer scienceAndArt);

    @Query(value =
    "from EnrollCollegeScoreLine as a where a.enrollCollegeEnrollBatch.id = ?1 and a.province.id = ?2 and a.scienceArt = ?3"
    )
    List<EnrollCollegeScoreLine> findByEnrollCollegeEnrollBatchAndProvinceAndScienceArt(Long enrollCollegeEnrollBatchId,Long provinceId, ScienceAndArt scienceAndArt);
}
