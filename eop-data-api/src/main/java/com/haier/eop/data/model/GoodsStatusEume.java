package com.haier.eop.data.model;

/**
 * Created by Administrator on 2017/8/21/021.
 *
 * 菜鸟货品状态枚举
 * SELLABLE(1, "可销售库存"),
 * DEFECTIVE(101, "残次"),
 * ENIGINEDAMAGE(102, "机损"),
 * BOXLOSS(103, "箱损"),
 * MD_IDENTIFY(104, "机损待鉴定"),
 * BD_IDENTIFY(105, "箱损待鉴定"),
 * MD_MERCHANT_RESP(106, "机损商家责任"),
 * MD_LOGISTICS_RESP(107, "机损物流责任"),
 * BD_LOGISTICS_RESP(108, "箱损物流责任"),
 * VISIT_DEFECTIVE(109, "上门退货残次"),
 *
 */
public enum GoodsStatusEume {
    GENUINE(1),//正品
    REMNANT(101),// 残次
    MACHINELOSS(102),// 机损
    BOXLOSS(103),// 箱损
    FREEZE(104),// 冻结
    PULLON(401);// 调拔在途
    private int goodsStatus;
    private GoodsStatusEume(int goodsStatus){
            this.goodsStatus = goodsStatus;
    }
    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }
}
