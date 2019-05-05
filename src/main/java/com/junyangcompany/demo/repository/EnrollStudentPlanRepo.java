package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.math.BigInteger;
import java.util.List;


public interface EnrollStudentPlanRepo extends JpaRepository<EnrollStudentPlan, Long> {

    //查大学里面的招生计划
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    List<EnrollStudentPlan> findAllByEnrollCollege_IdAndYearAndProvince_Id(Long collegeId, Integer year, Long provinceId, Sort sort);

    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    List<EnrollStudentPlan> findAllByEnrollCollege_IdAndProvince_Id(Long collegeId, Long provinceId, Sort sort);
    //填志愿里面的查询招生计划
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    List<EnrollStudentPlan> findAllByEnrollCollegeEnrollBatch_IdAndProvince_IdAndYear(Long enrollCollegeId, Long provinceId, int year, Sort sort);

    List<EnrollStudentPlan> findAllByEnrollCollegeEnrollBatch(EnrollCollegeEnrollBatch enrollCollegeEnrollBatch);

    @Query(value = "SELECT name from enroll_student_plan where enroll_college_enroll_batch_id = ?1",nativeQuery = true)
    List<String> getEnrollStudentPlanNameByEnrollCollegeEnrollBatch(Long EnrollCollegeEnrollBatch);

    /**
     * TODO 1128 优化排序  去掉已选置顶功能
     *
     * @Query(value = "SELECT DISTINCT * FROM (  " +
     * "SELECT p.id,p.name,p.code,p.plan_count,p.college_id FROM enroll_student_plan p " +
     * "LEFT JOIN college_first_alternative_enroll_student_plans cf ON p.id=cf.enroll_student_plans_id " +
     * "LEFT JOIN college_first_alternative c ON cf.college_first_alternative_id=c.id " +
     * "WHERE  c.college_id= :collegeId AND c.user_id= :userId " +
     * "UNION ALL  " +
     * "SELECT * FROM ( " +
     * "SELECT DISTINCT p.id,p.name,p.code,p.plan_count,p.college_id FROM  enroll_student_plan p WHERE p.id IN :ids " +
     * "ORDER BY FIELD(p.id,:ids))a" +
     * ")alls ",nativeQuery = true,countQuery = "SELECT COUNT(DISTINCT id) FROM (  " +
     * "SELECT p.id,p.name,p.code,p.plan_count,p.college_id FROM enroll_student_plan p " +
     * "LEFT JOIN college_first_alternative_enroll_student_plans cf ON p.id=cf.enroll_student_plans_id " +
     * "LEFT JOIN college_first_alternative c ON cf.college_first_alternative_id=c.id " +
     * "WHERE  c.college_id= :collegeId AND c.user_id= :userId " +
     * "UNION ALL  " +
     * "SELECT DISTINCT p.id,p.name,p.code,p.plan_count,p.college_id FROM  enroll_student_plan p WHERE p.id IN :ids " +
     * ")alls ")
     * Page<Object[]> findFirstCollegeMajor(@Param("userId")Long userId, @Param("collegeId")Long collegeId,
     * @Param("ids")List<Long>ids, Pageable pageable);
     **/
    @Query(value = "SELECT DISTINCT p.id,p.name,p.code,p.plan_count,p.enroll_college_enroll_batch_id,grade,year_of_study,price FROM  enroll_student_plan p WHERE p.id IN :ids " +
            "ORDER BY FIELD(p.id,:ids)", nativeQuery = true, countQuery = "SELECT COUNT(DISTINCT id) FROM  " +
            "enroll_student_plan p WHERE p.id IN :ids ")
    Page<Object[]> findFirstCollegeMajor(@Param("ids") List<Long> ids, Pageable pageable);

