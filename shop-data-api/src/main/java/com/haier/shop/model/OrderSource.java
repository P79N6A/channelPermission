package com.haier.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSource implements Serializable {

    /**
     * 
     */
    private static final long         serialVersionUID = 5676103010073235055L;
    private String[]                  id               = new String[] { "-1", "1", "19", "TBSC",
            "TOPFENXIAO", "TOPBUYBANG", "TOPBOILER", "TOPSHJD", "TOPMOBILE", "TONGSHUAI",
            "YIHAODIAN", "DALOU", "CORPORATE", "CORPORATE_SJMG", "99", "B2B", "CRM", "11", "15",
            "SC20", "SC11", "SC23", "SC13", "DL1", "201106080001", "SC16", "6", "12", "13", "8",
            "30", "4", "10", "201106230021", "SC15", "SC12", "5", "7", "LLIG", "1000", "SC14",
            "17", "SC22", "3", "1001", "TBFX", "16", "TBSC2", "2", "9", "SC18", "18", "1002",
            "SC9", "95533", "CCBSC", "CCBSR", "10", "12", "111", "112", "113", "114", "115",
            "OTHER", "ZPTH", "HPB2B", "TOPXB", "TOPFENXIAOXB", "C2BWASHER", "MOBILE", "COS", "RRS",
            "SCFX", "CK", "CK_MOBILE"                 };
    private String[]                  sourceName       = new String[] { "全部", "商城订单", "商城账户充值订单",
            "淘宝海尔官方旗舰店", "淘宝海尔官方旗舰店分销平台", "淘宝买帮", "淘宝海尔热水器专卖店", "淘宝海尔生活电器专卖店", "淘宝海尔手机专卖店",
            "统帅日日顺乐家专卖店", "1号店订单", "大楼订单", "CORPORATE", "CORPORATE_SJMG", "后台订单", "B2B", "CRM",
            "TM大单", "TM小单", "life会员", "财付通会员", "彩电会员", "村单联络人", "大楼展销", "大楼转单", "大学生创新应用大赛会员",
            "大学生创业会员", "导购大单", "导购小单", "电脑vip", "电视购物", "电视专卖店", "工贸大单", "顾服订单", "国网会员", "海尔内部员工",
            "俱乐部会员", "快钱会员", "狼狼爱购", "内部客户", "农行会员", "品牌家电网", "奇瑞会员", "企业会员", "售后技服", "淘宝分销",
            "淘宝商城", "淘宝商城转单", "特许经营店", "网站联盟", "微博用户", "银行分期", "预付款客户", "支付宝会员", "企业内购-建行",
            "建行商城接入订单", "建行善融商城订单", "本站订单", "淘宝订单", " 1号店订单", "商城订单_海尔地产", "企业订单_内购", "企业订单_集采",
            "企业订单_B2B", "其它来源", "正品退货", "海朋转TC订单", "海尔新宝旗舰店", "淘宝海尔新宝旗舰店分销平台", "C2B滚筒洗衣机订单",
            "移动商城", "样品机", "日日顺", "商城订单-分销渠道", "海尔创客", "海尔创客-移动端" };

    private List<Map<String, String>> source           = new ArrayList<Map<String, String>>();

    private static OrderSource        orderSource;

    public static OrderSource getInstance() {
        if (orderSource == null)
            orderSource = new OrderSource();
        return orderSource;
    }

    public OrderSource() {
        for (int i = 0; i < id.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id[i]);
            map.put("sourceName", sourceName[i]);
            source.add(map);
        }
    }

    public List<Map<String, String>> getSource() {
        return source;
    }

    public String getSourceName(String id) {
        for (int i = 0; i < this.id.length; i++) {
            if (this.id[i].equalsIgnoreCase(id))
                return sourceName[i];
        }
        return id;
    }

    public static Map<String, String> getExternalSource() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("TBSC", "淘宝海尔官方旗舰店");
        map.put("TOPFENXIAO", "淘宝海尔官方旗舰店分销平台");
        map.put("WASHER", "海尔洗衣机官方旗舰店");
        map.put("FRIDGE", "海尔冰冷官方旗舰店");
        map.put("TMMK", "mooka模卡官方旗舰店");
        map.put("TOPBOILER", "淘宝海尔热水器专卖店");
        map.put("TONGSHUAI", "统帅日日顺乐家专卖店");
        map.put("ICBC", "平台大客户-工商银行");
        map.put("YHD", "平台大客户-一号店");
        map.put("SCFX", "商城订单-分销渠道");
        map.put("GMZX", "国美在线");
        map.put("YMX", "亚马逊");
        map.put("ZSH", "平台大客户-中石化");
        map.put("YHDZY", "电商平台-1号店自营");
        map.put("SNYG", "电商平台-苏宁易购旗舰店");
        map.put("DDW", "电商平台-当当网");
        map.put("YLW", "电商平台-邮乐网");
        map.put("YDYZ", "移动商城_有赞微商城");
        map.put("HBSC", "海贝商城");
        map.put("KDGW", "移动商城_口袋购物");
        return map;
    }

    public static void main(String[] args) {
        System.out.println(new OrderSource().id.length);
        System.out.println(new OrderSource().sourceName.length);
    }
}
