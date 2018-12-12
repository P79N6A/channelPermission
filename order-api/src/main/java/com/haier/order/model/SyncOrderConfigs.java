/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.order.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>SyncOrderConfigs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>shopName</td><td>{@link String}</td><td>shopName</td><td>varchar</td><td>网店名称</td></tr>
 *   <tr><td>configType</td><td>{@link String}</td><td>configType</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>configValue</td><td>{@link String}</td><td>configValue</td><td>text</td><td>&nbsp;</td></tr>
 *   <tr><td>autoSync</td><td>{@link Integer}</td><td>autoSync</td><td>tinyint</td><td>是否自动同步</td></tr>
 *   <tr><td>SyncCount</td><td>{@link Integer}</td><td>autoSync</td><td>tinyint</td><td>每次job执行线程数</td></tr>
 *   <tr><td>limitcount</td><td>{@link Integer}</td><td>autoSync</td><td>tinyint</td><td>每次job线程执行个数</td></tr>
 *   <tr><td>customerid</td><td>{@link String}</td><td>customerid</td><td>tinyint</td><td>菜鸟给的货主id</td></tr>
 * </table>
 * @author rongmai
 */
public class SyncOrderConfigs implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -3823584022054334634L;
	
	private Integer id;
	
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
	
	private String shopName;
	
	/**
	 * 获取 网店名称
	 */
	public String getShopName() {
		return this.shopName;
	}
	
	/**
	 * 设置 网店名称
	 * @param value 属性值
	 */
	public void setShopName(String value) {
		this.shopName = value;
	}
	
	private String configType;
	
	public String getConfigType() {
		return this.configType;
	}
	
	public void setConfigType(String value) {
		this.configType = value;
	}
	
	private String configValue;
	
	public String getConfigValue() {
		return this.configValue;
	}
	
	public void setConfigValue(String value) {
		this.configValue = value;
	}
	
	private Integer autoSync	= 0;
	
	/**
	 * 获取 是否自动同步
	 */
	public Integer getAutoSync() {
		return this.autoSync;
	}
	
	/**
	 * 设置 是否自动同步
	 * @param value 属性值
	 */
	public void setAutoSync(Integer value) {
		this.autoSync = value;
	}
	
	private Integer limitcount;

    public Integer getLimitcount() {
        return limitcount;
    }

    public void setLimitcount(Integer limitcount) {
        this.limitcount = limitcount;
    }

	private Integer syncCount;
	
	public Integer getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(Integer syncCount) {
		this.syncCount = syncCount;
	}

    private String customerid;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
}