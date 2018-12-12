package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>Payments</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>paymentCode</td><td>{@link String}</td><td>paymentCode</td><td>varchar</td><td>支付方式代码</td></tr>
 *   <tr><td>paymentName</td><td>{@link String}</td><td>paymentName</td><td>varchar</td><td>支付方式名称</td></tr>
 *   <tr><td>paymentDesc</td><td>{@link String}</td><td>paymentDesc</td><td>text</td><td>支付方式描述</td></tr>
 *   <tr><td>paymentConfig</td><td>{@link String}</td><td>paymentConfig</td><td>text</td><td>支付方式配置</td></tr>
 *   <tr><td>sortNum</td><td>{@link Integer}</td><td>sortNum</td><td>tinyint</td><td>排序</td></tr>
 *   <tr><td>enabled</td><td>{@link Integer}</td><td>enabled</td><td>tinyint</td><td>是否启用</td></tr>
 *   <tr><td>onlyBackend</td><td>{@link Integer}</td><td>onlyBackend</td><td>tinyint</td><td>是否仅在后台使用</td></tr>
 * </table>
 * @author rongmai
 */
public class Payments implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 8984653916258436998L;
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
	
	private String	paymentCode;
	
	/**
	 * 获取 支付方式代码
	 */
	public String getPaymentCode() {
		return this.paymentCode;
	}
	
	/**
	 * 设置 支付方式代码
	 * @param value 属性值
	 */
	public void setPaymentCode(String value) {
		this.paymentCode = value;
	}
	
	private String	paymentName;
	
	/**
	 * 获取 支付方式名称
	 */
	public String getPaymentName() {
		return this.paymentName;
	}
	
	/**
	 * 设置 支付方式名称
	 * @param value 属性值
	 */
	public void setPaymentName(String value) {
		this.paymentName = value;
	}
	
	private String	paymentDesc;
	
	/**
	 * 获取 支付方式描述
	 */
	public String getPaymentDesc() {
		return this.paymentDesc;
	}
	
	/**
	 * 设置 支付方式描述
	 * @param value 属性值
	 */
	public void setPaymentDesc(String value) {
		this.paymentDesc = value;
	}
	
	private String	paymentConfig;
	
	/**
	 * 获取 支付方式配置
	 */
	public String getPaymentConfig() {
		return this.paymentConfig;
	}
	
	/**
	 * 设置 支付方式配置
	 * @param value 属性值
	 */
	public void setPaymentConfig(String value) {
		this.paymentConfig = value;
	}
	
	private Integer	sortNum;
	
	/**
	 * 获取 排序
	 */
	public Integer getSortNum() {
		return this.sortNum;
	}
	
	/**
	 * 设置 排序
	 * @param value 属性值
	 */
	public void setSortNum(Integer value) {
		this.sortNum = value;
	}
	
	private Integer	enabled;
	
	/**
	 * 获取 是否启用
	 */
	public Integer getEnabled() {
		return this.enabled;
	}
	
	/**
	 * 设置 是否启用
	 * @param value 属性值
	 */
	public void setEnabled(Integer value) {
		this.enabled = value;
	}
	
	private Integer	onlyBackend	= 0;
	
	/**
	 * 获取 是否仅在后台使用
	 */
	public Integer getOnlyBackend() {
		return this.onlyBackend;
	}
	
	/**
	 * 设置 是否仅在后台使用
	 * @param value 属性值
	 */
	public void setOnlyBackend(Integer value) {
		this.onlyBackend = value;
	}
	
}