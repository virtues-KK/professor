package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.MajorSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorSubCategoryRepo extends JpaRepository<MajorSubCategory,Long> {

    /**
     * 根据多个霍兰德类型组合去重查询专业子类
     *
     * @param groups 霍兰德类型组合
     * @return 专业子类列表
     * @author zxy
     */
    @Query(nativeQuery = true, value = "select distinct m.* from major_sub_category m " +
            "left join holland_type_group_major_sub_category hm on m.id = hm.major_sub_category_id " +
            "where hm.holland_type_group in ?1 order by m.id")
    List<MajorSubCategory> findByHollandTypeGroups(List<String> groups);

}
