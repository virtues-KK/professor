package com.junyangcompany.demo.config;

import com.junyangcompany.demo.bean.response.ProfessionalBean;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/5/13
 * Time:10:29
 */
@Component
public class PrepareCollegeData implements CommandLineRunner {

    public static Map<Long, ProfessionalBean> enrollCollegeEnrollBatchMap = null;

    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;

    @Autowired
    public PrepareCollegeData(EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo) {
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (enrollCollegeEnrollBatchMap == null) {
            enrollCollegeEnrollBatchMap = new HashMap<>();
            List<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatches = enrollCollegeEnrollBatchRepo.findAll();
            for (EnrollCollegeEnrollBatch enrollCollegeEnrollBatch : enrollCollegeEnrollBatches) {
                String name = enrollCollegeEnrollBatch.getEnrollCollege().getName();
                Long provinceIdForCollege = enrollCollegeEnrollBatch.getEnrollCollege().getProvince().getId();
                ArrayList<String> strings = new ArrayList<>();
                strings.add(enrollCollegeEnrollBatch.getEnrollBatch().getName());
                ProfessionalBean professionalEntity = new ProfessionalBean();
                professionalEntity.setProvinceIdForCollege(provinceIdForCollege);
                professionalEntity.setBatchNames(strings);
                professionalEntity.setCollegeName(name);
                professionalEntity.setCollegeCode(enrollCollegeEnrollBatch.getEnrollCollege().getCode());
                EnrollCollege enrollCollege = enrollCollegeEnrollBatch.getEnrollCollege();
                if (enrollCollege.getCollegeType() != null)
                    professionalEntity.setType(enrollCollege.getCollegeType().getName());
                professionalEntity.setRank(enrollCollege.getNetRank());
                if (enrollCollege.getCollegeLevel() != null) {
//                    List<CollegeLevel> collegeLevels = collegeLevelService.saveAll(enrollCollege.getCollegeLevel());
//                    professionalEntity.setLevels(collegeLevels);
                    professionalEntity.setLevels(enrollCollege.getCollegeLevel().stream().map(CollegeLevel::getName).collect(Collectors.toList()));
//                    professionalBeanService.save(professionalEntity)
                }
                enrollCollegeEnrollBatchMap.put(enrollCollegeEnrollBatch.getId(), professionalEntity);
            }
        }
    }
}
