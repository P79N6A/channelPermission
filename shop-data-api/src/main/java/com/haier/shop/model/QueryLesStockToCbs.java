package com.haier.shop.model;

import java.io.Serializable;

/**
 * 查询LES库存数---CBS库存对账使用
 * @author wangp-c
 *
 */
public class QueryLesStockToCbs implements Serializable{

	private static final long serialVersionUID = -5659845419615862795L;

	/**
	 * 物料编码
	 */
	private String sku;
	
	/**
	 * 库位编码
	 */
	private String secCode;
	
	/**
	 * 查询标识：输入条件：FLAG = 1 返回表COLLECTOR；FLAG = 2 返回表LTMX；---财务SAP库存对账使用
         FLAG = 3返回表CBS_MX；FLAG = 4返回表CBS_KC；---CBS库存对账使用
	 */
	private String flag;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
