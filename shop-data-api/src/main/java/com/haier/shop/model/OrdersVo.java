package com.haier.shop.model;

import java.util.List;

/**
 * 订单拓展实体类
 * 吴坤洋 
 * 2017-11-29
 * @author wukunyang
 *
 */
public class OrdersVo extends Orders{
	
	private String sCode;//库位编码
	private String type;//发票类型,1-增票,2-普票(FPLX)
	private String invoiceTitle;//发票抬头(KHMC)
	private String taxPayerNumber;//纳税人识别号(KHSH)
	private String registerAddressAndPhone;//注册地址和电话(KHDZ)
	private String bankNameAndAccount;//开户银行(KHKHYHZH)
	private String remark;//备注
	private String sku;//物料编码(CPDM)
	private String productName;//商品名称：可能是商品名称加颜色规格，也可能是套装名称
	private String productTitle;//商品标题，商品在前台展示时显示在名称后面的描述信息
	private String number;//数量
	private String price;//商品单价
	private String payTimeStr;//获取在线付款时间 字符串格式
	private String isReceipt;//是否需要发票
	private String orderYwType;
	private String netPointCode;//网点86码
	private Invoices invocie;//发票表
	private List<OrderProductsVo> productsVo;//网单表
	
	private String industrys;//所属产业
	private String orderUserName;//下单人（用户名
	
	private String userEmail;//Email

	private String stockType;

	private String tbOrderSn;
	
public List<OrderProductsVo> getProductsVo() {
		return productsVo;
	}
	public void setProductsVo(List<OrderProductsVo> productsVo) {
		this.productsVo = productsVo;
	}
	//	public OrderProductsVo getProductsVo() {
//		return productsVo;
//	}
//	public void setProductsVo(OrderProductsVo productsVo) {
//		this.productsVo = productsVo;
//	}
	public String getIndustrys() {
		return industrys;
	}
	public void setIndustrys(String industrys) {
		this.industrys = industrys;
	}
	public String getOrderUserName() {
		return orderUserName;
	}
	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Invoices getInvocie() {
		return invocie;
	}
	public void setInvocie(Invoices invocie) {
		this.invocie = invocie;
	}
	public String getNetPointCode() {
		return netPointCode;
	}
	public void setNetPointCode(String netPointCode) {
		this.netPointCode = netPointCode;
	}
	public String getOrderYwType() {
		return orderYwType;
	}
	public void setOrderYwType(String orderYwType) {
		this.orderYwType = orderYwType;
	}
	public String getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
	}
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
	public String getsCode() {
		return sCode;
	}
	public void setsCode(String sCode) {
		this.sCode = sCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getTaxPayerNumber() {
		return taxPayerNumber;
	}
	public void setTaxPayerNumber(String taxPayerNumber) {
		this.taxPayerNumber = taxPayerNumber;
	}
	public String getRegisterAddressAndPhone() {
		return registerAddressAndPhone;
	}
	public void setRegisterAddressAndPhone(String registerAddressAndPhone) {
		this.registerAddressAndPhone = registerAddressAndPhone;
	}
	public String getBankNameAndAccount() {
		return bankNameAndAccount;
	}
	public void setBankNameAndAccount(String bankNameAndAccount) {
		this.bankNameAndAccount = bankNameAndAccount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

    public String getTbOrderSn() {
        return tbOrderSn;
    }

    public void setTbOrderSn(String tbOrderSn) {
        this.tbOrderSn = tbOrderSn;
    }
}
