package com.haier.order.util;

import com.haier.purchase.data.model.T2OrderItem;

import java.util.ArrayList;
import java.util.List;

public class T2JobData implements IJobData {
    private static final org.apache.log4j.Logger log   = org.apache.log4j.LogManager
                                                           .getLogger(T2JobData.class);
    private List<T2OrderItem>                    l     = new ArrayList<T2OrderItem>();
    private int                                  index = -1;

    @Override
    public void run() {

    }

    @Override
    public List popData() {
        return l;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

}
