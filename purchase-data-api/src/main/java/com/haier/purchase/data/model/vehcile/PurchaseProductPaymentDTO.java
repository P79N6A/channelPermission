package com.haier.purchase.data.model.vehcile;


public class PurchaseProductPaymentDTO extends BaseDTO {

	private static final long serialVersionUID = -6738033665562970557L;
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
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	private String productCode; //
    private String productName; //
    private String paymentCode; //
    private String paymentName; //

}


