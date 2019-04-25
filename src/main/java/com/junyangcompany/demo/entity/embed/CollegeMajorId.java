package com.junyangcompany.demo.entity.embed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class CollegeMajorId implements Serializable {

    private Long collegeId;

    private Long majorId;

}
