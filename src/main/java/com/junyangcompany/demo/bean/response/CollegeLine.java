package com.junyangcompany.demo.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:pan le
 * Date:2019/5/6
 * Time:16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollegeLine {
        private Integer minScore;
        private Integer minRank;
        private Integer enrollCount;
        private Integer year;
        private String batchName;
}
