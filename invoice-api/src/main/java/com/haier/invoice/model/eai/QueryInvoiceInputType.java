package com.haier.invoice.model.eai;

import java.io.Serializable;

public class QueryInvoiceInputType implements Serializable {
    private String wdh;

    public String getWdh() {
        return wdh;
    }

    public void setWdh(String wdh) {
        this.wdh = wdh;
    }

}
