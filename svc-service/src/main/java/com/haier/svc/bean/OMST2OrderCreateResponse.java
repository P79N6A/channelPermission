/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

public class OMST2OrderCreateResponse implements Serializable {

    private String Flag;
    private String BillCode;
    private String Vbeln;
    private String ReturnMsg;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getBillCode() {
        return BillCode;
    }

    public void setBillCode(String billCode) {
        BillCode = billCode;
    }

    public String getVbeln() {
        return Vbeln;
    }

    public void setVbeln(String vbeln) {
        Vbeln = vbeln;
    }

    public String getReturnMsg() {
        return ReturnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        ReturnMsg = returnMsg;
    }

}
