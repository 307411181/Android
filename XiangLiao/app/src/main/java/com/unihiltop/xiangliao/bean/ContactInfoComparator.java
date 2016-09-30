package com.unihiltop.xiangliao.bean;

import java.util.Comparator;

public class ContactInfoComparator implements Comparator<ContactInfo> {

    @Override
    public int compare(ContactInfo lhs, ContactInfo rhs) {
        if (lhs == null || rhs == null){
            return 0;
        }
        if ("#".equals(lhs.sortKey) && !"#".equals(rhs.sortKey)) {
            return -1;
        }
        if ("#".equals(rhs.sortKey) && !"#".equals(lhs.sortKey)) {
            return 1;
        }
        return lhs.sortKey.compareTo(rhs.sortKey);
    }

}
