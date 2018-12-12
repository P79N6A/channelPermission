package com.haier.shop.model;

public class StockHolder {
    String  sku;
    String  secCode;
    Integer changeQty;

    public StockHolder(String sku, String secCode, Integer changeQty) {
        this.sku = sku;
        this.secCode = secCode;
        this.changeQty = changeQty;
    }

    public static StockHolder newInstance(String sku, String secCode, Integer changeQty) {
        return new StockHolder(sku, secCode, changeQty);
    }

    public String getSku() {
        return sku;
    }

    public String getSecCode() {
        return secCode;
    }

    public Integer getChangeQty() {
        return changeQty;
    }

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public void setChangeQty(Integer changeQty) {
		this.changeQty = changeQty;
	}
    
    

}
