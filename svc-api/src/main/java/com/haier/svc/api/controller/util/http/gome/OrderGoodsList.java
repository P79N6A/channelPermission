package com.haier.svc.api.controller.util.http.gome;

/**
 * Created by LuJun on 16/5/11.
 */
public class OrderGoodsList {
    private String skuNo;//订单商品/商品编号
    private String goodsName;//订单商品/商品名称
    private String quantity;//订单商品/商品数量
    private String masLoc;//订单商品/库区
    private String goodsPrice;//订单商品/网站售价
    private String partDiscountPrice;//订单商品/折扣金额
    private String providerSkuNo;//sku
    private String redCouponValue;//红券
    private String blueCouponValue;//蓝券


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getMasLoc() {
        return masLoc;
    }

    public void setMasLoc(String masLoc) {
        this.masLoc = masLoc;
    }

    public String getPartDiscountPrice() {
        return partDiscountPrice;
    }

    public void setPartDiscountPrice(String partDiscountPrice) {
        this.partDiscountPrice = partDiscountPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getProviderSkuNo() {
        return providerSkuNo;
    }

    public void setProviderSkuNo(String providerSkuNo) {
        this.providerSkuNo = providerSkuNo;
    }
    
	public String getRedCouponValue() {
		return redCouponValue;
	}

	public void setRedCouponValue(String redCouponValue) {
		this.redCouponValue = redCouponValue;
	}

	public String getBlueCouponValue() {
		return blueCouponValue;
	}

	public void setBlueCouponValue(String blueCouponValue) {
		this.blueCouponValue = blueCouponValue;
	}
}
