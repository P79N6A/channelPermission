package com.haier.afterSale.util;

import com.haier.common.util.StringUtil;

import java.util.*;

public class OrderSnUtil {
    /**
     * 获取网单编号
     * @param opId
     * @return
     */
    public static String getCOrderSn(String opId, Date date) {
        if (null == date) {
            date = new Date();
        }
        //序列号,不足6位补0,超过6位取末尾6位
        int len = opId.length();
        String seq = null;
        if (len >= 6) {
            seq = opId.substring(len - 6);
        } else {
            seq = String.format("%06d", opId);
        }
        //日期
        String sDate = DateFormatUtil.formatByType("yyMMdd", date);
        return "WD" + sDate + seq;
    }

    /**
     * 根据订单来源编号，获取来源名称
     * 
     * 注意：这里只包括常用订单来源，调用该方法后还需要调用查询订单来源接口获取最新添加的订单来源
     * 
     * @param source
     * @return
     */
    public static String getSourceName(String source) {
        if (source.equals("1")) {
            return "商城订单";
        } else if (source.equals("TBSC")) {
            return "淘宝海尔官方旗舰店";
        } else if (source.equals("TOPDHSC")) {
            return "淘宝海尔生活家电旗舰店";
        } else if (source.equals("TOPBUYBANG")) {
            return "淘宝海尔买帮专卖店";
        } else if (source.equals("TOPBOILER")) {
            return "淘宝海尔热水器专卖店";
        } else if (source.equals("TOPSHJD")) {
            return "淘宝海尔生活电器专卖店";
        } else if (source.equals("TOPMOBILE")) {
            return "淘宝海尔手机专卖店";
        } else if (source.equals("TONGSHUAI")) {
            return "统帅日日顺乐家专卖店";
        } else if (source.equals("CCBSC")) {
            return "企业订单_建行龙卡商城订单";
        } else if (source.equals("CCBSR")) {
            return "企业订单_建行善融商城订单";
        } else if (source.equals("95533")) {
            return "企业内购-建行";
        } else if (source.equals("CORPORATE")) {
            return "企业订单";
        } else if (source.equals("CORPORATE_SJMG")) {
            return "企业订单_四季沐歌";
        } else if (source.equals("112")) {
            return "商城订单_海尔地产";
        } else if (source.equals("YIHAODIAN")) {
            return "企业订单_1号店订单";
        } else if (source.equals("DALOU")) {
            return "企业订单_大楼订单";
        } else if (source.equals("113")) {
            return "企业订单_内购";
        } else if (source.equals("114")) {
            return "企业订单_集采";
        } else if (source.equals("115")) {
            return "企业订单_B2B";
        } else if (source.equals("19")) {
            return "充值订单";
        } else if (source.equals("TOPFENXIAO")) {
            return "淘宝海尔官方旗舰店分销平台";
        } else if ("HPB2B".equalsIgnoreCase(source)) {
            return "海朋转TC订单";
        } else if ("TOPXB".equalsIgnoreCase(source)) {
            return "海尔新宝旗舰店";
        } else if ("TOPFENXIAOXB".equalsIgnoreCase(source)) {
            return "淘宝海尔新宝旗舰店分销平台";
        } else if ("C2BWASHER".equalsIgnoreCase(source)) {
            return "C2B滚筒洗衣机订单";
        } else if ("MOBILE".equalsIgnoreCase(source)) {
            return "移动商城";
        } else if ("COS".equalsIgnoreCase(source)) {
            return "样品机";
        } else if ("RRS".equalsIgnoreCase(source)) {
            return "日日顺";
        } else if ("SCFX".equalsIgnoreCase(source)) {
            return "商城订单-分销渠道";
        } else if ("CK".equalsIgnoreCase(source)) {
            return "海尔创客";
        } else if ("CK_MOBILE".equalsIgnoreCase(source)) {
            return "海尔创客-移动端";
        } else if ("MSTORE".equalsIgnoreCase(source)) {
            return "微店";
        } else if ("690CK".equalsIgnoreCase(source)) {
            return "690创客";
        } else if ("HBSC".equalsIgnoreCase(source)) {
            return "海贝商城";
        } else if ("KDGW".equalsIgnoreCase(source)) {
            return "移动商城_口袋购物";
        } else if ("FRIDGE".equalsIgnoreCase(source)) {
            return "海尔冰冷官方旗舰店";
        } else if ("WASHER".equalsIgnoreCase(source)) {
            return "海尔洗衣机官方旗舰店";
        } else if ("YDYZ".equalsIgnoreCase(source)) {
            return "移动商城_有赞微商城";
        } else if ("PCNEW".equalsIgnoreCase(source)) {
            return "新品平台-海尔奇迹订单";
        } else if ("DCYH".equalsIgnoreCase(source)) {
            return "电商平台-地产用户";
        } else if ("YLW".equalsIgnoreCase(source)) {
            return "电商平台-邮乐网";
        } else if ("XPZC".equalsIgnoreCase(source)) {
            return "新品平台众筹订单";
        } else if ("BLPHH".equalsIgnoreCase(source)) {
            return "不良品换货";
        } else if ("DDW".equalsIgnoreCase(source)) {
            return "电商平台-当当网";
        } else if ("SNYG".equalsIgnoreCase(source)) {
            return "电商平台-苏宁易购旗舰店";
        } else if ("YHDZY".equalsIgnoreCase(source)) {
            return "电商平台-1号店自营";
        } else if ("TSPC".equalsIgnoreCase(source)) {
            return "统帅商城PC";
        } else if ("TSMOBILE".equalsIgnoreCase(source)) {
            return "统帅商城移动";
        } else if ("ZSH".equalsIgnoreCase(source)) {
            return "平台大客户-中石化";
        } else if ("YMX".equalsIgnoreCase(source)) {
            return "亚马逊";
        } else if ("SQXW".equalsIgnoreCase(source)) {
            return "商圈小微";
        } else if ("JDMK".equalsIgnoreCase(source)) {
            return "京东模卡";
        } else if ("TMMK".equalsIgnoreCase(source)) {
            return "mooka模卡官方旗舰店";
        } else if ("DBJ".equalsIgnoreCase(source)) {
            return "夺宝机";
        } else if ("GMZX".equalsIgnoreCase(source)) {
            return "国美在线";
        } else {
            return "";
        }
    }

