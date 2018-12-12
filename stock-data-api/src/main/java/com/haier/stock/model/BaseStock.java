package com.haier.stock.model;

public class BaseStock {

    private String             sku;
    private String             code;
   
    private String             itemProperty;
    private String             stockType;          //WA,EC
    private Integer            frozenQty;
    private Integer stockQty ;
    public static final String STOCKTYPE_WA = "WA";
    public static final String STOCKTYPE_EC = "EC";

    public Integer getFrozenQty() {
		return frozenQty;
	}

	public void setFrozenQty(Integer frozenQty) {
		this.frozenQty = frozenQty;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

}
