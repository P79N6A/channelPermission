package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>Regions</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>parentId</td><td>{@link Integer}</td><td>parentId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>regionName</td><td>{@link String}</td><td>regionName</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>parentPath</td><td>{@link String}</td><td>parentPath</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>firstLetter</td><td>{@link String}</td><td>firstLetter</td><td>char</td><td>&nbsp;</td></tr>
 *   <tr><td>regionType</td><td>{@link Integer}</td><td>regionType</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>shippingTime</td><td>{@link String}</td><td>shippingTime</td><td>varchar</td><td>配送时效</td></tr>
 *   <tr><td>visible</td><td>{@link Integer}</td><td>visible</td><td>tinyint</td><td>是否可见</td></tr>
 *   <tr><td>rowId</td><td>{@link String}</td><td>rowId</td><td>varchar</td><td>HP地址库映射字段</td></tr>
 *   <tr><td>testNum</td><td>{@link Integer}</td><td>testNum</td><td>tinyint</td><td>测试字段</td></tr>
 *   <tr><td>standardRegionId</td><td>{@link Integer}</td><td>standardRegionId</td><td>int</td><td>标准地区ID</td></tr>
 *   <tr><td>code</td><td>{@link String}</td><td>code</td><td>varchar</td><td>国标编码</td></tr>
 *   <tr><td>activeFlag</td><td>{@link Boolean}</td><td>activeFlag</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>hasRead</td><td>{@link Integer}</td><td>hasRead</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>isSupportCod</td><td>{@link Integer}</td><td>isSupportCod</td><td>tinyint</td><td>是否支持货到付款</td></tr>
 * </table>
 * @author rongmai
 */
public class Regions implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -1844830129539549413L;
	private Integer				id;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	private Integer	parentId	= 0;
	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Integer value) {
		this.parentId = value;
	}
	
	private String	regionName;
	
	public String getRegionName() {
		return this.regionName;
	}
	
	public void setRegionName(String value) {
		this.regionName = value;
	}
	
	private String	parentPath;
	
	public String getParentPath() {
		return this.parentPath;
	}
	
	public void setParentPath(String value) {
		this.parentPath = value;
	}
	
	private String	firstLetter;
	
	public String getFirstLetter() {
		return this.firstLetter;
	}
	
	public void setFirstLetter(String value) {
		this.firstLetter = value;
	}
	
	private Integer	regionType	= 2;
	
	public Integer getRegionType() {
		return this.regionType;
	}
	
	public void setRegionType(Integer value) {
		this.regionType = value;
	}
	
	private String	shippingTime;
	
	/**
	 * 获取 配送时效
	 */
	public String getShippingTime() {
		return this.shippingTime;
	}
	
	/**
	 * 设置 配送时效
	 * @param value 属性值
	 */
	public void setShippingTime(String value) {
		this.shippingTime = value;
	}
	
	private Integer	visible;
	
	/**
	 * 获取 是否可见
	 */
	public Integer getVisible() {
		return this.visible;
	}
	
	/**
	 * 设置 是否可见
	 * @param value 属性值
	 */
	public void setVisible(Integer value) {
		this.visible = value;
	}
	
	private String	rowId;
	
	/**
	 * 获取 HP地址库映射字段
	 */
	public String getRowId() {
		return this.rowId;
	}
	
	/**
	 * 设置 HP地址库映射字段
	 * @param value 属性值
	 */
	public void setRowId(String value) {
		this.rowId = value;
	}
	
	private Integer	testNum;
	
	/**
	 * 获取 测试字段
	 */
	public Integer getTestNum() {
		return this.testNum;
	}
	
	/**
	 * 设置 测试字段
	 * @param value 属性值
	 */
	public void setTestNum(Integer value) {
		this.testNum = value;
	}
	
	private Integer	standardRegionId;
	
	/**
	 * 获取 标准地区ID
	 */
	public Integer getStandardRegionId() {
		return this.standardRegionId;
	}
	
	/**
	 * 设置 标准地区ID
	 * @param value 属性值
	 */
	public void setStandardRegionId(Integer value) {
		this.standardRegionId = value;
	}
	
	private String	code;
	
	/**
	 * 获取 国标编码
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * 设置 国标编码
	 * @param value 属性值
	 */
	public void setCode(String value) {
		this.code = value;
	}
	
	private Boolean	activeFlag	= true;
	
	public Boolean getActiveFlag() {
		return this.activeFlag;
	}
	
	public void setActiveFlag(Boolean value) {
		this.activeFlag = value;
	}
	
	private Integer	hasRead	= 0;
	
	public Integer getHasRead() {
		return this.hasRead;
	}
	
	public void setHasRead(Integer value) {
		this.hasRead = value;
	}
	
	private Integer	isSupportCod;
	
	/**
	 * 获取 是否支持货到付款
	 */
	public Integer getIsSupportCod() {
		return this.isSupportCod;
	}
	
	/**
	 * 设置 是否支持货到付款
	 * @param value 属性值
	 */
	public void setIsSupportCod(Integer value) {
		this.isSupportCod = value;
	}
	
    private Integer isOto;

    /**
     * 获取 是否oto指定派工
     */
    public Integer getIsOto() {
        return isOto;
    }

    /**
     * 设置 是否oto指定派工
     * @param isOto
     */
    public void setIsOto(Integer isOto) {
        this.isOto = isOto;
    }

}
