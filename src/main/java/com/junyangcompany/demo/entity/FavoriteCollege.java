package com.junyangcompany.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 关注的大学
 * @author zxy
 * @date 2018-09-13 11:41
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class FavoriteCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn
    private College college;

    @CreatedBy
    private Long userId;

}
