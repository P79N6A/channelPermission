package com.haier.invoice.util;

public enum OrderSourceName {
	
    /** 海尔新宝旗舰店 */
    ORIGIN_TOPXB("ORIGIN_TOPXB", "TOPXB"),
    
    /** 海尔新宝旗舰店分销平台 */
    ORIGIN_TOPFENXIAOXB("ORIGIN_TOPFENXIAOXB", "TOPFENXIAOXB"),
    
	/** 商城订单 */
	ORIGIN_FRONT("ORIGIN_FRONT", "1"),
	
	/** 商城账户充值订单 */
	ORIGIN_RECHARGE("ORIGIN_RECHARGE", "19"),
	
	/** 淘宝海尔官方旗舰店 */
	ORIGIN_TBSC("ORIGIN_TBSC", "TBSC"),
	
	/** 淘宝海尔生活家电旗舰店 */
    ORIGIN_SHSC("ORIGIN_SHSC", "TOPDHSC"),
	
	/** 淘宝海尔官方旗舰店分销平台 */
	ORIGIN_FENXIAO("ORIGIN_FENXIAO", "TOPFENXIAO"),
	
	/** 淘宝海尔生活家电旗舰店分销平台 */
	ORIGIN_TOPFENXIAODHSC("ORIGIN_TOPFENXIAODHSC","TOPFENXIAODHSC"),

	/** 淘宝海尔模卡官方旗舰店 */
	ORIGIN_MOOKA("ORIGIN_MOOKA", "TMMK"),

	/** 淘宝海尔模卡官方旗舰店分销店铺 */
	ORIGIN_TMMKFX("ORIGIN_TMMKFX", "TMMKFX"),

	/** 淘宝海尔洗衣机旗舰店 */
	ORIGIN_WASHER("ORIGIN_WASHER", "WASHER"),

	/** 淘宝海尔冰冷旗舰店 */
	ORIGIN_FRIDGE("ORIGIN_FRIDGE", "FRIDGE"),

	/** 淘宝海尔村淘店铺 */
	ORIGIN_TBCT("ORIGIN_TBCT", "TBCT"),

	/** 卡萨帝旗舰店 */
	ORIGIN_TMKSD("ORIGIN_TMKSD", "TMKSD"),

	/** 海尔电视旗舰店 */
	ORIGIN_TMTV("ORIGIN_TMTV", "TMTV"),

	/** 海尔厨房大电旗舰店 */
	ORIGIN_TBCFDD("ORIGIN_TBCFDD", "TBCFDD"),
	/** 天猫小超人旗舰店 */
	ORIGIN_TBXCR("ORIGIN_TBXCR", "TBXCR"),

	/** 淘宝海尔官方旗舰店供应商 */
	ORIGIN_GQGYS("ORIGIN_GQGYS", "GQGYS"),

	/** 淘宝海尔空调旗舰店 */
	ORIGIN_AIR("ORIGIN_AIR", "AIR"),
	
	/** 淘宝买帮 */
	ORIGIN_TOPBUYBANG("ORIGIN_TOPBUYBANG", "TOPBUYBANG"),
	
	/** 淘宝海尔热水器专卖店 */
	ORIGIN_TOPBOILER("ORIGIN_TOPBOILER", "TOPBOILER"),
	
	/** 淘宝海尔生活电器专卖店 */
	ORIGIN_TOPSHJD("ORIGIN_TOPSHJD", "TOPSHJD"),
	
	/** 淘宝海尔手机专卖店 */
	ORIGIN_TOPMOBILE("ORIGIN_TOPMOBILE", "TOPMOBILE"),
	
	/** 统帅日日顺乐家专卖店 */
	ORIGIN_TONGSHUAI("ORIGIN_TONGSHUAI", "TONGSHUAI"),
	
	/** 统帅日日顺乐家专卖店-分销 */
	ORIGIN_TONGSHUAIFX("ORIGIN_TONGSHUAIFX", "TONGSHUAIFX"),
	
	/** 1号店订单 */
	ORIGIN_YIHAODIAN("ORIGIN_YIHAODIAN", "YIHAODIAN"),
	
