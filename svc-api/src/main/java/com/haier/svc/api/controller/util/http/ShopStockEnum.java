/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.api.controller.util.http;

/**
 * 库存同步渠道枚举                      
 * @Filename: ShopStockEnum.java
 * @Version: 1.0
 * @Author: 丛峰
 * @Email: 3024992@qq.com
 * 
 * 库存同步分类：（按库存同步程序代码区分）
 * 
 */
public enum ShopStockEnum {
	
	TBSC("海尔官方淘宝旗舰店",1,"taobao"),
    TOPBOILER("海尔热水器专卖店",4,"taobaorsq"),
    TONGSHUAI("淘宝网统帅日日顺乐家专卖店",7,"toptongshuai"),
    TONGSHUAIFX("统帅品牌商",15,"tongshuaifx"),
    TOPXB("海尔新宝旗舰店",12,"taobaoxb"),
    TOPSHJD("海尔生活电器专卖店",3,"taobaoshjd"), 
    TOPDHSC("海尔生活家电旗舰店",10,"taobaoshsc"),
    GMZX("统帅国美旗舰店",18,"GMZX"),
    GMZXTS("统帅国美官方旗舰店",98,"GMZXTS"),
    TBSCFX("海尔官方淘宝旗舰店",8,"taobaofx"),
    MOOKA("模卡淘宝旗舰店",16,"TMMK"),
    TMMK("模卡淘宝旗舰店",16,"TMMK"),
    TMMKFX("模卡淘宝旗舰店分销店铺",26,"TMMKFX"),
    XSST("淘宝WA线上生态授权店",19,"XSST"),
    WASHER("海尔洗衣机旗舰店",17,"WASHER"),
    FRIDGE("海尔冰冷旗舰店",18,"FRIDGE"),
    AIR("海尔空调旗舰店",21,"AIR"),
    TBCT("村淘海尔商家",22,"TBCT"),
    TMTV("海尔电视旗舰店",28,"TMTV"),
    TBCFDD("海尔厨房大电旗舰店",29,"TBCFDD"),
    TBZYKT("淘宝中央空调",32,"TBZYKT"),
    TBXCR("天猫小超人旗舰店",31,"TBXCR"),
    TMKSD("天猫卡萨帝旗舰店",27,"TMKSD"),
    GQGYS("海尔官方旗舰店供应商",23,"GQGYS"),
    TBQYG("天猫企业购直营",24,"TBQYG"),
    YMX("海尔官方亚马逊旗舰店",20,"YMX"),
    SNYG("海尔统帅苏宁旗舰店",30,"SNYG"),
    SNHEGQ("苏宁海尔集团官方旗舰店",94,"SNHEGQ"),
    SNQDZX("苏宁渠道中心",96,"SNQDZX"),
    DDW("海尔当当旗舰店",35,"DDW"),
    GMTSZYCW("国美自营店(厨卫)",90,"GMTSZYCW"),
    GMTSZYKT("国美自营店(空调)",91,"GMTSZYKT"),
    GMTSZYBX("国美自营店(冰箱)",92,"GMTSZYBX"),
    GMTSZYXYJ("国美自营店(洗衣机)",93,"GMTSZYXYJ"),
    GMTSZYYY("国美自营店(电视家庭影音)",93,"GMTSZYYY"),
    JDHEGQ("京东海尔集团官方旗舰店",97,"JDHEGQ"),
    JDHEBXGQ("京东海尔集团冰箱官方旗舰店",98,"JDHEBXGQ"),
    ;
	
	/**
	 * 代表库存同步的类型，ShopStockEnum+Type可确保唯一性即可
	 * 第一类：淘宝基础库存（小家电），包含店铺：TOPSHJD、TOPDHSC
	 * 第二类：库存中心库存（小家电），包含店铺：TBSC、TOPBOILER
	 * 第三类：物流宝库存（大家电/套装），包含店铺：TBSC、TOPBOILER、TONGSHUAI、TOPXB、TONGSHUAIFX
	 * 第四类：其他，包含：一号店yihaodian、建行jianhang
	 */
	public static enum Type{
//		对应第一类
		XJD_BASE,
//		对应第二类
		XJD_STOCKCENTER,
//		对应第三类
		DJDTZ_WLB,
//		对应第四类
		OHTER,
//对应付款后退货
		CLOSE_ORDER,
		//对应EC类
		DJD_WLB_EC
	};
    
    private ShopStockEnum(String stockName, Integer configId, String source){
        this.stockName = stockName;
        this.configId = configId;
        this.source = source;
    };
    
    private String stockName;
    private Integer configId;
    private String source;
    
	public Integer getConfigId() {
		return configId;
	}

	public String getSource() {
		return source;
	}

	public String getStockName() {
		return stockName;
	}
	
	
    
}
