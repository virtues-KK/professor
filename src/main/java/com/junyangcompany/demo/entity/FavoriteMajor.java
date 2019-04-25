package com.junyangcompany.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author zxy
 * @date 2018-09-13 11:44
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class FavoriteMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn
    private Major major;

    @CreatedBy
    private Long userId;

}
