package com.haier.order.model;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/27 0027.
 */
public enum ShopEnum {
    TBSC(1,"top_","海尔官方淘宝旗舰店"),
    TOPBOILER(4,"rsq_","海尔热水器专卖店"),
    TONGSHUAI(7,"ts_","淘宝网统帅日日顺乐家专卖店"),
    TOPFENXIAO(8,"fx_","海尔官方旗舰店:营销02(分销平台)"),
    TOPXB(12,"topxb_","海尔新宝旗舰店"),
    TOPFENXIAOXB(13,"fxtopxb_","海尔新宝旗舰店分销平台"),
    TONGSHUAIFX(15,"tsfx_","统帅日日顺分销平台"),
    //    2015年618前新增模卡旗舰店，因为起名是出现冲突，现在做了两个枚举解决问题
    MOOKA(16,"mooka_","淘宝模卡旗舰店"),
    TMMK(16,"mooka_","mooka模卡官方旗舰店"),
    TMMKFX(26,"TMMKFX_","mooka模卡分销"),
    //  2015年8月10日新增海尔洗衣机旗舰店
    WASHER(17,"WASHER_","海尔洗衣机旗舰店"),
    //  2015年8月13日新增海尔冰冷旗舰店
    FRIDGE(18,"FRIDGE_","海尔冰冷旗舰店"),
    AIR(21,"AIR_","海尔空调旗舰店"),
    TBCT(22,"TBCT_","村淘海尔商家"),
    GQGYS(23,"GQGYS_","海尔官方旗舰店供应商"),
    TMKSD(27,"TMKSD_","天猫卡萨帝旗舰店"),
    TMTV(28,"TMTV_","海尔电视旗舰店"),
    TBCFDD(29,"TBCFDD_","海尔厨房大电旗舰店"),
    TBXCR(31,"TBXCR_","天猫小超人旗舰店"),
//    TOPSHJD(3,"shjd_","海尔生活电器专卖店"),
//    TOPDHSC(10,"shsc_","海尔生活家电旗舰店"),
//    TOPFENXIAODHSC(11,"fxshsc_","海尔生活家电旗舰店分销平台"),
    ;

    private ShopEnum(int configId,String orderPrefix, String shopName){
        this.configId = configId;
        this.orderPrefix = orderPrefix;
        this.shopName = shopName;
    };

    private int configId;
    private String orderPrefix;
    private String shopName;

    public String getOrderPrefix() {
        return orderPrefix;
    }
    public int getConfigId() {
        return configId;
    }
    public String getShopName() {
        return shopName;
    }
}
