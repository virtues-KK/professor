package com.junyangcompany.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.junyangcompany.demo.entity.College;
import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.repository.CollegeRepo;
import com.junyangcompany.demo.repository.EnrollMajorScoreLineRepo;
import com.junyangcompany.demo.repository.ProvinceRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExportRxcelServiceImplTest {

    @Autowired
    private EnrollMajorScoreLineRepo enrollMajorScoreLineRepo;

    @Autowired
    private CollegeRepo collegeRepo;

    @Autowired
    private ProvinceRepo provinceRepo;

    @Test
    public void test() throws Exception {
        List<College> all = collegeRepo.findAll();
        List<String> collect = provinceRepo.findAll().stream().map(Province::getName).collect(Collectors.toList());
        Map<String,List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        File file = new File("college.json");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), StandardCharsets.UTF_8));
        collect.forEach(c->{
            List<String> collect1 = all.stream().filter(college -> college.getProvince().getName().equals(c)).map(College::getName).collect(Collectors.toList());
            map.put(c,collect1);
        });
        objectMapper.writeValue(writer,map);
        writer.close();
    }


    @Test
    public void test1() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("college.json");
        Map map = mapper.readValue(file, Map.class);
        System.out.println(map.keySet().size());
    }


}