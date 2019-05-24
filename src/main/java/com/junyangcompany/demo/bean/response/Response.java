package com.junyangcompany.demo.bean.response;

import com.junyangcompany.demo.bean.request.SecondBean;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * author:pan le
 * Date:2019/5/20
 * Time:14:58
 *  majorController里面转格式使用的bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response{
    Long enrollMajorScoreLine_id;
    String name;
    ScienceAndArt scienceAndArt;
    List<Map<String,Object>> scoreInformation;
    Long EnrollStudentPlanId;
    EnrollCollege enrollCollege;
    private Long EnrollCollegeEnrollBatch;
    private SecondBean secondBean;
}
