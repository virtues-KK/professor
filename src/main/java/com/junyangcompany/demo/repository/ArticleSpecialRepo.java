package com.junyangcompany.demo.repository;



import com.junyangcompany.demo.entity.ArticleSpecial;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface ArticleSpecialRepo extends JpaRepository<ArticleSpecial,Long>, JpaSpecificationExecutor<ArticleSpecial> {

    default List<ArticleSpecial> queryAllByIds(List<Long> ids) {
        //构建条件规格对象
        Specification<ArticleSpecial> specification = (Specification<ArticleSpecial>) (root, criteriaQuery, criteriaBuilder) -> {

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
}