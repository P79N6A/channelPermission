package com.haier.shop.model;

import java.io.Serializable;

/**
 * 异常发票信息展示类
 */
public class InvoiceExceptionDispItem implements Serializable {

    private static final long serialVersionUID = 8180062630062886910L;

    private String orderSn;                                //订单号
    private String sourceOrderSn;                          //来源订单号
    private String relationOrderSn;                        //关联订单号
    private String isBackend;                              //是否为后台下单
    private String o2oType;                                //o2oType
    private String stockType;                              //网单库存类型
    private String cOrderSn;                               //网单号
    private String isReceipt;                              //是否需要开具发票
    private String status;                                 //网单状态
    private String cPaymentStatus;                         //网单付款状态
    private String paymentStatus;                          //订单付款状态
    private String paymentCode;                            //订单付款方式
    private String paidAmount;                             //订单付款金额
    private String makeReceiptType;                        //开票类型
    private String isMakeReceipt;                          //开票状态
    private String receiptConsignee;                       //发票收件人
    private String receiptAddress;                         //发票收件地址
    private String receiptZipcode;                         //邮政编码
    private String receiptMobile;                          //收件人电话
    private String addTime;                                //下单时间
    private String orderStatus;                            //订单状态
    private String source;                                 //订单来源
    private String brandName;                              //品牌
    private String cateName;                               //类型
    private String productName;                            //型号
    private String sku;                                    //物料编码
    private String price;                                  //售价
    private String number;                                 //数量
    private String shippingFee;                            //运费
    private String productAmount;                          //总价
    private String remark;                                 //订单备注
    private String username;                               //下单人(会员用户名)
    private String originRegionName;                       //淘宝收货人地址
    private String originAddress;                          //淘宝收货人详细地址
    private String consignee;                              //收货人
    private String mobile;                                 //收货人电话
    private String province;                               //省
    private String city;                                   //市
    private String region;                                 //区县
    private String street;                                 //街道
    private String regionName;                             //地区名称（如：北京 北京 昌平区 兴寿镇）
    private String address;                                //联系地址
    private String zipcode;                                //邮编
    private String shippingMode;                           //订单模式
    private String sCode;                                  //所属库房
    private String tsCode;                                 //转运库房
    private String tsShippingTime;                         //转运时效（小时）
    private String tradeSn;                                //交易流水号
    private String points;                                 //订单使用积分
    private String payTime;                                //付款时间
    private String firstConfirmTime;                       //首次确认时间
    private String firstConfirmPerson;                     //首次确认人
    private String agent;                                  //确认人
    private String confirmTime;                            //确认时间
    private String netPointName;                           //分配网点
    private String hpFinishDate;                           //分配时间
    private String outping;                                //LES出库号
    private String invoiceType;                            //发票类型
    private String invoiceTitle;                           //发票户头
    private String taxPayerNumber;                         //纳税人登记号
    private String registerPhone;                          //电话
    private String registerAddress;                        //地址
    private String bankName;                               //开户行名称
    private String bankCardNumber;                         //开户银行账号
    private String state;                                  //发票审核状态

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public String getRelationOrderSn() {
        return relationOrderSn;
    }

    public void setRelationOrderSn(String relationOrderSn) {
        this.relationOrderSn = relationOrderSn;
    }

    public String getIsBackend() {
        return isBackend;
    }

    public void setIsBackend(String isBackend) {
        this.isBackend = isBackend;
    }

    public String getO2oType() {
        return o2oType;
    }

    public void setO2oType(String o2oType) {
        this.o2oType = o2oType;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getcPaymentStatus() {
        return cPaymentStatus;
    }

    public void setcPaymentStatus(String cPaymentStatus) {
        this.cPaymentStatus = cPaymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getMakeReceiptType() {
        return makeReceiptType;
    }

    public void setMakeReceiptType(String makeReceiptType) {
        this.makeReceiptType = makeReceiptType;
    }

    public String getIsMakeReceipt() {
        return isMakeReceipt;
    }

    public void setIsMakeReceipt(String isMakeReceipt) {
        this.isMakeReceipt = isMakeReceipt;
    }

    public String getReceiptConsignee() {
        return receiptConsignee;
    }

    public void setReceiptConsignee(String receiptConsignee) {
        this.receiptConsignee = receiptConsignee;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getReceiptZipcode() {
        return receiptZipcode;
    }

    public void setReceiptZipcode(String receiptZipcode) {
        this.receiptZipcode = receiptZipcode;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOriginRegionName() {
        return originRegionName;
    }

    public void setOriginRegionName(String originRegionName) {
        this.originRegionName = originRegionName;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public String getTsShippingTime() {
        return tsShippingTime;
    }

    public void setTsShippingTime(String tsShippingTime) {
        this.tsShippingTime = tsShippingTime;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getFirstConfirmTime() {
        return firstConfirmTime;
    }

    public void setFirstConfirmTime(String firstConfirmTime) {
        this.firstConfirmTime = firstConfirmTime;
    }

    public String getFirstConfirmPerson() {
        return firstConfirmPerson;
    }

    public void setFirstConfirmPerson(String firstConfirmPerson) {
        this.firstConfirmPerson = firstConfirmPerson;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getNetPointName() {
        return netPointName;
    }

    public void setNetPointName(String netPointName) {
        this.netPointName = netPointName;
    }

    public String getHpFinishDate() {
        return hpFinishDate;
    }

    public void setHpFinishDate(String hpFinishDate) {
        this.hpFinishDate = hpFinishDate;
    }

    public String getOutping() {
        return outping;
    }

    public void setOutping(String outping) {
        this.outping = outping;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
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

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
