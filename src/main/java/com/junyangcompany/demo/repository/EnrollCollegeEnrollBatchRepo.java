package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.bean.EnrollCollegeEnrollBatchAndEnrollCollegeBean;
import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollCollegeEnrollBatchRepo extends JpaRepository<EnrollCollegeEnrollBatch, Long>, JpaSpecificationExecutor<EnrollCollegeEnrollBatch> {
    @Query(value = "SELECT DISTINCT ebc.* FROM enroll_college_enroll_batch ebc LEFT JOIN enroll_college c ON ebc.enroll_college_id = c.id " +
            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
            "LEFT JOIN college_level l ON cl.college_level_id = l.id " +
            "where " +
            "ebc.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',ebc.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) " +
            "order by " +
            "CASE WHEN  2> :sortRule THEN FIELD(ebc.id,:ids) END," +
            "CASE WHEN  3= :sortRule THEN c.app_rank END asc, " +
            "CASE WHEN  2= :sortRule THEN c.net_rank END asc,c.college_initials ASC",
            nativeQuery = true, countQuery = "select COUNT(DISTINCT ebc.id) from " +
            "enroll_college_enroll_batch ebc LEFT JOIN enroll_college c ON ebc.enroll_college_id = c.id " +
            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
            "LEFT JOIN college_level l ON cl.college_level_id = l.id " +
            "where " +
            "ebc.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',ebc.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) " +
            "order by " +
            "CASE WHEN  4= :sortRule THEN c.college_initials  END ASC")
    Page<EnrollCollegeEnrollBatch> queryFirstCollege(@Param("collegeTypeId") String collegeTypeId,
                                                     @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
                                                     @Param("provinceId") String provinceId,
                                                     @Param("provinceIdList") List<Long> provinceIdList,
                                                     @Param("collegeLevelId") String collegeLevelId,
                                                     @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
                                                     @Param("ids") List<Long> ids,
                                                     @Param("enrollBatchId") String enrollBatchId,
                                                     @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
                                                     @Param("sortRule") Integer sortRule,
                                                     Pageable pageable);

    @Query(value = "SELECT DISTINCT ebc.* FROM enroll_college_enroll_batch ebc LEFT JOIN enroll_college c ON ebc.enroll_college_id = c.id " +
            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
            "LEFT JOIN college_level l ON cl.college_level_id = l.id " +
            "where " +
            "ebc.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',ebc.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) " +
            "order by " +
            "CASE WHEN  2> :sortRule THEN FIELD(ebc.id,:ids) END," +
            "CASE WHEN  3= :sortRule THEN c.app_rank END asc, " +
            "CASE WHEN  2= :sortRule THEN c.net_rank END asc,c.college_initials ASC",
            nativeQuery = true, countQuery = "select COUNT(DISTINCT ebc.id) from " +
            "enroll_college_enroll_batch ebc LEFT JOIN enroll_college c ON ebc.enroll_college_id = c.id " +
            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
            "LEFT JOIN college_level l ON cl.college_level_id = l.id " +
            "where " +
            "ebc.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',ebc.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) " +
            "order by " +
            "CASE WHEN  4= :sortRule THEN c.college_initials  END ASC")
    Page<EnrollCollegeEnrollBatch> queryWishAllCollege(@Param("collegeTypeId") String collegeTypeId,
                                                       @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
                                                       @Param("provinceId") String provinceId,
                                                       @Param("provinceIdList") List<Long> provinceIdList,
                                                       @Param("collegeLevelId") String collegeLevelId,
                                                       @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
                                                       @Param("ids") List<Long> ids,
                                                       @Param("enrollBatchId") String enrollBatchId,
                                                       @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
                                                       @Param("sortRule") Integer sortRule,
                                                       Pageable pageable);

    @Query(value = "from EnrollCollegeEnrollBatch where province.id = ?1")
    List<EnrollCollegeEnrollBatch> findEnrollBatchByProVinceId(Long provinceId);

    @Query(value = "from EnrollCollegeEnrollBatch as a where a.id = ?1 ")
    EnrollCollegeEnrollBatch findByEnrollCollegeEnrollBatch(Long bid);

    @Query(value ="SELECT id FROM enroll_college_enroll_batch as a where a.enroll_college_id in (SELECT id from enroll_college as b where b.name LIKE '%'||?1||'%' and enroll_province_id = ?2 )",nativeQuery = true)
    List<Long> findIdByEnrollCollegeName( String enrollCollegeName, Long provinceId);

    @Query(value ="SELECT id FROM EnrollCollegeEnrollBatch as a where a.enrollCollege.id in (SELECT id from EnrollCollege as b where b.name LIKE concat('%',?1,'%') and enrollProvince.id = ?2 )")
    List<Long> findIdByEnrollCollegeName1( String enrollCollegeName, Long provinceId);

}
