package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.bean.request.SecondBean;
import com.junyangcompany.demo.entity.professerEntity.Examinee;
import com.junyangcompany.demo.entity.professerEntity.ProfessionalEntity;
import com.junyangcompany.demo.entity.professerEntity.QueryEnrollCollegeMajorBean_demo;
import com.junyangcompany.demo.entity.professerEntity.SecondChoiceEntity;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.repository.SecondChoiceEntityRepo;
import com.junyangcompany.demo.repository.SecondChoiceRepo;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import com.junyangcompany.demo.service.SecondChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/5/7
 * Time:13:54
 */
@Service
@Transactional
public class SecondChoiceServiceImpl implements SecondChoiceService {

    private final SecondChoiceRepo secondChoiceRepo;

    private final JwtTokenUtil jwtTokenUtil;

    private final SecondChoiceEntityRepo secondChoiceEntityRepo;

    private final ExamineeRepo examineeRepo;

    @Autowired
    public SecondChoiceServiceImpl(SecondChoiceRepo secondChoiceRepo, JwtTokenUtil jwtTokenUtil, SecondChoiceEntityRepo secondChoiceEntityRepo, ExamineeRepo examineeRepo) {
        this.secondChoiceRepo = secondChoiceRepo;
        this.jwtTokenUtil = jwtTokenUtil;
        this.secondChoiceEntityRepo = secondChoiceEntityRepo;
        this.examineeRepo = examineeRepo;
    }

    @Override
    public List<QueryEnrollCollegeMajorBean_demo> save(List<QueryEnrollCollegeMajorBean_demo> queryEnrollCollegeMajorBean_demos, HttpServletRequest httpServletRequest) {
        // delete by firstChoiceId and examineeId
        queryEnrollCollegeMajorBean_demos.forEach(queryEnrollCollegeMajorBean_demo -> {
            if (Objects.nonNull(queryEnrollCollegeMajorBean_demo.getProfessionalEntity()) && Objects.nonNull(queryEnrollCollegeMajorBean_demo.getProfessionalEntity().getId())
                    &&Objects.nonNull(queryEnrollCollegeMajorBean_demo.getExaminee()) && Objects.nonNull(queryEnrollCollegeMajorBean_demo.getExaminee().getId())
            )
            secondChoiceRepo.deleteByExamineeAndProfessionalEntity(queryEnrollCollegeMajorBean_demo.getExaminee().getId(),queryEnrollCollegeMajorBean_demo.getProfessionalEntity().getId());
        });
        // 在保存之前校验examineeId是否为当前用户所有
        User user = jwtTokenUtil.getUserFromToken(httpServletRequest);
        List<Examinee> examinees = user.getExaminees();
        List<QueryEnrollCollegeMajorBean_demo> collect = queryEnrollCollegeMajorBean_demos.stream().filter(secondChoice -> examinees.stream().map(Examinee::getId).collect(Collectors.toList()).contains(secondChoice.getExaminee().getId())).collect(Collectors.toList());
        if (collect.size() == 0){
            throw new RuntimeException("当前考生id不存在");
        }
        return secondChoiceRepo.saveAll(queryEnrollCollegeMajorBean_demos);
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public List<QueryEnrollCollegeMajorBean_demo> search(Long examineeId) {
        return secondChoiceRepo.searchsecondChoice(examineeId);
    }

    /**
     * 在重新保存大学的时候同步删除该大学下所有的专业
     * @param firstChoiceId
     */
    @Override
    public void deleteByFirstChoiceId(List<Long> firstChoiceId) {
        secondChoiceRepo.deleteAllByProfessionalEntity(firstChoiceId);
    }

    @Override
    public void saveSecondChoice(List<SecondBean> secondBeans, Long examineeId) {
        List<SecondChoiceEntity> list = new ArrayList<>();
        Examinee examinee = examineeRepo.findById(examineeId).get();
        secondBeans.forEach(secondBean -> {
            SecondChoiceEntity secondChoiceEntity = SecondChoiceEntity.builder().enrollCollegeEnrollBatch(secondBean.getEnrollCollegeEnrollBatch()).enrollCollegeId(secondBean.getEnrollCollegeId()).professionalEntity(ProfessionalEntity.builder()
                    .id(secondBean.getProfessionalEntityId()).build()).enrollStudentPlanId(secondBean.getEnrollStudentPlanId()).examinee(examinee).enrollMajorScoreLineId(secondBean.getEnrollMajorScoreLineId()).build();
            list.add(secondChoiceEntity);
        });
        secondChoiceEntityRepo.deleteByExaminee(Examinee.builder().id(examineeId).build());
        secondChoiceEntityRepo.saveAll(list);
    }
}
