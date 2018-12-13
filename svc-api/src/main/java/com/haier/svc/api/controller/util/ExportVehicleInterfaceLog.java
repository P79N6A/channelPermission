package com.haier.svc.api.controller.util;

/**
 * Created by 李超 on 2018/4/13.
 */
public class ExportVehicleInterfaceLog {

    public static final String ROWID="日志ID";
    public static final String INTERFACEDATE="日志时间";
    public static final String INTERFACENAME="日志名称";
    public static final String INTERFACEMESSAGE="备注";

    //导出表头
    public static String[] vehicleInterfaceLogListTitle = {ROWID,INTERFACEDATE,INTERFACENAME,INTERFACEMESSAGE};

}
