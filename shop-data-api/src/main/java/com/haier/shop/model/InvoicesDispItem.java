/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发票显示类
 */
public class InvoicesDispItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6160430055195077256L;
    private Integer id;
    private Integer isOld;                                  //是否是从原开票中间表导过来的数据，如果是导过来的此值为1，但不可再传金税开票
    private Integer isReInvoice;                            //是否是重新开票
    private Integer orderProductId;                         //网单ID
    private Integer corderType;                             //网单类型，1：普通网单；2：差异网单；3：专项开票
    private Integer diffId;                                 //差异网单ID
    private String corderSn;                               //网单编号(对应WDH)
    private Integer memberInvoiceId;                        //会员发票信息ID,MemberInvoices表的主键
    private String customerCode;                           //客户编码(对应KHBM)目前不管是普票还是增票都默认传00002001
    private String invoiceTitle;                           //发票抬头(KHMC)
    private String taxPayerNumber;                         //纳税人识别号(KHSH)
    private String registerAddressAndPhone;                //注册地址和电话(KHDZ)
    private String bankNameAndAccount;                     //开户银行(KHKHYHZH)
    private String remark;                                 //备注(BZ)
    private String corderAddTime;                          //网单生成时间(WDRQ)
    private String sku;                                    //物料编码(CPDM)
    private String productName;                            //商品名称(CPMC)
    private String productCateName;                        //商品分类(CPXH)
    private String unit;                                   //计量单位(CPDW)
    private Integer number;                                 //数量(CPSL)取自网单表中的number
    private BigDecimal price;                                  //含税价(HSDJ)取自网单表中的price
    private BigDecimal amount;                                 //含税金额(HSJE)取自网单表中的productAmount
    private BigDecimal nonTaxPrice;                            //不含税单价(BHSDJ)
    private BigDecimal nonTaxAmount;                           //不含税金额(BHSJE)
    private BigDecimal taxAmount;                              //税额(JSJE)
    private BigDecimal taxRate;                                //税率(JSSL)
    private Integer type;                                   //发票类型,1-增票,2-普票(FPLX)
    private Integer isTogether;                             //是否货票同行(HPTX)1-是,2-否
    private Integer state;                                  //发票状态(FPZT)
    private String lessOrderSn;                            //less订单号(WLNO)LES返回的以10和79开票的单号
    private String paymentName;                            //付款方式(SKFS)
    private String scode;                                  //库位编码(KWBM)
    private String orderType;                              //订单类型(DDLX)目前不管普票还是增票暂都默认ZBCC
    private String invoiceNumber;                          //税控号码(FPHM)
    private String billingTime;                            //开票时间(KPRQ)
    private String eaiWriteTime;                           //eai回写商城时间(SKRQ)
    private String drawer;                                 //开票人(KPMAN)
    private String eaiWriteState;                          //开票状态(KPZT)
    private String invalidTime;                            //发票作废时间(ZFRQ)
    private String invalidReason;                          //作废原因
    private String firstPushTime;                          //首次推送开票时间(RRRQ)
    private String lastModifyTime;                         //电商最后更新开票信息时间(GXRQ)
    private String internalSettlement;                     //内部结算单号(LBJSDH)
    private String branchOfficeCode;                       //分公司代码(FGSNO)默认为空
    private String orderLineNumber;                        //订单行号(DDHNO)默认为空
    private String backupFieldA;                           //备用字段1(ADD1)默认为空
    private String backupFieldB;                           //备用字段1(ADD2)默认为空，目前该字段专门当作“订单来源”传给金税
    private BigDecimal integralAmount;                         //积分金额(JFJE)
    private BigDecimal energySavingAmount;                     //节能补贴金额(JLJE)
    private String energySavingRemark;                     //节能补贴备注(JLBZ)
    private Integer statusType;                             //状态类型
    private Integer success;                                //此状态类型下是否推送成功
    private String addTime;                                //写入时间
    private Integer tryNum;                                 //尝试传递的次数
    private Integer electricFlag;                           //电子发票标志，1：电子发票；0：纸质发票
    private String eInvViewUrl;                            //电子发票查看地址
    private String fiscalCode;                             //税控码
    private String writeAddTime;                           //写入队列时间(电子发票用(InvoiceElectricSyncLogs)中的addtime;非电子发票用(InvoiceApiLogs)中的addtime)

    /**
     * @return Returns the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the isOld
     */
    public Integer getIsOld() {
        return isOld;
    }

    /**
     * @param isOld
     * The isOld to set.
     */
    public void setIsOld(Integer isOld) {
        this.isOld = isOld;
    }

    /**
     * @return Returns the isReInvoice
     */
    public Integer getIsReInvoice() {
        return isReInvoice;
    }

    /**
     * @param isReInvoice
     * The isReInvoice to set.
     */
    public void setIsReInvoice(Integer isReInvoice) {
        this.isReInvoice = isReInvoice;
    }

    /**
     * @return Returns the orderProductId
     */
    public Integer getOrderProductId() {
        return orderProductId;
    }

    /**
     * @param orderProductId
     * The orderProductId to set.
     */
    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * @return Returns the corderType
     */
    public Integer getCorderType() {
        return corderType;
    }

    /**
     * @param corderType
     * The corderType to set.
     */
    public void setCorderType(Integer corderType) {
        this.corderType = corderType;
    }

    /**
     * @return Returns the diffId
     */
    public Integer getDiffId() {
        return diffId;
    }

    /**
     * @param diffId
     * The diffId to set.
     */
    public void setDiffId(Integer diffId) {
        this.diffId = diffId;
    }

    /**
     * @return Returns the corderSn
     */
    public String getCorderSn() {
        return corderSn;
    }

    /**
     * @param corderSn
     * The corderSn to set.
     */
    public void setCorderSn(String corderSn) {
        this.corderSn = corderSn;
    }

    /**
     * @return Returns the memberInvoiceId
     */
    public Integer getMemberInvoiceId() {
        return memberInvoiceId;
    }

    /**
     * @param memberInvoiceId
     * The memberInvoiceId to set.
     */
    public void setMemberInvoiceId(Integer memberInvoiceId) {
        this.memberInvoiceId = memberInvoiceId;
    }

    /**
     * @return Returns the customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCode
     * The customerCode to set.
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * @return Returns the invoiceTitle
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * @param invoiceTitle
     * The invoiceTitle to set.
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    /**
     * @return Returns the taxPayerNumber
     */
    public String getTaxPayerNumber() {
        return taxPayerNumber;
    }

    /**
     * @param taxPayerNumber
     * The taxPayerNumber to set.
     */
    public void setTaxPayerNumber(String taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    /**
     * @return Returns the registerAddressAndPhone
     */
    public String getRegisterAddressAndPhone() {
        return registerAddressAndPhone;
    }

    /**
     * @param registerAddressAndPhone
     * The registerAddressAndPhone to set.
     */
    public void setRegisterAddressAndPhone(String registerAddressAndPhone) {
        this.registerAddressAndPhone = registerAddressAndPhone;
    }

    /**
     * @return Returns the bankNameAndAccount
     */
    public String getBankNameAndAccount() {
        return bankNameAndAccount;
    }

    /**
     * @param bankNameAndAccount
     * The bankNameAndAccount to set.
     */
    public void setBankNameAndAccount(String bankNameAndAccount) {
        this.bankNameAndAccount = bankNameAndAccount;
    }

    /**
     * @return Returns the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     * The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Returns the corderAddTime
     */
    public String getCorderAddTime() {
        return corderAddTime;
    }

    /**
     * @param corderAddTime
     * The corderAddTime to set.
     */
    public void setCorderAddTime(String corderAddTime) {
        this.corderAddTime = corderAddTime;
    }

    /**
     * @return Returns the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     * The sku to set.
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return Returns the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     * The productName to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return Returns the productCateName
     */
    public String getProductCateName() {
        return productCateName;
    }

    /**
     * @param productCateName
     * The productCateName to set.
     */
    public void setProductCateName(String productCateName) {
        this.productCateName = productCateName;
    }

    /**
     * @return Returns the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     * The unit to set.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return Returns the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number
     * The number to set.
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return Returns the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     * The price to set.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return Returns the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     * The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the nonTaxPrice
     */
    public BigDecimal getNonTaxPrice() {
        return nonTaxPrice;
    }

    /**
     * @param nonTaxPrice
     * The nonTaxPrice to set.
     */
    public void setNonTaxPrice(BigDecimal nonTaxPrice) {
        this.nonTaxPrice = nonTaxPrice;
    }

    /**
     * @return Returns the nonTaxAmount
     */
    public BigDecimal getNonTaxAmount() {
        return nonTaxAmount;
    }

    /**
     * @param nonTaxAmount
     * The nonTaxAmount to set.
     */
    public void setNonTaxAmount(BigDecimal nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    /**
     * @return Returns the taxAmount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount
     * The taxAmount to set.
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return Returns the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate
     * The taxRate to set.
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return Returns the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     * The type to set.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return Returns the isTogether
     */
    public Integer getIsTogether() {
        return isTogether;
    }

    /**
     * @param isTogether
     * The isTogether to set.
     */
    public void setIsTogether(Integer isTogether) {
        this.isTogether = isTogether;
    }

    /**
     * @return Returns the state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     * The state to set.
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return Returns the lessOrderSn
     */
    public String getLessOrderSn() {
        return lessOrderSn;
    }

    /**
     * @param lessOrderSn
     * The lessOrderSn to set.
     */
    public void setLessOrderSn(String lessOrderSn) {
        this.lessOrderSn = lessOrderSn;
    }

    /**
     * @return Returns the paymentName
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * @param paymentName
     * The paymentName to set.
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * @return Returns the scode
     */
    public String getScode() {
        return scode;
    }

    /**
     * @param scode
     * The scode to set.
     */
    public void setScode(String scode) {
        this.scode = scode;
    }

    /**
     * @return Returns the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType
     * The orderType to set.
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return Returns the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber
     * The invoiceNumber to set.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return Returns the billingTime
     */
    public String getBillingTime() {
        return billingTime;
    }

    /**
     * @param billingTime
     * The billingTime to set.
     */
    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }

    /**
     * @return Returns the eaiWriteTime
     */
    public String getEaiWriteTime() {
        return eaiWriteTime;
    }

    /**
     * @param eaiWriteTime
     * The eaiWriteTime to set.
     */
    public void setEaiWriteTime(String eaiWriteTime) {
        this.eaiWriteTime = eaiWriteTime;
    }

    /**
     * @return Returns the drawer
     */
    public String getDrawer() {
        return drawer;
    }

    /**
     * @param drawer
     * The drawer to set.
     */
    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    /**
     * @return Returns the eaiWriteState
     */
    public String getEaiWriteState() {
        return eaiWriteState;
    }

    /**
     * @param eaiWriteState
     * The eaiWriteState to set.
     */
    public void setEaiWriteState(String eaiWriteState) {
        this.eaiWriteState = eaiWriteState;
    }

    /**
     * @return Returns the invalidTime
     */
    public String getInvalidTime() {
        return invalidTime;
    }

    /**
     * @param invalidTime
     * The invalidTime to set.
     */
    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    /**
     * @return Returns the invalidReason
     */
    public String getInvalidReason() {
        return invalidReason;
    }

    /**
     * @param invalidReason
     * The invalidReason to set.
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    /**
     * @return Returns the firstPushTime
     */
    public String getFirstPushTime() {
        return firstPushTime;
    }

    /**
     * @param firstPushTime
     * The firstPushTime to set.
     */
    public void setFirstPushTime(String firstPushTime) {
        this.firstPushTime = firstPushTime;
    }

    /**
     * @return Returns the lastModifyTime
     */
    public String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * @param lastModifyTime
     * The lastModifyTime to set.
     */
    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * @return Returns the internalSettlement
     */
    public String getInternalSettlement() {
        return internalSettlement;
    }

    /**
     * @param internalSettlement
     * The internalSettlement to set.
     */
    public void setInternalSettlement(String internalSettlement) {
        this.internalSettlement = internalSettlement;
    }

    /**
     * @return Returns the branchOfficeCode
     */
    public String getBranchOfficeCode() {
        return branchOfficeCode;
    }

    /**
     * @param branchOfficeCode
     * The branchOfficeCode to set.
     */
    public void setBranchOfficeCode(String branchOfficeCode) {
        this.branchOfficeCode = branchOfficeCode;
    }

    /**
     * @return Returns the orderLineNumber
     */
    public String getOrderLineNumber() {
        return orderLineNumber;
    }

    /**
     * @param orderLineNumber
     * The orderLineNumber to set.
     */
    public void setOrderLineNumber(String orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }

    /**
     * @return Returns the backupFieldA
     */
    public String getBackupFieldA() {
        return backupFieldA;
    }

    /**
     * @param backupFieldA
     * The backupFieldA to set.
     */
    public void setBackupFieldA(String backupFieldA) {
        this.backupFieldA = backupFieldA;
    }

    /**
     * @return Returns the backupFieldB
     */
    public String getBackupFieldB() {
        return backupFieldB;
    }

    /**
     * @param backupFieldB
     * The backupFieldB to set.
     */
    public void setBackupFieldB(String backupFieldB) {
        this.backupFieldB = backupFieldB;
    }

    /**
     * @return Returns the integralAmount
     */
    public BigDecimal getIntegralAmount() {
        return integralAmount;
    }

    /**
     * @param integralAmount
     * The integralAmount to set.
     */
    public void setIntegralAmount(BigDecimal integralAmount) {
        this.integralAmount = integralAmount;
    }

    /**
     * @return Returns the energySavingAmount
     */
    public BigDecimal getEnergySavingAmount() {
        return energySavingAmount;
    }

    /**
     * @param energySavingAmount
     * The energySavingAmount to set.
     */
    public void setEnergySavingAmount(BigDecimal energySavingAmount) {
        this.energySavingAmount = energySavingAmount;
    }

    /**
     * @return Returns the energySavingRemark
     */
    public String getEnergySavingRemark() {
        return energySavingRemark;
    }

    /**
     * @param energySavingRemark
     * The energySavingRemark to set.
     */
    public void setEnergySavingRemark(String energySavingRemark) {
        this.energySavingRemark = energySavingRemark;
    }

    /**
     * @return Returns the statusType
     */
    public Integer getStatusType() {
        return statusType;
    }

    /**
     * @param statusType
     * The statusType to set.
     */
    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    /**
     * @return Returns the success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * @param success
     * The success to set.
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * @return Returns the addTime
     */
    public String getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     * The addTime to set.
     */
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /**
     * @return Returns the tryNum
     */
    public Integer getTryNum() {
        return tryNum;
    }

    /**
     * @param tryNum
     * The tryNum to set.
     */
    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    /**
     * @return Returns the electricFlag
     */
    public Integer getElectricFlag() {
        return electricFlag;
    }

    /**
     * @param electricFlag
     * The electricFlag to set.
     */
    public void setElectricFlag(Integer electricFlag) {
        this.electricFlag = electricFlag;
    }

    /**
     * @return Returns the eInvViewUrl
     */
    public String geteInvViewUrl() {
        return eInvViewUrl;
    }

    /**
     * @param eInvViewUrl
     * The eInvViewUrl to set.
     */
    public void seteInvViewUrl(String eInvViewUrl) {
        this.eInvViewUrl = eInvViewUrl;
    }

    /**
     * @return Returns the fiscalCode
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * @param fiscalCode
     * The fiscalCode to set.
     */
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    /**
     * @return Returns the writeAddTime
     */
    public String getWriteAddTime() {
        return writeAddTime;
    }

    /**
     * @param writeAddTime
     * The writeAddTime to set.
     */
    public void setWriteAddTime(String writeAddTime) {
        this.writeAddTime = writeAddTime;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
