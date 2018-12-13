package com.haier.eop.data.model;

/**
 * 调拨常量类
 */
public abstract class TransferConstants {

	public static final String customerId = "2998123754";
	public static final String ownerCode = "2998123754";

	//Link对接生产使用
	public static final String LINK_URL = "http://link.cainiao.com/gateway/link.do";
	public static final String LINK_APPKEY = "009658";
	public static final String LINK_SECRECTKEY = "Vi3z88ZEy5j9W28s4JA0O3X29r159S06";
	public static final String LINK_CPCODE = "2998123754";

	//允许推送SAP的调拨单状态
	public static final int[] ALLOW_SAP_ORDERSTATUS = {140,150};
	//向SAP推送调拨信息的wsdl路径
	public static final String TRANSFEROUT_WSDLLOCATION = "/wsdl";
	//向SAP推送调拨信息的wsdl文件名
	public static final String TRANSFERORDER_CREATER_HAIER = "Haier";
	public static final String TRANSFERORDER_CREATER_CAINIAO = "Cainiao";

	//推送SAP成功返回值
	public static final String SUCCESS_SAP = "S";
	//推送SAP失败返回值
	public static final String FAILUTE_SAP = "E";
	//推送SAP自定义类型，同仓位调拨使用
	public static final String NONEED_SAP = "NONEED";
}