    private static final Set<String> source3HSet = new HashSet<String>();

    static {
        source3HSet.add("1");
        source3HSet.add("DXS");
        source3HSet.add("MOBILE");
        source3HSet.add("CK");
        source3HSet.add("CK_MOBILE");
        source3HSet.add("MSTORE");
        source3HSet.add("HBSC");
        source3HSet.add("S_MOBILE");
        source3HSet.add("YJYD");
        source3HSet.add("FLW");
    }

    private static final Set<String> sku3HSet = new HashSet<String>();

    static {
        sku3HSet.add("BB09B1094");
        sku3HSet.add("BB0A4008V");
        sku3HSet.add("BH02W207H");
        sku3HSet.add("B70U200AA");
        sku3HSet.add("BH03600A4");
        sku3HSet.add("B30HL001T");
        sku3HSet.add("CE0JK400M");
        sku3HSet.add("CE0JP8017");
        sku3HSet.add("GD0LSD00W");
        sku3HSet.add("GD0LSB00W");
        sku3HSet.add("GA0SL201C");
        sku3HSet.add("GA0SP0000");
        sku3HSet.add("GD0N5A001");
        sku3HSet.add("GD0N58001");
    }

    private static final Set<Integer> region3HSet = new HashSet<Integer>();

    static {
        region3HSet.add(938);
        region3HSet.add(943);
        region3HSet.add(2839);
        region3HSet.add(2681);
        region3HSet.add(2689);
        region3HSet.add(2825);
        region3HSet.add(2824);
        region3HSet.add(2803);
        region3HSet.add(2804);
        region3HSet.add(2802);
        region3HSet.add(2799);
        region3HSet.add(2662);
        region3HSet.add(2666);
        region3HSet.add(2668);
        region3HSet.add(2608);
        region3HSet.add(2607);
        region3HSet.add(2264);
        region3HSet.add(2262);
        region3HSet.add(2256);
        region3HSet.add(2265);
        region3HSet.add(2977);
        region3HSet.add(2974);
        region3HSet.add(2972);
        region3HSet.add(12022619);
        region3HSet.add(12022621);
        region3HSet.add(1686);
        region3HSet.add(1685);
        region3HSet.add(3240);
        region3HSet.add(3242);
        region3HSet.add(657);
        region3HSet.add(664);
        region3HSet.add(662);
        region3HSet.add(661);
        region3HSet.add(687);
        region3HSet.add(689);
        region3HSet.add(691);
        region3HSet.add(458);
        region3HSet.add(570);
        region3HSet.add(459);
        region3HSet.add(460);
        region3HSet.add(1293);
        region3HSet.add(1290);
        region3HSet.add(1294);
        region3HSet.add(2286);
        region3HSet.add(2120);
        region3HSet.add(1764);
        region3HSet.add(2454);
        region3HSet.add(2453);
        region3HSet.add(2446);
        region3HSet.add(2913);
        region3HSet.add(1777);
        region3HSet.add(2167);
        region3HSet.add(2175);
        region3HSet.add(2303);
        region3HSet.add(2304);
        region3HSet.add(2584);
        region3HSet.add(2588);
        region3HSet.add(3138);
        region3HSet.add(3142);
        region3HSet.add(3524);
        region3HSet.add(1621);
        region3HSet.add(1619);
        region3HSet.add(1618);
        region3HSet.add(1620);
        region3HSet.add(2570);
        region3HSet.add(1288);
        region3HSet.add(2185);
        region3HSet.add(1931);
        region3HSet.add(1928);
        region3HSet.add(2967);
        region3HSet.add(2965);
        region3HSet.add(2970);
        region3HSet.add(2966);
        region3HSet.add(3615);
        region3HSet.add(757);
    }