    /**
     * 功能描述:查询专业是否被选择
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/12 17:16
     */
    @Query(value = "SELECT COUNT(1) FROM college_first_alternative_enroll_student_plans cf " +
            "LEFT JOIN college_first_alternative c ON cf.college_first_alternative_id=c.id " +
            "WHERE c.enroll_college_enroll_batch_id= :collegeId AND c.user_id= :userId AND cf.enroll_student_plans_id= :maJorId ",nativeQuery = true)
    Integer countIsChooseMajor(@Param("userId") Long userId, @Param("collegeId") Long collegeId, @Param("maJorId") Long maJorId);
    /**
     * 功能描述: 统计可报考专业总数
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/5 15:51
     */
    //@Query(value = "select count(distinct m.majors_id) from (select id from enroll_student_plan where college_id in :ids)p " +
    //        "left join enroll_student_plan_majors m on p.id=m.enroll_student_plan_id",nativeQuery = true)
    @Query(value = "SELECT COUNT(DISTINCT major_id) FROM college_major WHERE college_id in :ids", nativeQuery = true)
    Integer countMajor(@Param("ids") List<Long> ids);

    /**
     * 功能描述: 查询大学招生总数
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/6 18:56
     */
    @Query(value = "SELECT SUM(plan_count) FROM enroll_student_plan WHERE province_id= :provinceId AND  " +
            "science_art= :scienceArt AND enroll_college_id= :collegeId" ,nativeQuery = true)
    Long sumPlanCount(@Param("scienceArt") Integer scienceArt, @Param("collegeId") Long collegeId,
                      @Param("provinceId") Long provinceId);

    @Query(value = "SELECT majors_id FROM enroll_student_plan_majors WHERE enroll_student_plan_id = :enrollStudentPlanId", nativeQuery = true)
    List<BigInteger> getMajorIds(@Param("enrollStudentPlanId") Long enrollStudentPlanId);

    /**
     * 功能描述: 查询大学全部招生计划 id 集合
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/12 15:27
     */
    @Query(value = "SELECT id FROM enroll_student_plan WHERE  " +
            "science_art= :scienceArt AND enroll_college_enroll_batch_id= :collegeId" ,nativeQuery = true)
    List<BigInteger> getEnrollStudentPlanIds(@Param("scienceArt") Integer scienceArt, @Param("collegeId") Long collegeId);
    /**
     * 功能描述: 查询适合我的招生计划
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/18 15:40
     */
    @Query(value = "SELECT DISTINCT enroll.id FROM (  " +
            "SELECT id FROM enroll_student_plan WHERE science_art= :scienceArt AND enroll_college_enroll_batch_id= :collegeId)enroll " +
            "LEFT JOIN enroll_student_plan_majors ml ON enroll.id=ml.enroll_student_plan_id " +
            "LEFT JOIN major mt ON ml.majors_id = mt.id " +
            "WHERE mt.id IN (SELECT h.major_id FROM evaluation_major_result h where h.user_id = :userId)"
            , nativeQuery = true)
    List<BigInteger> getMyEnrollStudentPlanIds(@Param("scienceArt") Integer scienceArt, @Param("collegeId") Long collegeId,
                                               @Param("userId") Long userId);


    @Query(value = "select e.enroll_student_plan_id from enroll_student_plan_majors e where e.majors_id = ?1", nativeQuery = true)
    List<BigInteger> queryEnrollStudentPlanId(Long majorId);

    @Query(value = "select * from enroll_student_plan where id in(:ids)", nativeQuery = true)
    List<EnrollStudentPlan> queryByIdIn(@Param("ids") List<BigInteger> ids);

    @Query(value = "SELECT YEAR,SUM(plan_count)AS plan_count FROM enroll_student_plan " +
            "WHERE province_id = :provinceId and science_art= :scienceArt AND college_id= :collegeId and year > :year  " +
            "GROUP BY YEAR ORDER BY YEAR ASC ",
            nativeQuery = true)
    List<Object[]> getPlanCountByYear(@Param("year") Integer year, @Param("scienceArt") Integer scienceArt, @Param("collegeId") Long collegeId,
                                      @Param("provinceId") Long provinceId);

    //查询招生大学下面的招生专业
    Page<EnrollStudentPlan> findAllByEnrollCollege_IdAndScienceArtAndProvince_IdAndEnrollBatch_Id(Long enrollCollegeId, ScienceAndArt scienceAndArt, Long provinceId, Long batchId, Pageable pageable);
}
