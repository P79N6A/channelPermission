package com.haier.shop.model;

import java.io.Serializable;


/**
 * 
 * @author wangp-c
 * 用户取消订单
 */
public class CancelInputType implements Serializable{

	private static final long serialVersionUID = 5796819151483277392L;

	/**订单号，必填 */
    private String orderno;
    
    /**取消类型：1.出库前取消 2.拦截订单  必填 */
    private String canceltype;
    
    /**取消说明 可选 */
    private String cancelexplain;
    
    /**属性备注 可选 */
    private String attributes;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	public String getCancelexplain() {
		return cancelexplain;
	}

	public void setCancelexplain(String cancelexplain) {
		this.cancelexplain = cancelexplain;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
    
}
