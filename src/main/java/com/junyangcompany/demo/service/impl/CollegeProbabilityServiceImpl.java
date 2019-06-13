package com.junyangcompany.demo.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.junyangcompany.demo.bean.CollegeMajorProbability;
import com.junyangcompany.demo.bean.CollegeProbability;
import com.junyangcompany.demo.bean.EnrollStudentPlanProbability;
import com.junyangcompany.demo.bean.ProbabilityCountBean;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.Major;
import com.junyangcompany.demo.entity.PassProbabilityMajorMetadata;
import com.junyangcompany.demo.entity.PassProbabilityMetadata;
import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import com.junyangcompany.demo.repository.*;
import com.junyangcompany.demo.service.CollegeProbabilityService;
import com.junyangcompany.demo.utils.DistributionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollegeProbabilityServiceImpl implements CollegeProbabilityService {


    private final ScoreRankRepo scoreRankRepo;

    private final PassProbabilityMetadataRepo passProbabilityMetadataRepo;

    private final EnrollStudentPlanRepo enrollStudentPlanRepo;

    private final EnrollBatchRepo enrollBatchRepo;

    private final CollegeMajorRepo collegeMajorRepo;
    private final MajorFirstAlternativeRepo majorFirstAlternativeRepo;
    private final EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo;
    private final PassProbabilityMajorMetadataRepository passProbabilityMajorMetadataRepository;


    private final static int numberOfBao = 5;

    private static Map<Long, Set<Major>> validMajors = null;

    private List<EnrollCollegeEnrollBatch> enrollCollegeEnrollBatches = null;

    @Autowired
    public CollegeProbabilityServiceImpl(ScoreRankRepo scoreRankRepo, PassProbabilityMetadataRepo passProbabilityMetadataRepo, EnrollStudentPlanRepo enrollStudentPlanRepo, EnrollBatchRepo enrollBatchRepo, CollegeMajorRepo collegeMajorRepo, MajorFirstAlternativeRepo majorFirstAlternativeRepo, EnrollCollegeEnrollBatchRepo enrollCollegeEnrollBatchRepo, PassProbabilityMajorMetadataRepository passProbabilityMajorMetadataRepository) {
        this.scoreRankRepo = scoreRankRepo;
        this.passProbabilityMetadataRepo = passProbabilityMetadataRepo;
        this.enrollStudentPlanRepo = enrollStudentPlanRepo;
        this.enrollBatchRepo = enrollBatchRepo;
        this.collegeMajorRepo = collegeMajorRepo;
        this.majorFirstAlternativeRepo = majorFirstAlternativeRepo;
        this.enrollCollegeEnrollBatchRepo = enrollCollegeEnrollBatchRepo;
        this.passProbabilityMajorMetadataRepository = passProbabilityMajorMetadataRepository;
    }

    @Override
    public List<CollegeProbability> getAll(Long provienceId, ScienceAndArt scienceAndArt, Long sequence, List<ChongShouBao> chongShouBaos, Boolean desc)
    {
        List<CollegeProbability> collegeProbabilities =  new ArrayList<>();

        Long seq = sequence;

        if(seq == null)
            return collegeProbabilities;

        if(seq == 1) {
            List<PassProbabilityMetadata> patched = passProbabilityMetadataRepo.findByProvinceAndSAAndBatch(provienceId,scienceAndArt,1001l,seq.intValue());
            for(PassProbabilityMetadata passProbabilityMetadata : patched)
            {
                CollegeProbability collegeProbability = new CollegeProbability();
                collegeProbability.setCollegeId(passProbabilityMetadata.getEnrollCollegeEnrollBatchId());
                collegeProbability.setProbalility(99);
                collegeProbability.generateChongShouBao();
                collegeProbability.setBatchSeq(passProbabilityMetadata.getBatchSequence());
                collegeProbability.setBatchId(passProbabilityMetadata.getEnrollBatch().getId());
                if(collegeProbability.getChongShouBao() != null && (chongShouBaos == null || chongShouBaos.contains(collegeProbability.getChongShouBao())))
                    collegeProbabilities.add(collegeProbability);
            }
        }
        else
        {
            List<PassProbabilityMetadata> passProbabilityMetadataList = passProbabilityMetadataRepo.findByProvinceAndSA(provienceId,scienceAndArt,seq.intValue());
            System.out.println(passProbabilityMetadataList.size());

            DistributionUtil distributionUtil = new DistributionUtil();

            Long lastBatch = null;

            int bao = 0;

            for(PassProbabilityMetadata passProbabilityMetadata : passProbabilityMetadataList)
            {
                if(lastBatch != null && !passProbabilityMetadata.getBatchSequence().equals(lastBatch))
                {
                    System.out.println("ignore batch:" + passProbabilityMetadata.getBatchSequence());
                    break;
                }

                CollegeProbability collegeProbability = new CollegeProbability();
                collegeProbability.setCollegeId(passProbabilityMetadata.getEnrollCollegeEnrollBatchId());
                collegeProbability.setProbalility(100-new Double(distributionUtil.NORMSDIST((seq-passProbabilityMetadata.getMean())/passProbabilityMetadata.getStand().doubleValue()) * 100).intValue());
                collegeProbability.generateChongShouBao();
                collegeProbability.setBatchSeq(passProbabilityMetadata.getBatchSequence());
                collegeProbability.setBatchId(passProbabilityMetadata.getEnrollBatch().getId());
                if(collegeProbability.getChongShouBao().equals(ChongShouBao.BAO) && lastBatch == null)
                    bao++;
                if(bao == numberOfBao)
                    lastBatch = passProbabilityMetadata.getBatchSequence();

                if(collegeProbability.getChongShouBao() != null && (chongShouBaos == null || chongShouBaos.contains(collegeProbability.getChongShouBao())))
                    collegeProbabilities.add(collegeProbability);
            }
            System.out.println(lastBatch);
        }
        if(desc == null || !desc)
            collegeProbabilities.sort((CollegeProbability p1, CollegeProbability p2) ->p1.getProbalility().compareTo(p2.getProbalility()));
        else
            collegeProbabilities.sort((CollegeProbability p1, CollegeProbability p2) ->p2.getProbalility().compareTo(p1.getProbalility()));
        System.out.println(collegeProbabilities.size());
        return collegeProbabilities;
//        List<CollegeProbability> collegeProbabilities =  new ArrayList<>();
//        for(int i = 0; i < 100; i++)
//        {
//            CollegeProbability collegeProbability = new CollegeProbability();
//            collegeProbability.setCollegeId(enrollCollegeEnrollBatches.get(i).getId());
//            collegeProbability.setProbalility(i+1);
//            collegeProbability.generateChongShouBao();
//            collegeProbability.setBatchSeq(new Long(i%4));
//
//            if(collegeProbability.getChongShouBao() != null && (chongShouBaos == null || chongShouBaos.contains(collegeProbability.getChongShouBao())))
//                collegeProbabilities.add(collegeProbability);
//        }
//        return collegeProbabilities;
    }

    @Override
    public List<CollegeProbability> getList(Long provienceId,ScienceAndArt scienceAndArt, Long sequence, List<Long> collegeIds,ChongShouBao chongShouBao, Boolean desc)
    {
        if(collegeIds == null || collegeIds.size() == 0)
            return null;
        List<CollegeProbability> collegeProbabilities =  new ArrayList<>();
//        for(int i = 0; i < collegeIds.size() ; i++)
//        {
//            CollegeProbability collegeProbability = new CollegeProbability();
//            collegeProbability.setCollegeId(collegeIds.get(i));
//            collegeProbability.setProbalility(i+50);
//            collegeProbability.generateChongShouBao();
//            collegeProbability.setBatchSeq(new Long(i%4));
//
//
//        }

        Long seq = sequence;


        if(seq == null) {
            for(Long collegeId: collegeIds)
            {
                CollegeProbability collegeProbability = CollegeProbability.builder().collegeId(collegeId).probalility(1).build();
                collegeProbability.generateChongShouBao();
                if(collegeProbability.getChongShouBao() != null && (chongShouBao == null || chongShouBao.equals(collegeProbability.getChongShouBao())))
                    collegeProbabilities.add(collegeProbability);
            }
            return collegeProbabilities;
        }

        List<PassProbabilityMetadata> passProbabilityMetadataList = passProbabilityMetadataRepo.findByProvince_IdAndScienceArtAndEnrollCollegeEnrollBatchIdIn(provienceId,scienceAndArt,collegeIds);
        System.out.println(passProbabilityMetadataList.size());

        DistributionUtil distributionUtil = new DistributionUtil();

        for(PassProbabilityMetadata passProbabilityMetadata : passProbabilityMetadataList)
        {
            CollegeProbability collegeProbability = new CollegeProbability();
            collegeProbability.setCollegeId(passProbabilityMetadata.getEnrollCollegeEnrollBatchId());
            collegeProbability.setProbalility(100-new Double(distributionUtil.NORMSDIST((seq-passProbabilityMetadata.getMean())/passProbabilityMetadata.getStand().doubleValue()) * 100).intValue());
            collegeProbability.generateChongShouBao();
            collegeProbability.setBatchSeq(passProbabilityMetadata.getBatchSequence());
            collegeProbability.setBatchId(passProbabilityMetadata.getEnrollBatch().getId());

            if(collegeProbability.getChongShouBao() != null && (chongShouBao == null || chongShouBao.equals(collegeProbability.getChongShouBao())))
                collegeProbabilities.add(collegeProbability);
        }

        if(desc == null || !desc)
            collegeProbabilities.sort((CollegeProbability p1, CollegeProbability p2) ->p1.getProbalility().compareTo(p2.getProbalility()));
        else
            collegeProbabilities.sort((CollegeProbability p1, CollegeProbability p2) ->p2.getProbalility().compareTo(p1.getProbalility()));


        System.out.println(collegeProbabilities.size());

        return collegeProbabilities;
    }

    //private static Map<EnrollStudentProbabilityKey, List<EnrollStudentPlanProbability>> values = new HashMap<>();

    @Override
    public List<EnrollStudentPlanProbability> getEnrollStudentPlanIdsList(Long provienceId, Long sequence, ScienceAndArt scienceAndArt, List<CollegeMajorProbability> collegeMajorProbabilities, List<ChongShouBao> chongShouBaos, Boolean desc) {
        if(collegeMajorProbabilities == null || collegeMajorProbabilities.size() == 0)
            return null;


        List<CollegeProbability> collegeProbabilitiess = getList(provienceId,scienceAndArt,sequence,new ArrayList<>(collegeMajorProbabilities.stream().map(collegeMajorProbability -> collegeMajorProbability.getCollegeId()).collect(Collectors.toSet())),  null, desc);


        Map<Long, CollegeProbability> collegeProbabilityMap = new HashMap<>();
        for(CollegeProbability collegeProbability : collegeProbabilitiess)
            collegeProbabilityMap.put(collegeProbability.getCollegeId(), collegeProbability);

        List<PassProbabilityMajorMetadata> passProbabilityMajorMetadataList = passProbabilityMajorMetadataRepository.findAllByEnrollStudentPlan_IdIn(new ArrayList<>(collegeMajorProbabilities.stream().map(collegeMajorProbability -> collegeMajorProbability.getEnrollStudentPlanId()).collect(Collectors.toSet())));
        Map<Long, PassProbabilityMajorMetadata> passProbabilityMajorMetadataMap = new HashMap<>();
        for(PassProbabilityMajorMetadata passProbabilityMajorMetadata : passProbabilityMajorMetadataList)
            passProbabilityMajorMetadataMap.put(passProbabilityMajorMetadata.getEnrollStudentPlan().getId(), passProbabilityMajorMetadata);

        DistributionUtil distributionUtil = new DistributionUtil();
        List<EnrollStudentPlanProbability> collegeProbabilities =  new ArrayList<>();
        for(CollegeMajorProbability collegeMajorProbability : collegeMajorProbabilities)
        {


            EnrollStudentPlanProbability collegeProbability = new EnrollStudentPlanProbability();


            collegeProbability.setEnrollStudentPlanId(collegeMajorProbability.getEnrollStudentPlanId());
            if(ObjectUtil.isNull(collegeProbabilityMap.get(collegeMajorProbability.getCollegeId()))){
                //查不到大学概率的，直接过滤掉
                continue;
            }
            Integer p = collegeProbabilityMap.get(collegeMajorProbability.getCollegeId()).getProbalility();
            EnrollStudentPlanProbability enrollStudentPlanProbability = new EnrollStudentPlanProbability();
            if(passProbabilityMajorMetadataMap.get(collegeMajorProbability.getEnrollStudentPlanId()) == null)
            {
                enrollStudentPlanProbability.setProbalility(p);
            }
            else
            {
                PassProbabilityMajorMetadata passProbabilityMajorMetadata = passProbabilityMajorMetadataMap.get(collegeMajorProbability.getEnrollStudentPlanId());
                enrollStudentPlanProbability.setProbalility(100-new Double(distributionUtil.NORMSDIST((sequence-passProbabilityMajorMetadata.getMean())/passProbabilityMajorMetadata.getStand().doubleValue()) * 100).intValue());
                if(enrollStudentPlanProbability.getProbalility() > p)
                    enrollStudentPlanProbability.setProbalility(p);
            }

            enrollStudentPlanProbability.setEnrollStudentPlanId(collegeMajorProbability.getEnrollStudentPlanId());
            enrollStudentPlanProbability.setCollegeId(collegeMajorProbability.getCollegeId());
            enrollStudentPlanProbability.generateChongShouBao();
            if(enrollStudentPlanProbability.getChongShouBao() != null && (chongShouBaos == null || chongShouBaos.contains(enrollStudentPlanProbability.getChongShouBao())))
                collegeProbabilities.add(enrollStudentPlanProbability);


        }




        if(desc == null || !desc)
            collegeProbabilities.sort((EnrollStudentPlanProbability p1, EnrollStudentPlanProbability p2) ->p1.getProbalility().compareTo(p2.getProbalility()));
        else
            collegeProbabilities.sort((EnrollStudentPlanProbability p1, EnrollStudentPlanProbability p2) ->p2.getProbalility().compareTo(p1.getProbalility()));



        return collegeProbabilities;
    }

    @Override
    public ProbabilityCountBean getCount(Long provienceId, Long sequence, ScienceAndArt scienceAndArt)
    {

        ProbabilityCountBean probabilityCountBean = new ProbabilityCountBean();

        List<CollegeProbability> collegeProbabilities = this.getAll(provienceId,scienceAndArt,sequence,null,null);
        if (CollectionUtil.isEmpty(collegeProbabilities)) {
            return probabilityCountBean;
        }
        Map<Long, Long> counts = collegeProbabilities.stream().collect(Collectors.groupingBy(CollegeProbability::getBatchSeq, Collectors.counting()));
        Map<Long, Long> batchMap = new HashMap<>();
        collegeProbabilities.stream().forEach(collegeProbability -> batchMap.put(collegeProbability.getBatchSeq(), collegeProbability.getBatchId()));

        Map<Long, Long> sortedMap = new TreeMap<Long, Long>(counts);
        List<ProbabilityCountBean.CollegeBatch> collegeBatches = new ArrayList<>();

        for(Long batch: sortedMap.keySet())
        {
            ProbabilityCountBean.CollegeBatch collegeBatch = probabilityCountBean.new CollegeBatch();
            collegeBatch.batch = enrollBatchRepo.findById(batchMap.get(batch)).get().getName();
            collegeBatch.count = sortedMap.get(batch);
            collegeBatch.batchId = batchMap.get(batch);
            collegeBatches.add(collegeBatch);

        }
        probabilityCountBean.setColleges(collegeBatches);
        List<Long> collegeIds = collegeProbabilities.stream().map(CollegeProbability::getCollegeId).collect(Collectors.toList());

        probabilityCountBean.setMajors(majorFirstAlternativeRepo.queryFirstMajorListCount(scienceAndArt.ordinal(),collegeIds));

//        ProbabilityCountBean probabilityCountBean = new ProbabilityCountBean();
//        List<ProbabilityCountBean.CollegeBatch> collegeBatches = new ArrayList<>();
//        probabilityCountBean.setColleges(collegeBatches);
//        probabilityCountBean.setMajors(5l);
        return probabilityCountBean;
    }



}
