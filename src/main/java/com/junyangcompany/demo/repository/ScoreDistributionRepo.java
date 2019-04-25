package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.ScoreDistribution;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * @Auther: xieyue
 * @Date: 2018/11/2 17:22
 * @Description:
 */
public interface ScoreDistributionRepo extends JpaRepository<ScoreDistribution, Long>, JpaSpecificationExecutor<ScoreDistribution> {
    //List<ProvinceControlLine> findByProvinceIdAndScienceAndArt(Long provinceId, Integer scienceArt, Sort sort);
    //List<ProvinceControlLine> findAllByProvinceAndScienceAndArt(Province province, ScienceAndArt scienceArt);

    /**
     * 根据省份 和文理科查询 分数线
     * @param provinceId
     * @param scienceAndArt
     * @return
     */
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    List<ScoreDistribution> findByProvince_IdAndScienceArt(Long provinceId, ScienceAndArt scienceAndArt);

}
