package com.junyangcompany.demo.bean;

import lombok.Data;

@Data
public class QueryCollegeFirstCondition {

    String provinceIds; //省份id  多个用,隔开

    String collegeTypeIds; //大学类型id

    String collegeLevelIds; //著名学校id

    String chongShouBaos;//冲守保

    String batchIds;//批次编号

    String sortRule;//排序规则  0 录取从低到高（默认）1 录取从高到低  2 校友排名 3 金苹果排名

    Long majorId;//专业id

}
