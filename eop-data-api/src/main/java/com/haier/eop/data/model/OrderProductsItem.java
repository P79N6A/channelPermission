package com.haier.eop.data.model;

import java.io.Serializable;

/**
 * 订单子订单信息（保存第三方订单子订单号）
 * @FileName:OrderProductsItem.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2015年4月3日 下午5:09:54
 */
public class OrderProductsItem implements Serializable {
    private static final long   serialVersionUID    = -8582599429262817975L;

	private Integer id;

    private Long orderId;  //订单创建ID
    
    private Long orderproductsId; //网单创建ID
    
    private String sourceOrderLineNum;  //外部订单行编号
    
    private String sourceProductCode;  //外部订单商品编码
    
    private String sourceOrderSn;  //外部订单号
    
    private String source;   //订单来源

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderproductsId() {
		return orderproductsId;
	}

	public void setOrderproductsId(Long orderproductsId) {
		this.orderproductsId = orderproductsId;
	}

	public String getSourceOrderLineNum() {
		return sourceOrderLineNum;
	}

	public void setSourceOrderLineNum(String sourceOrderLineNum) {
		this.sourceOrderLineNum = sourceOrderLineNum;
	}

	public String getSourceProductCode() {
		return sourceProductCode;
	}

	public void setSourceProductCode(String sourceProductCode) {
		this.sourceProductCode = sourceProductCode;
	}

	public String getSourceOrderSn() {
		return sourceOrderSn;
	}

	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	} 
    
    
	
}