package com.junyangcompany.demo.bean;


import com.junyangcompany.demo.entity.Career;

/**
 * @author zxy
 * @date 2018-09-19 11:11
 */
public class CareerDetailResponse extends Career {

    private boolean isPraise;

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }
}
