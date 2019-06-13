package com.junyangcompany.demo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junyangcompany.demo.bean.response.ProfessionalBean;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.repository.EnrollCollegeEnrollBatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
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

    @Value("classpath:professors.json")
    private Resource resource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public PrepareCollegeData(EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo) {
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

            if (enrollCollegeEnrollBatchMap == null) {
                enrollCollegeEnrollBatchMap = new HashMap<>();
                List<ProfessionalBean> professionalBeans = new ArrayList<>();
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
                    professionalBeans.add(professionalEntity);
                    enrollCollegeEnrollBatchMap.put(enrollCollegeEnrollBatch.getId(), professionalEntity);
                }
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("professionals"));
//            objectOutputStream.writeObject(professionalBeans);
//            objectOutputStream.close();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(new File("professors_new.json"), enrollCollegeEnrollBatchMap);
            }

//        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("professors.json");
//        File file1 = new File("professors.json");
//
//        InputStream inputStream = resourceLoader.getResource("classpath:professors.json").getInputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        enrollCollegeEnrollBatchMap = mapper.readValue(resource.getInputStream(), new TypeReference<Map<Long, ProfessionalBean>>() {});
//        System.out.println(resource.getFilename());
//        System.out.println(enrollCollegeEnrollBatchMap.keySet().size());
    }
}
