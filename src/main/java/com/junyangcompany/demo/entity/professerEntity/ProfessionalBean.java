package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.bean.UserInfo;
import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 生涯测评师初选结果
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProfessionalBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long enrollCollegeEnrollBatch;

    private Long seq; // 位次

    private Boolean scienceAndArt; // 文理科

    private Long provinceId;

    private String collegeName;

//    private String batchName;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<EnrollBatch> batchNames = new ArrayList<>();

    private String collegeCode;

    @OneToMany
    private  List<CollegeLine> collegeLines;

    private Integer probability;

    private Integer maxProbability;

    private Integer minProbability;

    private String type;

    @Column(name = "ranker")
    private Integer rank;

    private Integer minRank;

    private Integer maxRank;

    @OneToMany
    private List<CollegeLevel> levels;

    @OneToMany
   private List<PlanLine> planLInes;

    /**
     * 考生信息
     */
//   @ManyToOne(fetch = FetchType.LAZY)
//   private Examinee examinee;

}


