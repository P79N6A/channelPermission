package com.haier.shop.model;

import java.io.Serializable;

/**
 * LES五码表（送达方信息表）。
 * <p>Table: <strong>LesFiveYards</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>fiveYard</td><td>{@link String}</td><td>fiveYard</td><td>varchar</td><td>LES送达方代码</td></tr>
 *   <tr><td>desc</td><td>{@link String}</td><td>desc</td><td>varchar</td><td>送达方描述</td></tr>
 *   <tr><td>jdeCode</td><td>{@link String}</td><td>jdeCode</td><td>varchar</td><td>JDE代码</td></tr>
 *   <tr><td>address</td><td>{@link String}</td><td>address</td><td>varchar</td><td>地址</td></tr>
 *   <tr><td>centerName</td><td>{@link String}</td><td>centerName</td><td>varchar</td><td>中心简称</td></tr>
 *   <tr><td>centerCode</td><td>{@link String}</td><td>centerCode</td><td>varchar</td><td>中心代码</td></tr>
 *   <tr><td>sCode</td><td>{@link String}</td><td>sCode</td><td>varchar</td><td>对应中心库位</td></tr>
 * </table>
 * @author rongmai
 */
public class LesFiveYards implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -5041549402389602815L;
	private Integer				id;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	private Integer	siteId;
	
	public Integer getSiteId() {
		return this.siteId;
	}
	
	public void setSiteId(Integer value) {
		this.siteId = value;
	}
	
	private String	fiveYard;
	
	/**
	 * 获取 LES送达方代码
	 */
	public String getFiveYard() {
		return this.fiveYard;
	}
	
	/**
	 * 设置 LES送达方代码
	 * @param value 属性值
	 */
	public void setFiveYard(String value) {
		this.fiveYard = value;
	}
	
	private String	desc;
	
	/**
	 * 获取 送达方描述
	 */
	public String getDesc() {
		return this.desc;
	}
	
	/**
	 * 设置 送达方描述
	 * @param value 属性值
	 */
	public void setDesc(String value) {
		this.desc = value;
	}
	
	private String	jdeCode;
	
	/**
	 * 获取 JDE代码
	 */
	public String getJdeCode() {
		return this.jdeCode;
	}
	
	/**
	 * 设置 JDE代码
	 * @param value 属性值
	 */
	public void setJdeCode(String value) {
		this.jdeCode = value;
	}
	
	private String	address;
	
	/**
	 * 获取 地址
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * 设置 地址
	 * @param value 属性值
	 */
	public void setAddress(String value) {
		this.address = value;
	}
	
	private String	centerName;
	
	/**
	 * 获取 中心简称
	 */
	public String getCenterName() {
		return this.centerName;
	}
	
	/**
	 * 设置 中心简称
	 * @param value 属性值
	 */
	public void setCenterName(String value) {
		this.centerName = value;
	}
	
	private String	centerCode;
	
	/**
	 * 获取 中心代码
	 */
	public String getCenterCode() {
		return this.centerCode;
	}
	
	/**
	 * 设置 中心代码
	 * @param value 属性值
	 */
	public void setCenterCode(String value) {
		this.centerCode = value;
	}
	
	private String	sCode;
	
	/**
	 * 获取 对应中心库位
	 */
	public String getSCode() {
		return this.sCode;
	}
	
	/**
	 * 设置 对应中心库位
	 * @param value 属性值
	 */
	public void setSCode(String value) {
		this.sCode = value;
	}
	
}