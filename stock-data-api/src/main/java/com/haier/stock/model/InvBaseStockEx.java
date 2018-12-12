package com.haier.stock.model;


public class InvBaseStockEx extends InvBaseStock {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            lesSecCode;

    /**
     * 获取 库位编码。
     */
    public String getLesSecCode() {
        return this.lesSecCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setLesSecCode(String value) {
        this.lesSecCode = value;
    }

    /**
     * 库位属性
     */
    private String itemProperty;

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

    /**
     * 库位名称
     */
    private String secName;

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    /**
     * 品类
     */
    private String cbsCategory;

    public String getCbsCategory() {
        return cbsCategory;
    }

    public void setCbsCategory(String cbsCategory) {
        this.cbsCategory = cbsCategory;
    }

    /**
     * 产品型号 
     */
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    private String startDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
    
    private String endDate;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    
	/**
	 * 可用库存
	 */
	private Integer avaiableQty =0;

	public Integer getAvaiableQty() {
		return avaiableQty;
	}

	public void setAvaiableQty(Integer avaiableQty) {
		this.avaiableQty = avaiableQty;
	}
	
}