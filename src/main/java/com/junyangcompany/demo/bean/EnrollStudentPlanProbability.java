package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.enumeration.ChongShouBao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollStudentPlanProbability {

    Long enrollStudentPlanId;

    Long collegeId;

    Integer probalility;

    ChongShouBao chongShouBao;

    public void generateChongShouBao()
    {
        if(probalility != null)
        {
            if(probalility >= 90)
                chongShouBao = ChongShouBao.BAO;
            if(probalility < 90 && probalility >= 70)
                chongShouBao = ChongShouBao.SHOU;
            if(probalility >= 0 && probalility < 70)
                chongShouBao = ChongShouBao.CHONG;
            if(probalility<50)
                chongShouBao = ChongShouBao.NAN;
        }
        if(probalility > 99)
            probalility = 99;
        if(probalility < 1)
            probalility = 1;
    }
}
