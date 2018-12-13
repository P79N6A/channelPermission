package com.haier.svc.api.controller.util.http.suning;

public class ConsumerInvoice {
    private String invoiceHead;         //发票抬头
    private String needInvoiceFlag;     //是否需要发票，Y-是；N-否；
    private String invoiceType;         //发票类型，01-增值税发票； 02-普通发票； 03-纸质电子发票； 04-电子发票； 05-不需发票；
    private String taxPayerNo;          //税纳税人识别号
    private String registerAddress;     //注册地址
    private String registerPhoneNum;    //注册电话
    private String accountBank;         //开户行
    private String accountNumber;       //开户账号
    private String invoiceConent;       //发票内容
    private String taxPayerName;        //增值税发票收件人姓名
    private String taxPayerAddress;     //增值税发票收件人地址
    private String taxPayerPhoneNum;    //增值税发票收件人电话
    private String taxPayerMobileNum;   //增值税发票收件人手机

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getNeedInvoiceFlag() {
        return needInvoiceFlag;
    }

    public void setNeedInvoiceFlag(String needInvoiceFlag) {
        this.needInvoiceFlag = needInvoiceFlag;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getTaxPayerNo() {
        return taxPayerNo;
    }

    public void setTaxPayerNo(String taxPayerNo) {
        this.taxPayerNo = taxPayerNo;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterPhoneNum() {
        return registerPhoneNum;
    }

    public void setRegisterPhoneNum(String registerPhoneNum) {
        this.registerPhoneNum = registerPhoneNum;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getInvoiceConent() {
        return invoiceConent;
    }

    public void setInvoiceConent(String invoiceConent) {
        this.invoiceConent = invoiceConent;
    }

    public String getTaxPayerName() {
        return taxPayerName;
    }

    public void setTaxPayerName(String taxPayerName) {
        this.taxPayerName = taxPayerName;
    }

    public String getTaxPayerAddress() {
        return taxPayerAddress;
    }

    public void setTaxPayerAddress(String taxPayerAddress) {
        this.taxPayerAddress = taxPayerAddress;
    }

    public String getTaxPayerPhoneNum() {
        return taxPayerPhoneNum;
    }

    public void setTaxPayerPhoneNum(String taxPayerPhoneNum) {
        this.taxPayerPhoneNum = taxPayerPhoneNum;
    }

    public String getTaxPayerMobileNum() {
        return taxPayerMobileNum;
    }

    public void setTaxPayerMobileNum(String taxPayerMobileNum) {
        this.taxPayerMobileNum = taxPayerMobileNum;
    }
}
