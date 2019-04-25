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
public class CollegeProbability {

    Long collegeId;

    Integer probalility;

    ChongShouBao chongShouBao;

    Long batchId;

    Long batchSeq;

    public void generateChongShouBao()
    {
        if(probalility != null)
        {
            if(probalility >= 90)
                chongShouBao = ChongShouBao.BAO;
            if(probalility < 90 && probalility >= 70)
                chongShouBao = ChongShouBao.SHOU;
            if(probalility >= 50 && probalility < 70)
                chongShouBao = ChongShouBao.CHONG;
            if(probalility<50)
                chongShouBao = ChongShouBao.NAN;
        }

        if(probalility >= 100) probalility = 99;
        if(probalility <=0) {
            probalility = 1;
            chongShouBao = ChongShouBao.NAN;
        }
    }
}