    public static boolean get3HMark(String source, String sku, Integer region) {
        if (StringUtil.isEmpty(source) || StringUtil.isEmpty(sku) || region == null) {
            return false;
        }
        if (source3HSet.contains(source) && sku3HSet.contains(sku)
            && region3HSet.contains(region)) {
            return true;
        }
        return false;
    }

    public static Map<String, String> SCODE_MAP = new HashMap<String, String>();

    static {
        SCODE_MAP.put("ASWA", "ASO1");
        SCODE_MAP.put("AKWA", "AKO1");
        SCODE_MAP.put("YBWA", "YBO1");
        SCODE_MAP.put("BSWA", "BSO1");
        SCODE_MAP.put("BBWA", "BBO1");
        SCODE_MAP.put("BTWA", "BTO1");
        SCODE_MAP.put("BDWA", "BDO1");
        SCODE_MAP.put("BJWA", "BJO2");
        SCODE_MAP.put("CZWA", "CZO1");
        SCODE_MAP.put("DCWA", "DCO1");
        SCODE_MAP.put("CDWA", "CDO1");
        SCODE_MAP.put("LDWA", "LDO1");
        SCODE_MAP.put("DLWA", "DLO1");
        SCODE_MAP.put("DQWA", "DQO1");
        SCODE_MAP.put("DTWA", "DTO1");
        SCODE_MAP.put("DGWA", "DGO1");
        SCODE_MAP.put("FSWA", "FSO1");
        SCODE_MAP.put("FZWA", "FZO1");
        SCODE_MAP.put("FYWA", "FYO1");
        SCODE_MAP.put("JXWA", "JXO1");
        SCODE_MAP.put("GAWA", "GAO1");
        SCODE_MAP.put("LGWA", "LGO1");
        SCODE_MAP.put("GYWA", "GYO1");
        SCODE_MAP.put("DBWA", "DBO1");
        SCODE_MAP.put("HKWA", "HKO1");
        SCODE_MAP.put("HDWA", "HDO1");
        SCODE_MAP.put("HZWA", "HZO1");
        SCODE_MAP.put("HFWA", "HFO1");
        SCODE_MAP.put("HNWA", "HNO2");
        SCODE_MAP.put("WYWA", "WYO1");
        SCODE_MAP.put("HYWA", "HYO1");
        SCODE_MAP.put("HMWA", "HMO1");
        SCODE_MAP.put("HSWA", "HSO1");
        SCODE_MAP.put("HAWA", "HAO1");
        SCODE_MAP.put("JOWA", "JOO2");
        SCODE_MAP.put("SDWA", "SDO2");
        SCODE_MAP.put("JNWA", "JNO2");
        SCODE_MAP.put("JMWA", "JMO1");
        SCODE_MAP.put("JHWA", "JHO1");
        SCODE_MAP.put("JZWA", "JZO1");
        SCODE_MAP.put("KMWA", "KMO1");
        SCODE_MAP.put("LZWA", "LZO1");
        SCODE_MAP.put("LYWA", "LYO2");
        SCODE_MAP.put("GXWA", "GXO1");
        SCODE_MAP.put("DWWA", "DWO1");
        SCODE_MAP.put("LXWA", "LXO1");
        SCODE_MAP.put("LMWA", "LMO2");
        SCODE_MAP.put("LHWA", "LHO2");
        SCODE_MAP.put("MMWA", "MMO1");
        SCODE_MAP.put("MYWA", "MYO1");
        SCODE_MAP.put("NCWA", "NCO1");
        SCODE_MAP.put("SCWA", "SCO1");
        SCODE_MAP.put("NJWA", "NJO1");
        SCODE_MAP.put("NNWA", "NNO1");
        SCODE_MAP.put("NPWA", "NPO1");
        SCODE_MAP.put("NYWA", "NYO2");
        SCODE_MAP.put("NBWA", "NBO2");
        SCODE_MAP.put("QZWA", "QZO1");
        SCODE_MAP.put("XMWA", "XMO1");
        SCODE_MAP.put("STWA", "STO1");
        SCODE_MAP.put("SQWA", "SQO2");
        SCODE_MAP.put("SHWA", "SHO2");
        SCODE_MAP.put("SRWA", "SRO1");
        SCODE_MAP.put("SYWA", "SYO1");
        SCODE_MAP.put("HBWA", "HBO1");
        SCODE_MAP.put("XXWA", "XXO1");
        SCODE_MAP.put("SOWA", "SOO1");
        SCODE_MAP.put("TZWA", "TZO1");
        SCODE_MAP.put("TYWA", "TYO1");
        SCODE_MAP.put("TSWA", "TSO1");
        SCODE_MAP.put("TJWA", "TJO1");
        SCODE_MAP.put("GSWA", "GSO1");
        SCODE_MAP.put("WFWA", "WFO2");
        SCODE_MAP.put("NWWA", "NWO2");
        SCODE_MAP.put("WXWA", "WXO1");
        SCODE_MAP.put("WNWA", "WNO1");
        SCODE_MAP.put("WHWA", "WHO2");
        SCODE_MAP.put("XAWA", "XAO1");
        SCODE_MAP.put("XNWA", "XNO1");
        SCODE_MAP.put("XFWA", "XFO2");
        SCODE_MAP.put("XJWA", "XJO1");
        SCODE_MAP.put("XYWA", "XYO2");
        SCODE_MAP.put("XZWA", "XZO1");
        SCODE_MAP.put("YTWA", "YTO2");
        SCODE_MAP.put("CYWA", "CYO2");
        SCODE_MAP.put("YCWA", "YCO1");
        SCODE_MAP.put("YLWA", "YLO1");
        SCODE_MAP.put("ZJWA", "ZJO1");
        SCODE_MAP.put("CCWA", "CCO1");
        SCODE_MAP.put("CSWA", "CSO1");
        SCODE_MAP.put("ZZWA", "ZZO2");
        SCODE_MAP.put("CQWA", "CQO1");
        SCODE_MAP.put("ZBWA", "ZBO2");
        SCODE_MAP.put("ZYWA", "ZYO1");
    }
}