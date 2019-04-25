package com.junyangcompany.demo.bean;

import lombok.Data;

import java.util.List;

@Data
public class ProbabilityCountBean {

    private List<CollegeBatch> colleges;

    private Long majors;

    public class CollegeBatch
    {
        public String batch;
        public Long count;
        public Long batchId;

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public Long getBatchId() {
            return batchId;
        }

        public void setBatchId(Long batchId) {
            this.batchId = batchId;
        }
    }
}
