package com.junyangcompany.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * author:pan le
 * Date:2019/5/5
 * Time:14:28
 *  简单分页工具类
 */

public class pageUtils {
    public static <T> List<T> getPageSizeDataForRelations(List<T> datas, int pageSize, int pageNo){
        int startNum = (pageNo)* pageSize+1 ;                     //起始截取数据位置
        if(startNum > datas.size()){
            return null;
        }
        List<T> res = new ArrayList<>();
        int rum = datas.size() - startNum;
        if(rum < 0){
            for (int i = 0 ; i < datas.size() ; i++){
                res.add(datas.get(i));
            }
            return res;
        }
        if(rum == 0){                                               //说明正好是最后一个了
            int index = datas.size() -1;
            res.add(datas.get(index));
            return res;
        }
        if(rum / pageSize >= 1){                                    //剩下的数据还够1页，返回整页的数据
            for(int i=startNum;i<startNum + pageSize;i++){          //截取从startNum开始的数据
                res.add(datas.get(i-1));
            }
            return res;
        }else if(rum / pageSize == 0){                 //不够一页，直接返回剩下数据
            for(int j = startNum ;j<=datas.size();j++){
                res.add(datas.get(j-1));
            }
            return res;
        }else{
            return null;
        }
    }
}
