package com.haier.invoice.model.eai;

import java.io.Serializable;

public class InvoiceOutType implements Serializable {
    private String msg;

    private String flag;

    private String wdh;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWdh() {
        return wdh;
    }

    public void setWdh(String wdh) {
        this.wdh = wdh;
    }

}
