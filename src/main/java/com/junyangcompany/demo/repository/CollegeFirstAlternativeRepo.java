package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.CollegeFirstAlternative;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


public interface CollegeFirstAlternativeRepo extends JpaRepository<CollegeFirstAlternative,Long>, JpaSpecificationExecutor<CollegeFirstAlternative> {
    /**
     * 大学是否加入备选库状态
     */
    CollegeFirstAlternative findByEnrollCollegeEnrollBatch_IdAndUserId(Long collegeId, Long userId);

    /**
     * 删除大学备选
     */
    @Transactional
    void deleteByEnrollCollegeEnrollBatch_IdAndUserId(Long collegeId, Long userId);
    Long countByUserId(Long id);
    Long countByUserIdAndEnrollCollegeEnrollBatch(Long userId, EnrollCollegeEnrollBatch college);

    @Transactional
    @Modifying
    @Query(value = "insert into college_first_alternative_enroll_student_plans(college_first_alternative_id,enroll_student_plans_id) values(?1,?2)",nativeQuery = true)
    int addCollegeFirstMajor(Long cId, Long majorsId);

    @Query(value = "select * from college_first_alternative_enroll_student_plans where college_first_alternative_id=?1 and enroll_student_plans_id=?2",nativeQuery = true)
    Object queryCollegeFirstMajor(Long cId, Long majorsId);

    @Transactional
    @Modifying
    @Query(value = "delete from college_first_alternative_enroll_student_plans where college_first_alternative_id=?1 and enroll_student_plans_id=?2",nativeQuery = true)
    int delCollegeFirstMajor(Long cId, Long majorsId);

    @Query(value = "select count(1) from college_first_alternative_enroll_student_plans t where t.college_first_alternative_id =?1",nativeQuery = true)
    Long findCountById(Long id);

    @Query(value = "select e.* from college_first_alternative e where e.user_id= :userId  ORDER BY FIELD(e.enroll_college_enroll_batch_id,:ids)",nativeQuery = true,
    countQuery = "select count(1) from college_first_alternative e where e.user_id= :userId ORDER BY FIELD(e.enroll_college_enroll_batch_id,:ids)")
    Page<CollegeFirstAlternative> findAllByUserId(@Param("userId") Long userId, @Param("ids") List<Long> ids, Pageable pageable);

    @Query(value = "select e.enroll_college_enroll_batch_id from college_first_alternative e where e.user_id= :userId  ",nativeQuery = true)
    List<BigInteger> findAllCollegeIdByUserId(@Param("userId") Long userId);

    List<CollegeFirstAlternative> findAllByUserId(Long UserId);

    /**
     *
     * 功能描述: 查询已选择的招生专业编号
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/18 15:56
     */
    @Query(value = "SELECT enroll_student_plans_id FROM college_first_alternative cf INNER JOIN " +
            "college_first_alternative_enroll_student_plans cfe ON cf.id=cfe.college_first_alternative_id " +
            "WHERE cf.user_id= :userId AND cf.enroll_college_enroll_batch_id= :collegeId" ,nativeQuery = true)
    List<BigInteger> getSelectEnrollStudentPlanIds(@Param("collegeId") Long collegeId, @Param("userId") Long userId);

}
