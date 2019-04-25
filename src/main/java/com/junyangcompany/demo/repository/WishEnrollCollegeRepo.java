package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.WishEnrollCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishEnrollCollegeRepo extends JpaRepository<WishEnrollCollege, Long> {
    List<WishEnrollCollege> findAllByUserId(Long userId, Sort sort);
    List<WishEnrollCollege> findAllByUserIdAndEnrollBatch_Id(Long userId, Long batchId, Sort sort);
    @Query(value = "SELECT DISTINCT a.* FROM ( " +
            "SELECT b.id,c.name,b.enroll_batch_id,c.plan_count  FROM college_first_alternative cf LEFT JOIN enroll_college_enroll_batch b ON cf.enroll_college_enroll_batch_id=b.id " +
            "LEFT JOIN enroll_college c ON b.enroll_college_id = c.id " +
            "WHERE cf.user_id = :userId and b.enroll_batch_id= :batchId " +
            "UNION ALL " +
            "SELECT b.id,c.name,b.enroll_batch_id,c.plan_count  FROM major_first_alternative m," +
            "major_first_alternative_colleges cc  LEFT JOIN enroll_college_enroll_batch b ON cc.enroll_college_enroll_batch_id=b.id " +
            "LEFT JOIN enroll_college c ON b.enroll_college_id = c.id " +
            "WHERE m.id=cc.major_first_alternative_id " +
            "AND m.user_id = :userId and b.enroll_batch_id= :batchId)a",nativeQuery = true,
    countQuery ="SELECT count(distinct a.id) FROM ( " +
            "SELECT b.id,c.name,b.enroll_batch_id  FROM college_first_alternative cf LEFT JOIN enroll_college_enroll_batch b ON cf.enroll_college_enroll_batch_id=b.id " +
            "LEFT JOIN enroll_college c ON b.enroll_college_id = c.id " +
            "WHERE cf.user_id = :userId and b.enroll_batch_id= :batchId " +
            "UNION ALL " +
            "SELECT b.id,c.name,b.enroll_batch_id  FROM major_first_alternative m," +
            "major_first_alternative_colleges cc  LEFT JOIN enroll_college_enroll_batch b ON cc.enroll_college_enroll_batch_id=b.id " +
            "LEFT JOIN enroll_college c ON b.enroll_college_id = c.id " +
            "WHERE m.id=cc.major_first_alternative_id " +
            "AND m.user_id = :userId and b.enroll_batch_id= :batchId)a")
    Page<Object[]> getCollegeList(@Param("batchId") Long batchId, @Param("userId") Long userId, Pageable pageable);

    Long countByUserIdAndEnrollCollegeEnrollBatchAndEnrollBatch_Id(Long userId, EnrollCollegeEnrollBatch enrollCollegeEnrollBatch, Long batchIds);


    @Query(value = "SELECT DISTINCT a.* FROM (SELECT cf.enroll_college_enroll_batch_id,ep.id AS major_id,ep.name,ep.code,ep.plan_count " +
            "FROM college_first_alternative cf,college_first_alternative_enroll_student_plans cp,enroll_student_plan ep " +
            "WHERE cp.college_first_alternative_id=cf.id AND cp.enroll_student_plans_id=ep.id " +
            "AND user_id= :userId AND cf.enroll_college_enroll_batch_id= :collegeId AND ep.science_art= :scienceArt " +
            "UNION ALL " +
            "SELECT cf.enroll_college_enroll_batch_id,ep.id AS major_id,ep.name,ep.code,ep.plan_count  " +
            "FROM major_first_alternative mf,major_first_alternative_colleges cf,enroll_student_plan ep " +
            "WHERE mf.id=cf.major_first_alternative_id AND cf.enroll_student_plan_id = ep.id " +
            "AND user_id= :userId " +
            "AND cf.enroll_college_enroll_batch_id= :collegeId )a",
             countQuery = "SELECT count(distinct a.majorId) FROM " +
                     "( SELECT cf.enroll_college_enroll_batch_id,ep.id AS major_id,ep.name,ep.code " +
            "FROM college_first_alternative cf,college_first_alternative_enroll_student_plans cp,enroll_student_plan ep " +
            "WHERE cp.college_first_alternative_id=cf.id AND cp.enroll_student_plans_id=ep.id " +
            "AND user_id= :userId AND cf.enroll_college_enroll_batch_id= :collegeId AND ep.science_art= :scienceArt " +
            "UNION ALL " +
            "SELECT cf.enroll_college_enroll_batch_id,ep.id AS major_id,ep.name,ep.code " +
            "FROM major_first_alternative mf,major_first_alternative_colleges cf,enroll_student_plan ep " +
            "WHERE mf.id=cf.major_first_alternative_id AND cf.enroll_student_plan_id = ep.id  " +
            "AND user_id= :userId " +
            "AND cf.enroll_college_enroll_batch_id= :collegeId )a", nativeQuery = true)
    Page<Object[]> getMajorList(@Param("userId") Long userId, @Param("collegeId") Long collegeId, @Param("scienceArt") Integer scienceArt, Pageable pageable);

    /**
     * 功能描述: 删除志愿专业信息
     */
    @Transactional
    @Modifying
    @Query(value = "delete from wish_enroll_major where id=?1 ",nativeQuery = true)
    int delWishEnrollMajor(Long id);

    /**
     *
     * 功能描述: 查询专业是否已经选择
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/9 14:32
     */
    @Query(value = "SELECT COUNT(1) FROM wish_enroll_college c LEFT JOIN wish_enroll_major m ON  c.id =m.wish_college_id  WHERE c.user_id= :userId AND c.enroll_college_enroll_batch_id= :collegeId " +
            "AND m.enroll_student_plan_id= :majorId ",nativeQuery = true)
    Long countByWishEnrollMajor(@Param("userId") Long userId, @Param("collegeId") Long collegeId, @Param("majorId") Long majorId);
}
