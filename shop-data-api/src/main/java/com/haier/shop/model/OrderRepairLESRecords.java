package com.haier.shop.model;

import java.io.Serializable;
/*
*
*
* 作者:张波
* 2017/12/19
* */
public class OrderRepairLESRecords implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID              = -7266140184630642105L;

    /**
     * 批次 - 良品
     */
    public static Integer     STORAGE_TYPE_GOOD             = 10;
    /**
     * 批次 - 开箱品
     */
    public static Integer     STORAGE_TYPE_OPENED           = 22;
    /**
     * 批次 - 不良品
     */
    public static Integer     STORAGE_TYPE_BAD              = 21;
    /**
     * 批次 - 样品
     */
    public static Integer     STORAGE_TYPE_SAMPLE           = 40;
    /**
     * 批次 - 样品
     */
    public static Integer     STORAGE_TYPE_SAMPLE_SHOP      = 41;

    /**
     * 操作 - 入库-网点不质检
     */
    public static Integer     OPERATE_NETPOINT_DONOTINSPECT = 11;
    /**
     * 操作 - 入库-网点质检
     */
    public static Integer     OPERATE_NETPOINT_INSPECT      = 12;
    /**
     * 操作 - 出库-存性变更
     */
    public static Integer     OPERATE_CHANGE_OUT            = 21;
    /**
     * 操作 - 入库-存性变更
     */
    public static Integer     OPERATE_CHANGE_IN             = 13;

    private String productId;
    private Double price;
    private int number;
    private String productName;
    private String sku;
    private String failReason;//失败原因
    private String falg;
    private Integer           id;
    private int pushFailNumber;//推送失败次数
    private String repairSn;
    
    
    
    public String getRepairSn() {
		return repairSn;
	}

	public void setRepairSn(String repairSn) {
		this.repairSn = repairSn;
	}

	public String getFalg() {
		return falg;
	}

	public void setFalg(String falg) {
		this.falg = falg;
	}


	public int getPushFailNumber() {
		return pushFailNumber;
	}

	public void setPushFailNumber(int pushFailNumber) {
		this.pushFailNumber = pushFailNumber;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public static Integer getSTORAGE_TYPE_GOOD() {
		return STORAGE_TYPE_GOOD;
	}

	public static void setSTORAGE_TYPE_GOOD(Integer sTORAGE_TYPE_GOOD) {
		STORAGE_TYPE_GOOD = sTORAGE_TYPE_GOOD;
	}

	public static Integer getSTORAGE_TYPE_OPENED() {
		return STORAGE_TYPE_OPENED;
	}

	public static void setSTORAGE_TYPE_OPENED(Integer sTORAGE_TYPE_OPENED) {
		STORAGE_TYPE_OPENED = sTORAGE_TYPE_OPENED;
	}

	public static Integer getSTORAGE_TYPE_BAD() {
		return STORAGE_TYPE_BAD;
	}

	public static void setSTORAGE_TYPE_BAD(Integer sTORAGE_TYPE_BAD) {
		STORAGE_TYPE_BAD = sTORAGE_TYPE_BAD;
	}

	public static Integer getSTORAGE_TYPE_SAMPLE() {
		return STORAGE_TYPE_SAMPLE;
	}

	public static void setSTORAGE_TYPE_SAMPLE(Integer sTORAGE_TYPE_SAMPLE) {
		STORAGE_TYPE_SAMPLE = sTORAGE_TYPE_SAMPLE;
	}

	public static Integer getSTORAGE_TYPE_SAMPLE_SHOP() {
		return STORAGE_TYPE_SAMPLE_SHOP;
	}

	public static void setSTORAGE_TYPE_SAMPLE_SHOP(Integer sTORAGE_TYPE_SAMPLE_SHOP) {
		STORAGE_TYPE_SAMPLE_SHOP = sTORAGE_TYPE_SAMPLE_SHOP;
	}

	public static Integer getOPERATE_NETPOINT_DONOTINSPECT() {
		return OPERATE_NETPOINT_DONOTINSPECT;
	}

	public static void setOPERATE_NETPOINT_DONOTINSPECT(Integer oPERATE_NETPOINT_DONOTINSPECT) {
		OPERATE_NETPOINT_DONOTINSPECT = oPERATE_NETPOINT_DONOTINSPECT;
	}

	public static Integer getOPERATE_NETPOINT_INSPECT() {
		return OPERATE_NETPOINT_INSPECT;
	}

	public static void setOPERATE_NETPOINT_INSPECT(Integer oPERATE_NETPOINT_INSPECT) {
		OPERATE_NETPOINT_INSPECT = oPERATE_NETPOINT_INSPECT;
	}

	public static Integer getOPERATE_CHANGE_OUT() {
		return OPERATE_CHANGE_OUT;
	}

	public static void setOPERATE_CHANGE_OUT(Integer oPERATE_CHANGE_OUT) {
		OPERATE_CHANGE_OUT = oPERATE_CHANGE_OUT;
	}

	public static Integer getOPERATE_CHANGE_IN() {
		return OPERATE_CHANGE_IN;
	}

	public static void setOPERATE_CHANGE_IN(Integer oPERATE_CHANGE_IN) {
		OPERATE_CHANGE_IN = oPERATE_CHANGE_IN;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Long addTime;

    /**
     * 获取 创建时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }
    
    private String addTimeTs;
    
    

    public String getAddTimeTs() {
		return addTimeTs;
	}

	public void setAddTimeTs(String addTimeTs) {
		this.addTimeTs = addTimeTs;
	}

	/**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单ID。
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单ID。
     *
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer orderRepairId;

    private Integer opCancelFlag;

    /**
     * 获取 退货申请单ID。
     */
    public Integer getOrderRepairId() {
        return this.orderRepairId;
    }

    /**
     * 设置 退货申请单ID。
     *
     * @param value 属性值
     */
    public void setOrderRepairId(Integer value) {
        this.orderRepairId = value;
    }

    private String recordSn;

    /**
     * 获取 记录单号。
     */
    public String getRecordSn() {
        return this.recordSn;
    }

    /**
     * 设置 记录单号。
     *
     * @param value 属性值
     */
    public void setRecordSn(String value) {
        this.recordSn = value;
    }

    private Integer operate;

    /**
     * 获取 操作，1=出库；2=入库。
     */
    public Integer getOperate() {
        return this.operate;
    }

    /**
     * 设置 操作，1=出库；2=入库。
     *
     * @param value 属性值
     */
    public void setOperate(Integer value) {
        this.operate = value;
    }

    private Integer storageType;

    /**
     * 获取 批次，22；21；10。
     */
    public Integer getStorageType() {
        return this.storageType;
    }

    /**
     * 设置 批次，22；21；10。
     *
     * @param value 属性值
     */
    public void setStorageType(Integer value) {
        this.storageType = value;
    }

    private String lesOrderSn;

    /**
     * 获取 LES提单号。
     */
    public String getLesOrderSn() {
        return this.lesOrderSn;
    }

    /**
     * 设置 LES提单号。
     *
     * @param value 属性值
     */
    public void setLesOrderSn(String value) {
        this.lesOrderSn = value;
    }

    private String lesOutPing;

    /**
     * 获取 LES出入库凭证号。
     */
    public String getLesOutPing() {
        return this.lesOutPing;
    }

    /**
     * 设置 LES出入库凭证号。
     *
     * @param value 属性值
     */
    public void setLesOutPing(String value) {
        this.lesOutPing = value;
    }

    private Long lesOutPingTime;

    /**
     * 获取 LES回传凭证号时间。
     */
    public Long getLesOutPingTime() {
        return this.lesOutPingTime;
    }

    /**
     * 设置 LES回传凭证号时间。
     *
     * @param value 属性值
     */
    public void setLesOutPingTime(Long value) {
        this.lesOutPingTime = value;
    }

    private String sCode;

    /**
     * 获取 库位编码。
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    private Integer success;

    /**
     * 获取 是否成功。
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 是否成功。
     *
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    /**
     * 开单成功时间
     */
    private long lesOrderSnTime;

    public long getLesOrderSnTime() {
        return lesOrderSnTime;
    }

    public void setLesOrderSnTime(long lesOrderSnTime) {
        this.lesOrderSnTime = lesOrderSnTime;
    }

    public Integer getOpCancelFlag() {
        return opCancelFlag;
    }

    public void setOpCancelFlag(Integer opCancelFlag) {
        this.opCancelFlag = opCancelFlag;
    }
}
