package com.junyangcompany.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 霍兰德评测结果
 *
 * @author zxy
 * @date 2018-08-02 02:51
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Cacheable
public class HollandResult {

    @Data
    @Embeddable
    public static class Scores {
        @JsonProperty(value = "S")
        @Column(precision = 5, scale = 2)
        private Double s;
        @JsonProperty(value = "E")
        @Column(precision = 5, scale = 2)
        private Double e;
        @JsonProperty(value = "C")
        @Column(precision = 5, scale = 2)
        private Double c;
        @JsonProperty(value = "I")
        @Column(precision = 5, scale = 2)
        private Double i;
        @JsonProperty(value = "A")
        @Column(precision = 5, scale = 2)
        private Double a;
        @JsonProperty(value = "R")
        @Column(precision = 5, scale = 2)
        private Double r;

        public static Scores fromScoresMap(Map<String, Double> source) {
            Scores target = new Scores();
            target.setS(source.get("S"));
            target.setE(source.get("E"));
            target.setC(source.get("C"));
            target.setI(source.get("I"));
            target.setA(source.get("A"));
            target.setR(source.get("R"));
            return target;
        }

        public Map<String, Double> toScoresMap() {
            Map<String, Double> target = new HashMap<>();
            target.put("S", this.s);
            target.put("E", this.e);
            target.put("C", this.c);
            target.put("I", this.i);
            target.put("A", this.a);
            target.put("R", this.r);
            return target;
        }

        public Double get(String type){
            switch (type){
                case "A":
                    return a;
                case "S":
                    return s;
                case "E":
                    return e;
                case "I":
                    return i;
                case "C":
                    return c;
                case "R":
                    return r;
            }
            return null;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 标准分
     */
    @Embedded
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Scores scores;

    /**
     * 说明文字
     */
    private String detail;

    /**
     * 所有类型
     */
    @Column(length = 10)
    private String types;

    @Column(nullable = false, unique = true,length = 200)
    private Long userId;

    //特殊说明
    private String specialExplain;

    /**
     * 类型组合
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Column(name = "type_groups",length = 200)
    private List<String> typeGroups = new ArrayList<>();

}