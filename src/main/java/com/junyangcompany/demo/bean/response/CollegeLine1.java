package com.junyangcompany.demo.bean.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * author:pan le
 * Date:2019/5/8
 * Time:14:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeLine1 implements Serializable {
        public Integer minScore;
        public Integer minRank;
        public Integer enrollCount;
        public Integer year;
        public String batch;
}
