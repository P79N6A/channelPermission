package com.haier.shop.model;

import java.io.Serializable;
/*
*
*
* 作者:张波
* 2017/12/19
* */
public class OrderRepairLESRecords implements Serializable {
    /**
     * 开单成功时间
     */
    private long lesOrderSnTime;

    private String lesOrderSnTimeTS;//用来转译中文格式的日期

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
    private Integer success;
    private String successStatus;
    private String sCode;
    private Long lesOutPingTime;
    private String lesOutPingTimeTs;//转成时间格式
    private String lesOutPing;
    private String lesOrderSn;
    private Integer storageType;
    private Integer operate;
    private String operateStatus;//用来转中文
    private Integer orderRepairId;

    private Integer opCancelFlag;
    private Integer orderProductId;



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

    
    public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

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






    public String getLesOutPingTimeTs() {
		return lesOutPingTimeTs;
	}

	public void setLesOutPingTimeTs(String lesOutPingTimeTs) {
		this.lesOutPingTimeTs = lesOutPingTimeTs;
	}

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



    public String getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(String successStatus) {
		this.successStatus = successStatus;
	}

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


    

    public String getLesOrderSnTimeTS() {
		return lesOrderSnTimeTS;
	}

	public void setLesOrderSnTimeTS(String lesOrderSnTimeTS) {
		this.lesOrderSnTimeTS = lesOrderSnTimeTS;
	}

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
