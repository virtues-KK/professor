package com.junyangcompany.demo.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/5/22
 * Time:9:43
 * 保存精选结果bean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecondBean {
    Long enrollStudentPlanId;
    Long enrollCollegeId;
    Long professionalEntityId;
    Long examineeId;
    Long enrollCollegeEnrollBatch;
    Long enrollMajorScoreLineId;
}
