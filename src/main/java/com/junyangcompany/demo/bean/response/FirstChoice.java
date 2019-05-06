package com.junyangcompany.demo.bean.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * author:pan le
 * Date:2019/5/6
 * Time:15:22
 */
@Data
public class FirstChoice {
    private Long enrollCollegeEnrollBatch;
    private String collegeName;
    private String batchName;
    private String collegeCode;
    private Long probability;
    private String type;
    private Long rank;
    private List<String> levels;
    private List<CollegeLine> collegeLines;
    private Long examineeId;
}

