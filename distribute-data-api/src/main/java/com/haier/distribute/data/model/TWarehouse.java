package com.haier.distribute.data.model;

import java.io.Serializable;
import java.util.Date;

public class TWarehouse implements Serializable{
	/**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3431631072818009195L;
	private Long id;

    private Integer channelId;

    private String warehouseCode;

    private String warehouseName;

    private String warehouseCodeHaier;

    private String warehouseNameHaier;

    private String warehouseType;

    private Integer pid;

    private String transmitCode;

    private String city;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;
    
    private String channelCode;
    private String channelName;
    
    private String cTime;
    /**
     * 始发仓名称
     */
    private String pname;
    public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}

	private String uTime;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

   

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

   

    public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseCodeHaier() {
		return warehouseCodeHaier;
	}

	public void setWarehouseCodeHaier(String warehouseCodeHaier) {
		this.warehouseCodeHaier = warehouseCodeHaier;
	}

	public String getWarehouseNameHaier() {
		return warehouseNameHaier;
	}

	public void setWarehouseNameHaier(String warehouseNameHaier) {
		this.warehouseNameHaier = warehouseNameHaier;
	}

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getTransmitCode() {
		return transmitCode;
	}

	public void setTransmitCode(String transmitCode) {
		this.transmitCode = transmitCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}