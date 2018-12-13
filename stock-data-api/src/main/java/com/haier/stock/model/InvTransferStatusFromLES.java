package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

public class InvTransferStatusFromLES implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5362291018982842993L;

	private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }
    
    /**
     * 业务单号：订单号
     */
    private String orderNo;
    /**
     * 仓库编码：按日日顺C码
     */
    private String storeCode;
    /**
     *  状态
		信息内容：
		WMS_ACCEPT -接单
		WMS_FAILED -拒单
		TMS_ACCEPT 揽收
		TMS_REJECT -揽收失败
		TMS_DELIVERING -派送
		TMS_STATION_IN -分站进
		TMS_STATION_OUT -分站出
		TMS_CHANGE -用户改约
		TMS_ERROR -异常
		TMS_SIGN -签收成功
		TMS_FAILED -拒签
		TMS_DELIVERY-网点交付
		TMS_FAILED_IN -转运入库
		TMS_FAILED_OUT -转运出库
		TMS_RETURN-拒收入库
		TMS_RESULT_S-取件成功
		TMS_RESULT_F-取件失败
		COD_SUCCESS 扣款成功
		TMS_DB_CREATE 调拨单生成
		TMS_DB_OUT调拨单出库
		TMS_DB_IN 调拨单入库
		TMS_STATEMENT 结单（配送结束）
		TMS_ACCOUNT 对账（收款信息）
     */
    private String orderStatus;

    private String outCode;

    public String getOutCode() {
        return this.outCode;
    }

    public void setOutCode(String value) {
        this.outCode = value;
    }

    private String buType;

    /**
     * 获取 通知类型：接口方法名。
     */
    public String getBuType() {
        return this.buType;
    }

    /**
     * 设置 通知类型：接口方法名。
     *
     * @param value 属性值
     */
    public void setBuType(String value) {
        this.buType = value;
    }

    private Date notifyTime;

    /**
     * 获取 通知时间。
     */
    public Date getNotifyTime() {
        return this.notifyTime;
    }

    /**
     * 设置 通知时间。
     *
     * @param value 属性值
     */
    public void setNotifyTime(Date value) {
        this.notifyTime = value;
    }

    private String source;

    /**
     * 获取 系统来源。
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 系统来源。
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private String type;

    /**
     * 获取 报文格式：xml/json。
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 报文格式：xml/json。
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    private String sign;

    /**
     * 获取 签名。
     */
    public String getSign() {
        return this.sign;
    }

    /**
     * 设置 签名。
     *
     * @param value 属性值
     */
    public void setSign(String value) {
        this.sign = value;
    }

    private String content;

    /**
     * 获取 消息内容。
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置 消息内容。
     *
     * @param value 属性值
     */
    public void setContent(String value) {
        this.content = value;
    }

    public static final int STATUS_NEW   = 0;

    public static final int STATUS_DONE  = 1;

    public static final int STATUS_FAIL  = 2;

    public static final int STATUS_SYNCH = 3;

    private Integer         status;

    /**
     * 获取 状态：0：未处理 1：完成 2：失败 3：已同步采购系统。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态：0：未处理 1：完成 2：失败。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
