package com.junyangcompany.demo.entity.professerEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProfessionalBean {

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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<EnrollBatch> batchNames = new ArrayList<>();

    private String collegeCode;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "collegeLine_id")
    private List<CollegeLine> collegeLines;

    private Integer probability;

    private Integer maxProbability;

    private Integer minProbability;

    private String type;

    @Column(name = "ranker")
    private Integer rank;

    private Integer minRank;

    private Integer maxRank;

    @OneToMany
    @JoinColumn(name = "collegeLevel_id")
    private List<CollegeLevel> levels;

    @OneToMany
    @JoinColumn(name = "planLine_id")
    private List<PlanLine> planLInes;

    /**
     * 考生信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Examinee examinee;

    @Override
    public String toString() {
        return "ProfessionalBean{" +
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


