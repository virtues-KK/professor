package com.junyangcompany.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户的全文检索记录
 * @author zxy
 * @date 2018-08-06 15:02
 */
@Entity
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class FullTextSearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 检索关键词
     */
    @Column(length = 36)
    private String keyword;

    @CreatedDate
    @Column(nullable = false,columnDefinition = "datetime(3)")
    private LocalDateTime createdDate;

    /**
     * 关联到总用户表的id
     */
    @CreatedBy
    @Column(nullable = false)
    private Long userId;

    public FullTextSearchHistory(String keyword) {
        this.keyword = keyword;
    }

    public FullTextSearchHistory() {
    }
}