	/** 大楼订单 */
	ORIGIN_DALOU("ORIGIN_DALOU", "DALOU"),
	
	/** CORPORATE */
	ORIGIN_CORPORATE("ORIGIN_CORPORATE", "CORPORATE"),
	
	/** CORPORATE_SJMG */
	ORIGIN_CORPORATE_SJMG("ORIGIN_CORPORATE_SJMG", "CORPORATE_SJMG"),
	
	/** 后台订单 */
	ORIGIN_BACKGROUND("ORIGIN_BACKGROUND", "99"),
	
	/** B2B */
	ORIGIN_B2B("ORIGIN_B2B", "B2B"),
	
	/** CRM */
	ORIGIN_CRM("ORIGIN_CRM", "CRM"),
	
	/** TM大单 */
	ORIGIN_BIGTM("ORIGIN_BIGTM", "11"),
	
	/** TM小单 */
	ORIGIN_SMALLTM("ORIGIN_SMALLTM", "15"),
	
	/** life会员 */
	ORIGIN_LIFEMEMBER("ORIGIN_LIFEMEMBER", "SC20"),
	
	/** 财付通会员 */
	ORIGIN_TENPAYMEMBER("ORIGIN_TENPAYMEMBER", "SC11"),
	
	/** 彩电会员 */
	ORIGIN_TVMEMBER("ORIGIN_TVMEMBER", "SC23"),
	
	/** 村单联络人 */
	ORIGIN_VILLAGECONTACT("ORIGIN_VILLAGECONTACT", "SC13"),
	
	/** 大楼展销 */
	ORIGIN_BUILDINGEXHIBITION("ORIGIN_BUILDINGEXHIBITION", "DL1"),
	
	/** 大楼转单 */
	ORIGIN_BUILDINGTURN("ORIGIN_BUILDINGTURN", "201106080001"),
	
	/** 大学生创新应用大赛会员 */
	ORIGIN_UNIVERSITY_INNOVATION("ORIGIN_UNIVERSITY_INNOVATION", "SC16"),
	
	/** 大学生创业会员 */
	ORIGIN_UNIVERSITY_BUSINESS("ORIGIN_UNIVERSITY_BUSINESS", "6"),
	
	/** 导购大单 */
	ORIGIN_BIG_SHOPPING_GUIDE("ORIGIN_BIG_SHOPPING_GUIDE", "12"),
	
	/** 导购小单 */
	ORIGIN_SMALL_SHOPPING_GUIDE("ORIGIN_SMALL_SHOPPING_GUIDE", "13"),
	
	/** 电脑vip */
	ORIGIN_COMPUTER_VIP("ORIGIN_COMPUTER_VIP", "8"),
	
	/** 电视购物 */
	ORIGIN_TV_SHOPPING("ORIGIN_TV_SHOPPING", "30"),
	
	/** 电视专卖店 */
	ORIGIN_TV_STORE("ORIGIN_TV_STORE", "4"),
	
	/** 工贸大单 */
	ORIGIN_BIG_TRADE("ORIGIN_BIG_TRADE", "10"),
	
	/** 顾服订单 */
	ORIGIN_BIG_CARE_SERVICE_ORDERS("ORIGIN_BIG_CARE_SERVICE_ORDERS", "201106230021"),
	
	/** 国网会员 */
	ORIGIN_STATEGRIDMEMBER("ORIGIN_STATEGRIDMEMBER", "SC15"),
	
	/** 海尔内部员工 */
	ORIGIN_HAIER_INTERNALSTAFF("ORIGIN_HAIER_INTERNALSTAFF", "SC12"),
	
	/** 俱乐部会员 */
	ORIGIN_CLUBMEMBER("ORIGIN_CLUBMEMBER", "5"),
	
	/** 快钱会员 */
	ORIGIN_QUANQIANMEMBER("ORIGIN_QUANQIANMEMBER", "7"),
	
	/** 狼狼爱购 */
	ORIGIN_LLIG("ORIGIN_LLIG", "LLIG"),
	
	/** 内部客户 */
	ORIGIN_INTERNAL_CUSTOMERS("ORIGIN_INTERNAL_CUSTOMERS", "1000"),
	
