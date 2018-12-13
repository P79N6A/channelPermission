package com.haier.svc.api.controller.util;

/**
 * Created by 李超 on 2018/4/12.
 */
public class ExportPushToSAP {

    public static final String CNSTOCKSYNCSID="订单号";
    public static final String PUSHDATA="推送数据";
    public static final String RETURNDATA="返回数据";
    public static final String STATUS="状态";
    public static final String MESSAGE="信息";
    public static final String ADDTIME="添加时间";
    public static final String PROCESSTIME="处理时间";

    public static final String VBELNDN1="一次DN1";
    public static final String VBELNDN5="二次DN5";
    public static final String VBELNSPARE="预约LBX备用DN";
    public static final String LBX="LBX单号";
    public static final String QTY="订单数量";
    public static final String ACTUALQTY="LBX入库数量";
    public static final String VBELN="DN";

    public static final String MENGE="数量";

    public static final String LGORT="库位";

    public static final String MATNR="物料编码";
    public static final String ZLSIN="LBX";
    public static final String FOREIGNKEY="订单号";
    public static final String REQUESTDATA="请求数据";
    public static final String RESPONSEDATAS="返回数据";
    public static final String REQUESTTIME="推送时间";
    public static final String ZLGORTO="调出库位";
    public static final String ZLGORTI="调入库位";
    //导出表头
    public static String[] pushToSAPListTitle = {CNSTOCKSYNCSID,PUSHDATA,RETURNDATA,STATUS,MESSAGE,PROCESSTIME,LGORT,MENGE,MATNR,VBELN};
    public static String[] transPushToSAPListTitle = {FOREIGNKEY,REQUESTDATA,RESPONSEDATAS,STATUS,REQUESTTIME,ZLSIN,MATNR,MENGE,ZLGORTO,ZLGORTI,};

    public static String[] pushToSAPListTitle2 = {CNSTOCKSYNCSID,VBELNDN1,VBELNDN5,VBELNSPARE,LBX,ACTUALQTY,QTY,PUSHDATA,RETURNDATA,STATUS,MESSAGE,ADDTIME,PROCESSTIME};

}
