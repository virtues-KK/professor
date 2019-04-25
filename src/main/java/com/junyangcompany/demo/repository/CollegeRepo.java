package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.bean.QueryCollegeMajorCondition;
import com.junyangcompany.demo.entity.College;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public interface CollegeRepo extends JpaRepository<College, Long>, JpaSpecificationExecutor<College> {

    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    @Override
    List<College> findAll();

    @Query(nativeQuery = true, value = "select id from college where name = ?1")
    Long findIdByName(String name);

    /**
     * 根据用户省份查询所有大学
     */
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    Page<College> findAllByProvince_Id(long id, Pageable pageable);

    @Override
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    Page<College> findAll(Specification<College> specification, Pageable pageable);

    /**
     * 动态过滤大学列表--查询大学界面
     */
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    default Page<College> queryCollege(QueryCollegeMajorCondition condition, Pageable pageable) {
        //构建条件规格对象
        Specification<College> specification = (Specification<College>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();//条件列表
            //过滤学校地区
            if (condition.getProvinceIdList() != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("province").get("id"));
                for (Long provinceId : condition.getProvinceIdList()) {
                    in.value(provinceId);
                }
                predicates.add(criteriaBuilder.and(in));
            }
            //过滤学校性质
            if (condition.getCollegeTypeIdList() != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("collegeType").get("id"));
                for (Long collegeTypeId : condition.getCollegeTypeIdList()) {
                    in.value(collegeTypeId);
                }
                predicates.add(criteriaBuilder.and(in));
            }
            //过滤学校要求
            if (condition.getGradeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("grade"), condition.getGradeId()));
            }
            //过滤类型
            if (condition.getCollegeLevelIdList() != null) {
                Join<College, CollegeLevel> CollegeLevelJoin = root.join(root.getModel().getDeclaredList("collegeLevel", CollegeLevel.class), JoinType.LEFT);
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(CollegeLevelJoin.get("id"));
                for (Long collegeLevelId : condition.getCollegeLevelIdList()) {
                    in.value(collegeLevelId);
                }
                predicates.add(criteriaBuilder.and(in));
            }
            //将条件接在where子句之后
            return criteriaQuery
                    .multiselect(root.get("name"), root.get("id"))
                    .distinct(true)