	/** 农行会员 */
	ORIGIN_SC14("ORIGIN_SC14", "SC14"),
	
	/** 品牌家电网 */
	ORIGIN_BRANDHOME("ORIGIN_BRANDHOME", "17"),
	
	/** 奇瑞会员 */
	ORIGIN_QIRUI("ORIGIN_QIRUI", "SC22"),
	
	/** 企业会员 */
	ORIGIN_CORPORATEMEMBER("ORIGIN_CORPORATEMEMBER", "3"),
	
	/** 售后技服 */
	ORIGIN_AFTERSALE_TS("ORIGIN_AFTERSALE_TS", "1001"),
	
	/** 淘宝分销 */
	ORIGIN_TBFX("ORIGIN_TBFX", "TBFX"),
	
	/** 淘宝商城 */
	ORIGIN_TAOBAO("ORIGIN_TAOBAO", "16"),
	
	/** 淘宝商城转单 */
	ORIGIN_TBSC2("ORIGIN_TBSC2", "TBSC2"),
	
	/** 特许经营店 */
	ORIGIN_FRANCHISE_STORES("ORIGIN_FRANCHISE_STORES", "2"),
	
	/** 网站联盟 */
	ORIGIN_AFFILIATE("ORIGIN_AFFILIATE", "9"),
	
	/** 微博用户 */
	ORIGIN_WEIBO("ORIGIN_WEIBO", "SC18"),
	
	/** 银行分期 */
	ORIGIN_BANKSTAGE("ORIGIN_BANKSTAGE", "18"),
	
	/** 预付款客户 */
	ORIGIN_CUSTOMER_ADVANCES("ORIGIN_CUSTOMER_ADVANCES", "1002"),
	
	/** 支付宝会员 */
	ORIGIN_ALIPAYMEMBER("ORIGIN_ALIPAYMEMBER", "SC9"),
	
	/** 企业内购-建行 */
	ORIGIN_CCB("ORIGIN_CCB", "95533"),
	
	/** 建行商城接入订单 */
	ORIGIN_CCBSC("ORIGIN_CCBSC", "CCBSC"),
	
	/** 建行善融商城订单 */
	ORIGIN_CCBSR("ORIGIN_CCBSR", "CCBSR"),
	
	/** 本站订单 */
	ORIGIN_COMBINE_SELF("ORIGIN_COMBINE_SELF", "10"),
	
	/** 淘宝订单 */
	ORIGIN_COMBINE_TAOBAO("ORIGIN_COMBINE_TAOBAO", "12"),
	
	/** 1号店订单 */
	ORIGIN_BOMBINE_YIHAODIAN("ORIGIN_BOMBINE_YIHAODIAN", "111"),
	
	/** 商城订单_海尔地产 */
	ORIGIN_CORPORATE_DC("ORIGIN_CORPORATE_DC", "112"),
	
	/** 企业订单_内购 */
	ORIGIN_CORPORATE_NG("ORIGIN_CORPORATE_NG", "113"),
	
	/** 企业订单_集采 */
	ORIGIN_CORPORATE_JC("ORIGIN_CORPORATE_JC", "114"),
	
	/** 企业订单_B2B */
	ORIGIN_CORPORATE_B2B("ORIGIN_CORPORATE_B2B", "115"),
	
	/** 其它来源 */
	ORIGIN_OTHER("ORIGIN_OTHER", "OTHER"),

	/** 京东海尔官旗 */
	ORIGIN_JDHEGQ("ORIGIN_JDHEGQ", "JDHEGQ"),


	/** 其它来源 */
	XSST("XSST", "XSST");


	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String value;
	
	/**
	 * 
	 * 构建一个<code>OrderSource.java</code>
	 * @param code
	 * @param value
	 */
	private OrderSourceName(String code, String value) {
		this.code = code;
		this.value = value;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the value.
	 */
	public String value() {
		return value;
	}
	
	public static OrderSourceName getByValue(String value) {
		OrderSourceName[] values = OrderSourceName.values();
		for (OrderSourceName os : values) {
			if (os.getValue().equals(value)) {
				return os;
			}
		}
		return null;
		
	}
}
