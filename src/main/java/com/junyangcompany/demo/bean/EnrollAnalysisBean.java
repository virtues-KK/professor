package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xieyue
 * @Date: 2018/12/18 11:27
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollAnalysisBean {
    //年份
    private String year;
    //数量
    private String cnt;
}
