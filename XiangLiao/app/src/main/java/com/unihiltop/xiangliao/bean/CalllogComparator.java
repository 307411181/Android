package com.unihiltop.xiangliao.bean;

import java.util.Comparator;

/**
 * Created by yangyang on 2015/11/19.
 */
public class CalllogComparator  implements Comparator<Calllog> {
    @Override
    public int compare(Calllog lhs, Calllog rhs) {
        long date= lhs.getDate() - rhs.getDate();
        if (date>0){
            return -1;
        }else if(date<0){
            return 1;
        }else if (date == 0){
            return 0;
        }
        return 0;
    }
}
