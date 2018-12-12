/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

public class OMST2OrderCancelRequire implements Serializable {

    private String oms_id; //OMS单号

    public String getOms_id() {
        return oms_id;
    }

    public void setOms_id(String oms_id) {
        this.oms_id = oms_id;
    }

    public String toXMLMessage() {
        return "";
    }
}
