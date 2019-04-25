package com.junyangcompany.demo.repository;


import com.junyangcompany.demo.entity.HollandQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface HollandQuestionRepo extends JpaRepository<HollandQuestion,Long> {

    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value ="true") })
    @Query("from HollandQuestion ")
    List<HollandQuestion> all();

}
