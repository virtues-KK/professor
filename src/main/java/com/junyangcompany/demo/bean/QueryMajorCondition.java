package com.junyangcompany.demo.bean;

import lombok.Data;

@Data
public class QueryMajorCondition {

    Long gradeId;   //大学级别id
    Long categoryId; //专业大类id
    Long subCategoryId;//专业小类id
    String topicTypeId; //主题id
}
