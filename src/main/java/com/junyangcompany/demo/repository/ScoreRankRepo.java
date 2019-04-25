package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.ScoreRank;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author zxy
 * @date 2018-11-13 17:16
 */
public interface ScoreRankRepo extends JpaRepository<ScoreRank,Long> {

    @Query(value = "select sort from ScoreRank where score = ?1 and scienceArt = ?2 and provinceId = ?3")
    Long findScoreByScoreAndScienceArtAndProvinceId(int score, ScienceAndArt scienceAndArt, long provinceId);

}