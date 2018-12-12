package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CBS与财务接口。(3W正品退仓)
 * @author wangp-c
 *
 */
public class EisInterfaceFinance3w implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer lesStockTransQueue3WId;

    public Integer getLesStockTransQueue3WId() {
		return lesStockTransQueue3WId;
	}

	public void setLesStockTransQueue3WId(Integer lesStockTransQueue3WId) {
		this.lesStockTransQueue3WId = lesStockTransQueue3WId;
	}

	private String interfaceCode;

    /**
     * 获取 接口编码。
     */
    public String getInterfaceCode() {
        return this.interfaceCode;
    }

    /**
     * 设置 接口编码。
     *
     * @param value 属性值
     */
    public void setInterfaceCode(String value) {
        this.interfaceCode = value;
    }

    /**
    * 成功
    */
    public final static int STATUS_SUCCESS = 1;
    /**
     * 未知（需要调用状态查询接口）
     */
    public final static int STATUS_UNKNOWN = 0;
    /**
     * 失败（需要重新处理）
     */
    public final static int STATUS_FAILED  = 2;
    /**
     * 错误（无法处理）
     */
    public final static int STATUS_ERROR   = 3;

    /**
     * 出现特殊处理
     */
    public final static int STATUS_WARN    = -1;

    private Integer         status;

    /**
     * 获取 0：未知（需调用状态查询接口）。
     *
     * <p>
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 0：未知（需调用状态查询接口）。
     *
     * <p>
     * 1：成功<br />
     * 2：失败（重新发送）<br />
     * 3：错误（系统无法处理）
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String eaiType;

    /**
     * 获取 EAI反馈类型。
     */
    public String getEaiType() {
        return this.eaiType;
    }

    /**
     * 设置 EAI反馈类型。
     *
     * @param value 属性值
     */
    public void setEaiType(String value) {
        this.eaiType = value;
    }

    private String message;

    /**
     * 获取 信息。
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置 信息。
     *
     * @param value 属性值
     */
    public void setMessage(String value) {
        this.message = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

    private Date updateTime;

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private Integer eaiDataLogId;

    /**
     * 获取 EAI接口日志标识。
     */
    public Integer getEaiDataLogId() {
        return this.eaiDataLogId;
    }

    /**
     * 设置 EAI接口日志标识。
     *
     * @param value 属性值
     */
    public void setEaiDataLogId(Integer value) {
        this.eaiDataLogId = value;
    }

}