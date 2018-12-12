package com.haier.shop.model;

import java.io.Serializable;

public class O2oOrderCloseQueuesExt extends O2oOrderCloseQueues implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1569503217487281338L;

    private Integer           orderRepairId;

    public Integer getOrderRepairId() {
        return orderRepairId;
    }

    public void setOrderRepairId(Integer orderRepairId) {
        this.orderRepairId = orderRepairId;
    }

}
