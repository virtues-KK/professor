package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.entity.CollegeLevel;
import com.junyangcompany.demo.entity.EnrollBatch;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 生涯测评师初选结果
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProfessionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long enrollCollegeEnrollBatch;

    @NonNull
    private Long seq; // 位次

    @NonNull
    private ScienceAndArt scienceAndArt; // 文理科

    @NonNull
    private Long provinceId;

    private String collegeName;

//    private String batchName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private List<EnrollBatch> batchNames = new ArrayList<>();

    private String collegeCode;

    @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    private List<CollegeLine> collegeLines;

    private Integer probability;

    private Integer maxProbability;

    private Integer minProbability;

    private String type;

    @Column(name = "ranker")
    private Integer rank;

    private Integer minRank;

    private Integer maxRank;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private List<CollegeLevel> levels;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "planLine_id")
    private List<PlanLine> planLInes;

//    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
//    @JoinColumn(name = "secondChoiceId")
//    private List<QueryEnrollCollegeMajorBean_demo> secondChoices = new ArrayList<>();

    /**
     * 考生信息
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Examinee examinee;

    @Override
    public String toString() {
        return "ProfessionalEntity{" +
                "id=" + id +
                ", enrollCollegeEnrollBatch=" + enrollCollegeEnrollBatch +
                ", seq=" + seq +
                ", scienceAndArt=" + scienceAndArt +
                ", provinceId=" + provinceId +
                ", collegeName='" + collegeName + '\'' +
                ", batchNames=" + batchNames +
                ", collegeCode='" + collegeCode + '\'' +
                ", collegeLines=" + collegeLines +
                ", probability=" + probability +
                ", maxProbability=" + maxProbability +
                ", minProbability=" + minProbability +
                ", type='" + type + '\'' +
                ", rank=" + rank +
                ", minRank=" + minRank +
                ", maxRank=" + maxRank +
                ", levels=" + levels +
                ", planLInes=" + planLInes +
                ", examinee=" + examinee +
                '}';
    }
}


