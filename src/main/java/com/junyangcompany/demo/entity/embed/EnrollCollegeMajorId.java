package com.junyangcompany.demo.entity.embed;

import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class EnrollCollegeMajorId implements Serializable {

    private Long enrollCollegeEnrollBatchId;

    private Long majorId;
    @Enumerated
    private ScienceAndArt scienceArt;
}
