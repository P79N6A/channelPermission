package com.haier.svc.api.controller.util.http.jingdong;

import com.alibaba.fastjson.annotation.JSONField;

public class ItemInfo {
	@JSONField(name="sku_id")
	private String skuId;	//京东内部SKU的ID
	
	@JSONField(name="outer_sku_id")
	private String outerSkuId;	//SKU外部ID（极端情况下不保证返回，建议从商品接口获取
	
	@JSONField(name="sku_name")
	private String skuName;	//商品的名称+SKU规格
	
	@JSONField(name="jd_price")
	private String jdPrice;	//SKU的京东价
	
	@JSONField(name="gift_point")
	private String giftPoint;	//赠送积分
	
	@JSONField(name="ware_id")
	private String wareId;	//京东内部商品ID（极端情况下不保证返回，建议从商品接口获取
	
	@JSONField(name="item_total")
	private String itemTotal;	//数量
	
	@JSONField(name="product_no")
	private String productNo;	//商品货号（极端情况下不保证返回，建议从商品接口获取
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOuterSkuId() {
		return outerSkuId;
	}
	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(String jdPrice) {
		this.jdPrice = jdPrice;
	}
	public String getGiftPoint() {
		return giftPoint;
	}
	public void setGiftPoint(String giftPoint) {
		this.giftPoint = giftPoint;
	}
	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public String getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
}
