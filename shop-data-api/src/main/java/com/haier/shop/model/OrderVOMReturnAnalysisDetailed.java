package com.haier.shop.model;

/**
 * 接收VOM推送到CBS数据之后解析出来的 明细 （明细表）
 * @author wukunyang
 *
 */
public class OrderVOMReturnAnalysisDetailed {
    private Integer id;

    private String itemno;

    private String storageType;

    private String productCode;

    private String hrCode;

    private String prodes;

    private Integer number;

    private String isComPlete;

    private String volume;

    private String weight;

    private String attriButes;

    private String remark;

    private String remark1;

    private String remark2;

    private Integer addTime;
    
    private Integer analysisId;//OrderVOMReturnAnalysis 主表单关联ID
    
    
    
    public Integer getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno == null ? null : itemno.trim();
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getHrCode() {
        return hrCode;
    }

    public void setHrCode(String hrCode) {
        this.hrCode = hrCode == null ? null : hrCode.trim();
    }

    public String getProdes() {
        return prodes;
    }

    public void setProdes(String prodes) {
        this.prodes = prodes == null ? null : prodes.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIsComPlete() {
        return isComPlete;
    }

    public void setIsComPlete(String isComPlete) {
        this.isComPlete = isComPlete == null ? null : isComPlete.trim();
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume == null ? null : volume.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getAttriButes() {
        return attriButes;
    }

    public void setAttriButes(String attriButes) {
        this.attriButes = attriButes == null ? null : attriButes.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

   
}