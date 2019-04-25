package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.Major;
import com.junyangcompany.demo.entity.TopicType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: MajorRepo
 * @Description: 专业模块repo处理
 * @author: LZA
 * @date: 2018-09-13 15:23
 */
public interface MajorRepo extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {

    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE,value = "true"))
    List<Major> findAll();

    @Query(value = "select m.name from major m where m.id = ?1",nativeQuery = true)
    String queryMajorName(Long majorId);

    /**
     * 根据关键词查询专业数量
     *
     * @param keyword 关键词
     * @return 数量
     * @author zxy
     */
    long countByNameContaining(String keyword);

    /**
     * 根据关键词查询
     *
     * @param keyword  关键词
     * @param pageable
     * @author zxy
     * @return 分页
     */
    Page<Major> findAllByNameContaining(String keyword, Pageable pageable);

    /**
     * 统计包含与某个职业相关的专业数量
     *
     * @author zxy
//     * @param career 指定职业
     * @return 数量
     */
//    long countByCareersContaining(Career career);

    Long countByMajorSubCategory_Id(Long id);

    /**
     * 根据专业小类查询专业
     */
    Page<Major> findByMajorSubCategory_Id(Long subCategoryId, Pageable pageable);

    /**
     * 根据topictype类别查专业
     */
    default List<Major> queryMajorByCategory(Long type) {
        Specification<Major> specification = new Specification<Major>() {
            @Override
            public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //类别 适合男生 女生 判断
                if (type == 1003 || type == 1004) {
                    Join<Major, TopicType> majorTopicTypeJoin = root.join(root.getModel().getList("topicTypes", TopicType.class), JoinType.LEFT);
                    predicateList.add(criteriaBuilder.equal(majorTopicTypeJoin.get("id"), type));
                }
                return query
                        .distinct(true)
                        .where(predicateList.toArray(new Predicate[predicateList.size()]))
                        .getRestriction();
            }
        };
        return this.findAll(specification);
    }
}
