package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.MajorFirstAlternative;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MajorFirstAlternativeRepo extends JpaRepository<MajorFirstAlternative,Long>, JpaSpecificationExecutor<MajorFirstAlternative> {

    @Query(value = "SELECT mt.id,mt.name,mt.grade,mt.schooling_time,mf.id AS mf_id " +
            "FROM major mt LEFT JOIN major_topic_types mtt ON mt.id=mtt.major_id LEFT JOIN major_first_alternative mf ON mt.id = mf.major_id AND user_id= :userId ," +
            "(SELECT DISTINCT major_id FROM enroll_college_major WHERE enroll_college_enroll_batch_id IN :ids and science_art= :scienceArt)allmajor " +
            "WHERE allmajor.major_id=mt.id " +
            "and mt.grade= :grade " +
            "and if(:majorSubCategoryId !='', mt.major_sub_category_id= :majorSubCategoryId, 1=1 ) " +
            "and if(:topicTypesId !='',mtt.topic_types_id= :topicTypesId, 1=1 ) " +
            "and if(:hollandTypes !='',mt.id in " +
            "(SELECT h.major_id FROM evaluation_major_result h  " +
            "where h.user_id = :userId),1=1) " +
            /** TODO 1128 优化排序  去掉已选置顶功能
            "order by mf.id desc ,mt.id desc ",**/
            "order by mt.id desc ",
    countQuery = "SELECT count(mt.id) " +
            "FROM major mt LEFT JOIN major_topic_types mtt ON mt.id=mtt.major_id LEFT JOIN major_first_alternative mf ON mt.id = mf.major_id AND user_id= :userId ," +
            "(SELECT DISTINCT major_id FROM enroll_college_major WHERE enroll_college_enroll_batch_id IN :ids and science_art= :scienceArt)allmajor " +
            "WHERE allmajor.major_id=mt.id " +
            "and mt.grade= :grade " +
            "and if(:majorSubCategoryId !='', mt.major_sub_category_id= :majorSubCategoryId, 1=1 ) " +
            "and if(:topicTypesId !='',mtt.topic_types_id= :topicTypesId, 1=1 ) " +
            "and if(:hollandTypes !='',mt.id in" +
            "(SELECT h.major_id FROM evaluation_major_result h " +
            "where h.user_id = :userId),1=1) ",
             nativeQuery = true)
    Page<Object[]> queryFirstMajorList(@Param("userId") Long userId, @Param("scienceArt") Integer scienceArt, @Param("ids") List<Long> ids,
                                       @Param("topicTypesId") String topicTypesId, @Param("hollandTypes") String hollandTypes,
                                       @Param("majorSubCategoryId") String majorSubCategoryId,
                                       @Param("grade") Long grade,
                                       Pageable pageable);
    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/12/14 15:50
     */
    @Query(value = "SELECT mt.id,mt.name,mt.grade,mt.schooling_time,mf.id AS mf_id,mt.category_name,mt.sub_category_name " +
            "FROM major mt LEFT JOIN major_topic_types mtt ON mt.id=mtt.major_id LEFT JOIN major_first_alternative mf ON mt.id = mf.major_id AND user_id= :userId ," +
            "(SELECT DISTINCT major_id FROM enroll_college_major WHERE enroll_college_enroll_batch_id IN :ids and science_art= :scienceArt)allmajor " +
            "WHERE allmajor.major_id=mt.id " +
            "and mt.grade= :grade " +
            "and if(:topicTypesId !='',mtt.topic_types_id= :topicTypesId, 1=1 ) " +
            "and if(:hollandTypes !='',mt.id in" +
            "(SELECT h.major_id FROM evaluation_major_result h " +
            "where h.user_id = :userId),1=1) "+
            "order by mt.id desc ",
             nativeQuery = true)
    List<Object[]> queryFirstMajorListToWeb(@Param("userId") Long userId, @Param("scienceArt") Integer scienceArt,
                                            @Param("ids") List<Long> ids,
                                            @Param("topicTypesId") String topicTypesId, @Param("hollandTypes") String hollandTypes,
                                            @Param("grade") Long grade);


    @Query(value = "SELECT count(DISTINCT major_id) FROM enroll_college_major  WHERE enroll_college_enroll_batch_id IN :ids and science_art= :scienceArt ",
            nativeQuery = true)
    Long queryFirstMajorListCount(@Param("scienceArt") Integer scienceArt, @Param("ids") List<Long> ids);

    Long countByUserId(Long id);
    /**
     *
     * 功能描述: 专业是否加入备选库状态
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/5 19:02
     */
    MajorFirstAlternative findByMajorIdAndUserId(Long majorId, Long userId);
    /**
     * 删除大学备选
     */
    @Transactional
    void deleteByMajorIdAndUserId(Long majorId, Long userId);
    /**
     * 功能描述: 查询专业大学备选库大学信息
     */
    @Query(value = "SELECT f.major_first_alternative_id,c.id as collegeId,c.name FROM major_first_alternative_colleges f " +
            "LEFT JOIN college c ON f.enroll_college_id = c.id WHERE major_first_alternative_id=?1",nativeQuery = true)
    List<Object[]> queryMajorFirstChooseCollege(Long id);
    /**
     *
     * 功能描述: 查询常规专业的下招生专业和大学组合
     *
     * @param:
     * majorId: 常规专业
     * collegeTypeId： 大学类型
     * provinceId : 大学省份
     * collegeLevelId ：大学等级
     * enrollBatchId ：大学批次
     * userProvince ：用户志愿所在地
     * year ：年份
     * scienceArt ：文理科标识  0 理科 ：1 专科
     * @return:
     * @auther: xieyue
     * @date: 2018/11/13 16:55
     */
    @Query(value = "SELECT p.id,p.enroll_college_enroll_batch_id as college_id FROM enroll_student_plan p " +
            "LEFT JOIN enroll_college c ON p.enroll_college_id=c.id " +
            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
            "LEFT JOIN college_level l ON cl.college_level_id = l.id " +
            "LEFT JOIN enroll_student_plan_majors em ON p.id=em.enroll_student_plan_id  " +
            "WHERE p.enroll_college_enroll_batch_id in :ids and p.science_art = :scienceArt and em.majors_id = :majorId " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',p.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 )"
            ,nativeQuery = true)
    List<Object[]> queryCollegeIdsByMajorId(@Param("majorId") Long majorId,
                                            @Param("collegeTypeId") String collegeTypeId,
                                            @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
                                            @Param("provinceId") String provinceId,
                                            @Param("provinceIdList") List<Long> provinceIdList,
                                            @Param("collegeLevelId") String collegeLevelId,
                                            @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
                                            @Param("enrollBatchId") String enrollBatchId,
                                            @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
                                            @Param("scienceArt") Integer scienceArt,
                                            @Param("ids") List<Long> ids);
//    @Query(value = "SELECT e.id,c.id as college_id,e.name,c.name as college_name FROM enroll_student_plan e LEFT JOIN enroll_student_plan_majors em ON e.id=em.enroll_student_plan_id " +
//            "INNER JOIN ( SELECT c.id,c.name,c.province_id,c.college_type_id,cl.college_level_id,cb.enroll_batch_id FROM enroll_college c " +
//            "LEFT JOIN enroll_college_college_level cl ON c.id = cl.enroll_college_id " +
//            "LEFT JOIN enroll_college_enroll_batch cb ON c.id=cb.enroll_college_id AND cb.province_id= :userProvince " +
//            "WHERE 1=1 " +
//            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
//            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
//            "AND if(:enrollBatchId !='',cb.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
//            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) GROUP BY c.id )c ON e.enroll_college_id = c.id " +
//            "WHERE em.majors_id= :majorId " +
//            "AND e.province_id= :userProvince " +
//            "AND e.science_art= :scienceArt "
//            ,nativeQuery = true)
//    List<Object[]> queryCollegeIdsByMajorId(@Param("majorId") Long majorId,
//                                            @Param("collegeTypeId") String collegeTypeId,
//                                            @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
//                                            @Param("provinceId") String provinceId,
//                                            @Param("provinceIdList") List<Long> provinceIdList,
//                                            @Param("collegeLevelId") String collegeLevelId,
//                                            @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
//                                            @Param("enrollBatchId") String enrollBatchId,
//                                            @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
//                                            @Param("userProvince") Long userProvince,
//                                            @Param("scienceArt")Integer scienceArt);

    //@Query(value = "SELECT id FROM college WHERE id IN :ids ORDER BY FIELD(id,:ids)",nativeQuery = true)

    /**
     *  根据 招生计划编号，查询大学列表数据
     * @param majorId 专业编号
     * @param userId  用户编号
     * @param ids     招生计划集合
     * @param pageable 分页对象
     * @return
     */
    @Query(value = " SELECT e.id,e.enroll_college_enroll_batch_id AS college_id,e.name,c.name as college_name,c.school_badge,c.code,l.level_names,IFNULL(e.plan_count, 0) AS plan_count, " +
            "(SELECT COUNT(1) FROM major_first_alternative m LEFT JOIN major_first_alternative_colleges ff ON m.id=ff.major_first_alternative_id " +
            "WHERE major_id= :majorId AND m.user_id = :userId AND ff.enroll_college_enroll_batch_id=e.enroll_college_enroll_batch_id AND ff.enroll_student_plan_id=e.id)AS cnt,l.level_ids,c.is_public,ct.name as type_name,c.city,pr.name as province_name " +
            "FROM enroll_student_plan e LEFT JOIN enroll_college c ON e.enroll_college_id = c.id " +
            "LEFT JOIN (SELECT l.enroll_college_id,GROUP_CONCAT(IFNULL(cl.name, ' ')) level_names,GROUP_CONCAT(IFNULL(cl.id, ' ')) level_ids FROM college_level cl " +
            "LEFT JOIN enroll_college_college_level l  ON  cl.id=l.college_level_id  " +
            "GROUP BY l.enroll_college_id )l ON c.id=l.enroll_college_id left join college_type ct on c.college_type_id=ct.id " +
            "left join province pr on c.province_id= pr.id left join enroll_college_major cm on e.enroll_college_enroll_batch_id=cm.enroll_college_enroll_batch_id and cm.major_id = :majorId and cm.science_art = :scienceArt " +
            "WHERE " +
            "e.id IN :ids " +
            /**TODO 1128 优化排序  去掉已选置顶功能
            "ORDER BY cnt DESC,FIELD(e.id,:ids)",**/
            //"ORDER BY FIELD(e.id,:ids)"
            "ORDER BY "+
            "CASE WHEN  2> :sortRule THEN FIELD(e.id,:ids) END," +
            "CASE WHEN  3= :sortRule THEN cm.apple_ranking END asc, " +
            "CASE WHEN  2= :sortRule THEN cm.network_ranking END asc,c.college_initials ASC",
             countQuery =  "SELECT COUNT(1)" +
            " FROM enroll_student_plan e " +
                     "WHERE e.id IN :ids ORDER BY " +
                     "CASE WHEN  0> :sortRule THEN FIELD(e.id,:ids) END",
            nativeQuery = true)
    Page<Object[]> queryFirstMajorCollege(@Param("majorId") Long majorId,
                                          @Param("userId") Long userId,
                                          @Param("ids") List<Long> ids,
                                          @Param("sortRule") Integer sortRule,
                                          @Param("scienceArt") Integer scienceArt,
                                          Pageable pageable);

    Page<MajorFirstAlternative> findAllByUserId(Long UserId, Pageable pageable);

    List<MajorFirstAlternative>  findAllByUserId(Long UserId);


    @Query(value = " SELECT m.id,mf.enroll_college_enroll_batch_id AS collegeid,e.id AS espid,c.name,c.school_badge,e.name AS espname,l.level_names,IFNULL(e.plan_count, 0) AS plan_count,l.level_ids,c.is_public,ct.name as type_name," +
            "c.city,pr.name as province_name " +
            "FROM major_first_alternative m " +
            "INNER JOIN major_first_alternative_colleges mf ON m.id = mf.major_first_alternative_id " +
            "LEFT JOIN enroll_student_plan e ON mf.enroll_student_plan_id = e.id and mf.enroll_college_enroll_batch_id = e.enroll_college_enroll_batch_id " +
            "LEFT JOIN enroll_college c ON e.enroll_college_id =c.id   " +
            "LEFT JOIN (SELECT l.enroll_college_id,GROUP_CONCAT(IFNULL(cl.name, ' ')) level_names,GROUP_CONCAT(IFNULL(cl.id, ' ')) level_ids FROM college_level cl " +
            "LEFT JOIN enroll_college_college_level l  ON  cl.id=l.college_level_id " +
            "GROUP BY l.enroll_college_id )l ON c.id=l.enroll_college_id left join college_type ct on c.college_type_id=ct.id " +
            "left join province pr on c.province_id= pr.id " +
            "WHERE m.major_id= :majorId AND m.user_id= :userId " +
            "order by FIELD(e.id,:ids) ",nativeQuery = true,countQuery = "SELECT COUNT(1) FROM major_first_alternative m " +
            "INNER JOIN major_first_alternative_colleges mf ON m.id = mf.major_first_alternative_id " +
            "WHERE m.major_id= :majorId  AND m.user_id= :userId ")
    Page<Object[]> queryFirstChooseMajorCollege(@Param("userId") Long userId, @Param("majorId") Long majorId, @Param("ids") List<Long> ids,
                                                Pageable pageable);
    /**
     *
     * 功能描述: 查询专业备选库，招生专业大学集合，获取专业概率
     *
     * @param:
     * @return:
     * @auther: xieyue
     * @date: 2018/11/27 16:05
     */
    @Query(value = "SELECT mf.enroll_college_enroll_batch_id,mf.enroll_student_plan_id FROM major_first_alternative m " +
            "INNER JOIN major_first_alternative_colleges mf ON m.id = mf.major_first_alternative_id " +
                    "WHERE m.major_id= :majorId  AND m.user_id= :userId",nativeQuery = true)
    List<Object[]> queryFirstChooseMajorCollegeIds(@Param("userId") Long userId, @Param("majorId") Long majorId);
}