//                    .groupBy(root.get("name"))
                    .where(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]))
                    .getRestriction();
        };
        return this.findAll(specification, pageable);
    }

    /**
     * 根据关键词统计大学数量
     *
     * @param keyword
     * @return
     */
    long countByNameContaining(String keyword);

    /**
     * 根据关键词查询
     *
     * @param keyword  关键词
     * @param pageable
     * @return 分页
     */
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Page<College> findAllByNameContaining(String keyword, Pageable pageable);

    /**
     * TODO 1128 优化排序  去掉已选置顶功能
     *
     * @Query(value = "select distinct * from " +
     * "(select c.*,f.id AS firstId  from college c " +
     * "left join college_first_alternative f on c.id = f.college_id  " +
     * "left join college_college_level cl on c.id = cl.college_id " +
     * "LEFT JOIN college_enroll_batch cb ON c.id=cb.college_id and cb.province_id= :userProvinceId " +
     * "where c.id in :ids " +
     * "and f.user_id= :userId " +
     * "AND if(:collegeTypeId !='',c.college_type_id= :collegeTypeId, 1=1 ) " +
     * "AND if(:provinceId !='',c.province_id= :provinceId, 1=1 ) " +
     * "AND if(:collegeLevelId !='',cl.college_level_id= :collegeLevelId, 1=1 ) " +
     * "AND if(:enrollBatchId !='',cb.enroll_batch_id= :enrollBatchId, 1=1 ) " +
     * "union all " +
     * "select * from (select DISTINCT c.*,f.id AS firstId  from college c " +
     * "left join college_college_level cl on c.id = cl.college_id " +
     * "left join college_level l on cl.college_level_id = l.id " +
     * "left join college_first_alternative f on c.id = f.college_id and f.user_id= :userId " +
     * "LEFT JOIN college_enroll_batch cb ON c.id=cb.college_id and cb.province_id= :userProvinceId " +
     * "where " +
     * "c.id in :ids " +
     * "AND if(:collegeTypeId !='',c.college_type_id= :collegeTypeId, 1=1 ) " +
     * "AND if(:provinceId !='',c.province_id= :provinceId, 1=1 ) " +
     * "AND if(:enrollBatchId !='',cb.enroll_batch_id= :enrollBatchId, 1=1 ) " +
     * "AND if(:collegeLevelId !='',cl.college_level_id= :collegeLevelId, 1=1 ) order by FIELD(c.id,:ids))b " +
     * ")c ",
     * nativeQuery = true, countQuery = "select COUNT(DISTINCT c.id)  from " +
     * "(select c.*,f.id AS firstId  from college c " +
     * "left join college_first_alternative f on c.id = f.college_id  " +
     * "left join college_college_level cl on c.id = cl.college_id " +
     * "LEFT JOIN college_enroll_batch cb ON c.id=cb.college_id and cb.province_id= :userProvinceId " +
     * "where c.id in :ids " +
     * "and f.user_id= :userId " +
     * "AND if(:collegeTypeId !='',c.college_type_id= :collegeTypeId, 1=1 ) " +
     * "AND if(:provinceId !='',c.province_id= :provinceId, 1=1 ) " +
     * "AND if(:enrollBatchId !='',cb.enroll_batch_id= :enrollBatchId, 1=1 ) " +
     * "AND if(:collegeLevelId !='',cl.college_level_id= :collegeLevelId, 1=1 ) " +
     * "union all " +
     * "select  DISTINCT c.*,f.id AS firstId  from college c " +
     * "left outer join college_college_level cl on c.id = cl.college_id " +
     * "left outer join college_level l on cl.college_level_id = l.id " +
     * "left join college_first_alternative f on c.id = f.college_id and f.user_id= :userId " +
     * "LEFT JOIN college_enroll_batch cb ON c.id=cb.college_id and cb.province_id= :userProvinceId " +
     * "where " +
     * "c.id in :ids " +
     * "AND if(:collegeTypeId !='',c.college_type_id= :collegeTypeId, 1=1 ) " +
     * "AND if(:provinceId !='',c.province_id= :provinceId, 1=1 ) " +
     * "AND if(:enrollBatchId !='',cb.enroll_batch_id= :enrollBatchId, 1=1 ) " +
     * "AND if(:collegeLevelId !='',cl.college_level_id= :collegeLevelId, 1=1 )" +
     * ")c "
     * )
     * Page<College> queryFirstCollege(@Param("userId") String userId, @Param("collegeTypeId") String collegeTypeId,
     * @Param("provinceId") String provinceId, @Param("collegeLevelId") String collegeLevelId,
     * @Param("ids") List<Long> ids, @Param("userProvinceId") Long userProvinceId,@Param("enrollBatchId") String enrollBatchId,
     * Pageable pageable);
     **/
    @Query(value = "select * from enroll_college c where c.id in(select DISTINCT c.id from enroll_college c " +
            "left join enroll_college_college_level cl on c.id = cl.enroll_college_id " +
            "left join college_level l on cl.college_level_id = l.id " +
            "LEFT JOIN enroll_college_enroll_batch cb ON c.id=cb.enroll_college_id and cb.province_id= :userProvinceId " +
            "where " +
            "c.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',cb.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 )) " +
            "order by " +
            "CASE WHEN  2> :sortRule THEN FIELD(c.id,:ids) END," +
            "CASE WHEN  3= :sortRule THEN c.app_rank END asc, " +
            "CASE WHEN  2= :sortRule THEN c.net_rank END asc,c.college_initials ASC",
            nativeQuery = true, countQuery = "select COUNT(DISTINCT c.id) from enroll_college c " +
            "left join enroll_college_college_level cl on c.id = cl.enroll_college_id " +
            "left join college_level l on cl.college_level_id = l.id " +
            "LEFT JOIN enroll_college_enroll_batch cb ON c.id=cb.enroll_college_id and cb.province_id= :userProvinceId " +
            "where " +
            "c.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',cb.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 ) " +
            "order by " +
            "CASE WHEN  4= :sortRule THEN c.college_initials  END ASC")
    Page<EnrollCollege> queryFirstCollege(@Param("collegeTypeId") String collegeTypeId,
                                          @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
                                          @Param("provinceId") String provinceId,
                                          @Param("provinceIdList") List<Long> provinceIdList,
                                          @Param("collegeLevelId") String collegeLevelId,
                                          @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
                                          @Param("ids") List<Long> ids, @Param("userProvinceId") Long userProvinceId,
                                          @Param("enrollBatchId") String enrollBatchId,
                                          @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
                                          @Param("sortRule") Integer sortRule,
                                          Pageable pageable);

    @Query(value = "select * from enroll_college c where c.id in(select distinct c.id  from enroll_college c " +
            "left join enroll_college_college_level cl on c.id = cl.enroll_college_id " +
            "left join college_level l on cl.college_level_id = l.id " +
            "LEFT JOIN enroll_college_enroll_batch cb ON c.id=cb.enroll_college_id and cb.province_id= :userProvinceId " +
            "where " +
            "c.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',cb.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 )) " +
            "ORDER BY " +
            "CASE WHEN  2> :sortRule THEN FIELD(c.id,:ids) END," +
            "CASE WHEN  3= :sortRule THEN c.app_rank END asc," +
            "CASE WHEN  2= :sortRule THEN c.net_rank END asc,c.college_initials ASC ",
            nativeQuery = true, countQuery = "select count(distinct c.id) from enroll_college c " +
            "left join enroll_college_college_level cl on c.id = cl.enroll_college_id " +
            "left join college_level l on cl.college_level_id = l.id " +
            "LEFT JOIN enroll_college_enroll_batch cb ON c.id=cb.enroll_college_id and cb.province_id= :userProvinceId " +
            "where " +
            "c.id in :ids " +
            "AND if(:collegeTypeId !='',c.college_type_id in :collegeTypeIdList, 1=1 ) " +
            "AND if(:provinceId !='',c.province_id in :provinceIdList, 1=1 ) " +
            "AND if(:enrollBatchId !='',cb.enroll_batch_id in :enrollBatchIdList, 1=1 ) " +
            "AND if(:collegeLevelId !='',cl.college_level_id in :collegeLevelIdList, 1=1 )" +
            "order by " +
            "CASE WHEN  4= :sortRule THEN c.college_initials  END ASC")
    Page<EnrollCollege> queryWishAllCollege(@Param("collegeTypeId") String collegeTypeId,
                                            @Param("collegeTypeIdList") List<Long> collegeTypeIdList,
                                            @Param("provinceId") String provinceId,
                                            @Param("provinceIdList") List<Long> provinceIdList,
                                            @Param("collegeLevelId") String collegeLevelId,
                                            @Param("collegeLevelIdList") List<Long> collegeLevelIdList,
                                            @Param("ids") List<Long> ids, @Param("userProvinceId") Long userProvinceId,
                                            @Param("enrollBatchId") String enrollBatchId,
                                            @Param("enrollBatchIdList") List<Long> enrollBatchIdList,
                                            @Param("sortRule") Integer sortRule,
                                            Pageable pageable);

    List<College> findAllByIdBetween(Long max, Long min);
}

