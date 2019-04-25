package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.WishEnrollCollege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 功能描述: 简化志愿列表的bean
 *
 * @param:
 * @return:
 * @auther: xieyue
 * @date: 2018/11/2 17:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeApplyBatchSettingBean {
    //招生批次信息对象
    private EnrollBatch enrollBatch;
    //已选志愿大学集合
    private List<WishEnrollCollege> wishEnrollColleges;
    /**
     * 可填报大学数量
     */
    private Integer collegeNumber;
    /**
     * 可填报专业数量
     */
    private Integer majorNumber;
    /**
     * 是否默认展开
     */
    private boolean isShow;


}
