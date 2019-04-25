package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 关注的职业
 * @author zxy
 * @date 2018-09-13 11:46
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn
    private Career career;

    @CreatedBy
    private Long userId;

    public static FavoriteCareer ofCareerId(long careerId) {
        return FavoriteCareer.builder().career(Career.builder().id(careerId).build()).build();
    }
}
