package com.junyangcompany.demo.security.mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/4/18
 * Time:16:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class email {
    private String userEmail;
    private String subject;
    private String content;
}
