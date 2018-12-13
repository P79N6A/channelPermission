package com.haier.afterSale.service;

public interface ReportType {
    /**妥投率*/
    public static final String OWF                = "owf";
    /**O2O妥投率*/
    public static final String O2OOWF             = "o2oOwf";
    /**确认*/
    public static final String CONFIRM            = "confirm";
    /**派工*/
    public static final String HP                 = "hp";
    /**开单*/
    public static final String LES                = "les";
    /**多层级转运*/
    public static final String TRANSPORT          = "transport";
    /**送达网点*/
    public static final String NETPOINT           = "netpoint";
    /**送达用户*/
    public static final String USER               = "user";
    /**逆向闭环*/
    public static final String ORDERCLOSE         = "orderclose";
    /**审核*/
    public static final String AUDIT              = "audit";
    /**O2O审核*/
    public static final String O2OAUDIT           = "o2oAudit";
    /**一次质检*/
    public static final String CHECKQUALITY       = "checkquality";
    /**入库*/
    public static final String INSTOCK            = "instock";
    /**冲票*/
    public static final String INVOICE            = "invoice";
    /**二次质检*/
    public static final String RECHECKQUALITY     = "recheckquality";
    /**转库*/
    public static final String TRANSMITSTOCK      = "transmitstock";
    /**退款*/
    public static final String REFUND             = "refund";
    /**COD回款*/
    public static final String COD                = "cod";
    /**O2O COD回款*/
    public static final String O2OCOD             = "o2oCod";
    /**转箱*/
    public static final String TRANSMITBOX        = "transmitbox";
    /**二次鉴定换箱*/
    public static final String RECHECKTRANSMITBOX = "rechecktransmitbox";
    /**22库转10或41库*/
    public static final String STORE22            = "store22";
}
