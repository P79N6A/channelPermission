package com.haier.afterSale.services;

import com.haier.afterSale.service.ReportType;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/8/30.
 */
@Service
public  class  Kpilists {
    /**正向kpi报表类型列表*/
    public static Set<String> kpiTypeList  = new HashSet<String>();
    /**逆向kpi报表类型列表*/
    public static Set<String>   kpiReverseTypeList = new HashSet<String>();

    static {
        kpiTypeList.add(ReportType.OWF);
        kpiTypeList.add(ReportType.O2OOWF);
        kpiTypeList.add(ReportType.CONFIRM);
        kpiTypeList.add(ReportType.HP);
        kpiTypeList.add(ReportType.LES);
        kpiTypeList.add(ReportType.TRANSPORT);
        kpiTypeList.add(ReportType.NETPOINT);
        kpiTypeList.add(ReportType.USER);
        kpiTypeList.add(ReportType.COD);
        kpiTypeList.add(ReportType.O2OCOD);
        kpiReverseTypeList.add(ReportType.ORDERCLOSE);
        kpiReverseTypeList.add(ReportType.AUDIT);
        kpiReverseTypeList.add(ReportType.O2OAUDIT);
        kpiReverseTypeList.add(ReportType.CHECKQUALITY);
        kpiReverseTypeList.add(ReportType.INSTOCK);
        kpiReverseTypeList.add(ReportType.INVOICE);
        kpiReverseTypeList.add(ReportType.RECHECKQUALITY);
        kpiReverseTypeList.add(ReportType.TRANSMITSTOCK);
        kpiReverseTypeList.add(ReportType.REFUND);
        kpiReverseTypeList.add(ReportType.TRANSMITBOX);
        kpiReverseTypeList.add(ReportType.RECHECKTRANSMITBOX);
        kpiReverseTypeList.add(ReportType.STORE22);
    }

}
