package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.ArticleFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface ArtcleFileRepo extends JpaRepository<ArticleFile, Long>, JpaSpecificationExecutor<ArticleFile> {

    default List<ArticleFile> queryAllByIds(List<Long> ids) {
        //构建条件规格对象
        Specification<ArticleFile> specification = (Specification<ArticleFile>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();//条件列表
            Expression<Long> exp = root.<Long>get("id");
            predicates.add(exp.in(ids)); // 往in中添加所有id 实现in 查询
            //将条件接在where子句之后
            return criteriaQuery
                    .distinct(true)
                    .where(predicates.toArray(new Predicate[predicates.size()]))
                    .getRestriction();
        };
        return this.findAll(specification);
    }

    Page<ArticleFile> findByStatus(Integer status, Pageable pageable);

}
