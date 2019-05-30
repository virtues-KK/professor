package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/5/30
 * Time:11:41
 *  enrollCollegeEnrollBatch 和enrollCollege 的投影类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCollegeEnrollBatchAndEnrollCollegeBean {
    /**
     * enrollCollegeEnrollBatchId
     */
    Long id;
    Integer year;
    Long enrollBatchId;
    Long enrollCollegeId;
    Long enrollProvinceId;
    Long provinceId;
    Long collegeTypeId;
    String name;
    String city;
    Integer planCount;

}
