package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 功能描述: 
 *
 * @param:
 * @return: 
 * @auther: xieyue
 * @date: 2018/12/12 17:48
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateScoreInfo {

    private ScienceAndArt scienceArt;

    /**
     * 位次
     */
    private Integer position;

    /**
     * 修改次数
     */
    private Integer modifyTimes;

    private Double score;

    private long id;

}
