package com.junyangcompany.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/***
 * 招生简章的Bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollGuideBean {
    private Long id;
    private String title;
    private LocalDate publishDate;

}
