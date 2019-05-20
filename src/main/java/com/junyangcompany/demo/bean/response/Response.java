package com.junyangcompany.demo.bean.response;

import com.junyangcompany.demo.entity.EnrollCollege;
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
    String name;
    List<Map<String,Object>> scoreInformation;
    EnrollCollege enrollCollege;
}
