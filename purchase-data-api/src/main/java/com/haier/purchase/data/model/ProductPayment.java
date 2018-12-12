package com.haier.purchase.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 *                       
 * @Filename: BaseFactory.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
/**
 *                       
 * @Filename: BaseFactory.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public class ProductPayment implements Serializable {
   private int id;
   private String productCode;//产品组代码
   private String productName;//产品组名称
   private String payMentCode;//付款方代码
   private String payMentName;//付款方名称
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getProductCode() {
	return productCode;
}
public void setProductCode(String productCode) {
	this.productCode = productCode;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getPayMentCode() {
	return payMentCode;
}
public void setPayMentCode(String payMentCode) {
	this.payMentCode = payMentCode;
}
public String getPayMentName() {
	return payMentName;
}
public void setPayMentName(String payMentName) {
	this.payMentName = payMentName;
}
   
   
}
