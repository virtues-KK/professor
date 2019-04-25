package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author ldx
 * @Description:
 * @Date 2019/1/19 17:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallZhiyuanReq {
    private Long userId;
    //测评结果
    Map<String, ScoreBean> resultMap;

}
