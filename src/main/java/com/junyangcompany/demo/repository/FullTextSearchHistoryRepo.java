package com.junyangcompany.demo.repository;

import com.junyangcompany.demo.entity.FullTextSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FullTextSearchHistoryRepo extends JpaRepository<FullTextSearchHistory, Long> {

    /**
     * 查询前五条某用户的搜索记录
     * 会对关键词去重
     * @param userId 用户id
     * @return 关键词数组
     */
    @Query(value = "select keyword from full_text_search_history o " +
            "where o.created_date = (select max(i.created_date) from full_text_search_history i where o.keyword = i.keyword and user_id = ?1) " +
            "and user_id = ?1 order by o.created_date desc limit 5",nativeQuery = true)
    String[] top5KeywordByUser(long userId);

}