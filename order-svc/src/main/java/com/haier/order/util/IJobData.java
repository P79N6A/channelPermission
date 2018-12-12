package com.haier.order.util;

import java.util.List;

public interface IJobData extends Runnable {
    public List popData();

    public void setIndex(int index);
}
