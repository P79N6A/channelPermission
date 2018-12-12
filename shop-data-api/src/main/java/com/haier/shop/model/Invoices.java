package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发票信息表。
 */
public class Invoices implements Serializable {

    /**
     * 网单类型-普通网单
     */
    public static final Integer CORDERTYPE_COMMON_CORDER_TYPE  = 1;
//    /**
//     * 网单类型-差异网单
//     */
    public static final Integer CORDERTYPE_DIFF_CORDER_TYPE    = 2;
//    /**
//     * 网单类型-专项开票
//     */
    public static final Integer CORDERTYPE_SPECIAL_CORDER_TYPE = 3;
    /**
     * 发票类型-电子发票
     */
    public static final Integer ELECTRICINVOICE                = 1;
    /**
     * 发票类型-纸质发票
     */
    public static final Integer NOTELECTRICINVOICE             = 0;
    /**
     * 开票状态 针对eaiWriteState字段-正常
     */
    public static final String NORMAL_KP_STATE                = "";
    /**
     * 开票状态 针对eaiWriteState字段-当月作废，由EAI回写商城，商城这边无权私自修改
     */
    public static final String INVALID_KP_STATE               = "3";
    /**
     * 开票状态 针对eaiWriteState字段-跨月冲红，也是作废发票中的一种
     */
    public static final String MONTH_INVALID_KP_STATE         = "4";

    /**
     * 发票状态 针对state字段-待开票
     */
    public static final Integer WAIT_STATE                     = 0;
    /**
     * 发票状态 针对state字段-开票中,商城初次开票推送给EAI的值也为此值
     */
    public static final Integer IN_STATE                       = 1;
    /**
     * 发票状态 针对state字段-已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
     */
    public static final Integer COMPLETE_STATE                 = 4;
    /**
     * 发票状态 针对state字段-已取消开票
     */
    public static final Integer CANCEL_STATE                   = 5;

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer isOld = 0;

    /**
     * 获取 是否是从原开票中间表导过来的数据，如果是导过来的此值为1，但不可再传金税开票
     */
    public Integer getIsOld() {
        return this.isOld;
    }

    /**
     * 设置 是否是从原开票中间表导过来的数据，如果是导过来的此值为1，但不可再传金税开票
     * @param value 属性值
     */
    public void setIsOld(Integer value) {
        this.isOld = value;
    }

    private Integer isReInvoice = 0;

    /**
     * 获取 是否是重新开票
     */
    public Integer getIsReInvoice() {
        return this.isReInvoice;
    }

    /**
     * 设置 是否是重新开票
     * @param value 属性值
     */
    public void setIsReInvoice(Integer value) {
        this.isReInvoice = value;
    }

    private Integer orderProductId;

    /**
     * 获取 网单ID
     */
    public Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置 网单ID
     * @param value 属性值
     */
    public void setOrderProductId(Integer value) {
        this.orderProductId = value;
    }

    private Integer cOrderType;

    /**
     * 获取 网单类型,1-普通网单,2-差异网单
     */
    public Integer getCOrderType() {
        return this.cOrderType;
    }

    /**
     * 设置 网单类型,1-普通网单,2-差异网单
     * @param value 属性值
     */
    public void setCOrderType(Integer value) {
        this.cOrderType = value;
    }

    private Integer diffId;

    /**
     * 获取 差异网单ID
     */
    public Integer getDiffId() {
        return this.diffId;
    }

    /**
     * 设置 差异网单ID
     * @param value 属性值
     */
    public void setDiffId(Integer value) {
        this.diffId = value;
    }

    private String cOrderSn;

    /**
     * 获取 网单编号(对应WDH)
     */
    public String getCOrderSn() {
        return this.cOrderSn;
    }

    /**
     * 设置 网单编号(对应WDH)
     * @param value 属性值
     */
    public void setCOrderSn(String value) {
        this.cOrderSn = value;
    }

    private Integer memberInvoiceId;

    /**
     * 获取 会员发票信息ID,MemberInvoices表的主键
     */
    public Integer getMemberInvoiceId() {
        return this.memberInvoiceId;
    }

    /**
     * 设置 会员发票信息ID,MemberInvoices表的主键
     * @param value 属性值
     */
    public void setMemberInvoiceId(Integer value) {
        this.memberInvoiceId = value;
    }

    private String customerCode;

    /**
     * 获取 客户编码(对应KHBM)目前不管是普票还是增票都默认传00002001
     */
    public String getCustomerCode() {
        return this.customerCode;
    }

    /**
     * 设置 客户编码(对应KHBM)目前不管是普票还是增票都默认传00002001
     * @param value 属性值
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    private String invoiceTitle;

    /**
     * 获取 发票抬头(KHMC)
     */
    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    /**
     * 设置 发票抬头(KHMC)
     * @param value 属性值
     */
    public void setInvoiceTitle(String value) {
        this.invoiceTitle = value;
    }

    private String taxPayerNumber;

    /**
     * 获取 纳税人识别号(KHSH)
     */
    public String getTaxPayerNumber() {
        return this.taxPayerNumber;
    }

    /**
     * 设置 纳税人识别号(KHSH)
     * @param value 属性值
     */
    public void setTaxPayerNumber(String value) {
        this.taxPayerNumber = value;
    }

    private String registerAddressAndPhone;

    /**
     * 获取 注册地址和电话(KHDZ)
     */
    public String getRegisterAddressAndPhone() {
        return this.registerAddressAndPhone;
    }

    /**
     * 设置 注册地址和电话(KHDZ)
     * @param value 属性值
     */
    public void setRegisterAddressAndPhone(String value) {
        this.registerAddressAndPhone = value;
    }

    private String bankNameAndAccount;

    /**
     * 获取 开户银行(KHKHYHZH)
     */
    public String getBankNameAndAccount() {
        return this.bankNameAndAccount;
    }

    /**
     * 设置 开户银行(KHKHYHZH)
     * @param value 属性值
     */
    public void setBankNameAndAccount(String value) {
        this.bankNameAndAccount = value;
    }

    private String remark;

    /**
     * 获取 备注(BZ)
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注(BZ)
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private String cOrderAddTime;

    /**
     * 获取 网单生成时间(WDRQ)
     */
    public String getCOrderAddTime() {
        return this.cOrderAddTime;
    }

    /**
     * 设置 网单生成时间(WDRQ)
     * @param value 属性值
     */
    public void setCOrderAddTime(String value) {
        this.cOrderAddTime = value;
    }

    private String sku;

    /**
     * 获取 物料编码(CPDM)
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编码(CPDM)
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String productName;

    /**
     * 获取 商品名称(CPMC)
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 商品名称(CPMC)
     * @param value 属性值
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    private String productCateName;

    /**
     * 获取 商品分类(CPXH)
     */
    public String getProductCateName() {
        return this.productCateName;
    }

    /**
     * 设置 商品分类(CPXH)
     * @param value 属性值
     */
    public void setProductCateName(String value) {
        this.productCateName = value;
    }

    private String unit;

    /**
     * 获取 计量单位(CPDW)
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * 设置 计量单位(CPDW)
     * @param value 属性值
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    private Integer number;

    /**
     * 获取 数量(CPSL)取自网单表中的number
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * 设置 数量(CPSL)取自网单表中的number
     * @param value 属性值
     */
    public void setNumber(Integer value) {
        this.number = value;
    }

    private BigDecimal price;

    /**
     * 获取 含税价(HSDJ)取自网单表中的price
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置 含税价(HSDJ)取自网单表中的price
     * @param value 属性值
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    private BigDecimal amount;

    /**
     * 获取 含税金额(HSJE)取自网单表中的productAmount
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 设置 含税金额(HSJE)取自网单表中的productAmount
     * @param value 属性值
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    private BigDecimal nonTaxPrice;

    /**
     * 获取 不含税单价(BHSDJ)
     */
    public BigDecimal getNonTaxPrice() {
        return this.nonTaxPrice;
    }

    /**
     * 设置 不含税单价(BHSDJ)
     * @param value 属性值
     */
    public void setNonTaxPrice(BigDecimal value) {
        this.nonTaxPrice = value;
    }

    private BigDecimal nonTaxAmount;

    /**
     * 获取 不含税金额(BHSJE)
     */
    public BigDecimal getNonTaxAmount() {
        return this.nonTaxAmount;
    }

    /**
     * 设置 不含税金额(BHSJE)
     * @param value 属性值
     */
    public void setNonTaxAmount(BigDecimal value) {
        this.nonTaxAmount = value;
    }

    private BigDecimal taxAmount;

    /**
     * 获取 税额(JSJE)
     */
    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    /**
     * 设置 税额(JSJE)
     * @param value 属性值
     */
    public void setTaxAmount(BigDecimal value) {
        this.taxAmount = value;
    }

    private BigDecimal taxRate;

    /**
     * 获取 税率(JSSL)
     */
    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    /**
     * 设置 税率(JSSL)
     * @param value 属性值
     */
    public void setTaxRate(BigDecimal value) {
        this.taxRate = value;
    }

    private Integer type;

    /**
     * 获取 发票类型,1-增票,2-普票(FPLX)
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 发票类型,1-增票,2-普票(FPLX)
     * @param value 属性值
     */
    public void setType(Integer value) {
        this.type = value;
    }

    private Integer isTogether;

    /**
     * 获取 是否货票同行(HPTX)1-是,2-否
     */
    public Integer getIsTogether() {
        return this.isTogether;
    }

    /**
     * 设置 是否货票同行(HPTX)1-是,2-否
     * @param value 属性值
     */
    public void setIsTogether(Integer value) {
        this.isTogether = value;
    }

    private Integer state;

    /**
     * 获取 发票状态(FPZT)
     * WAIT_STATE        = 0; //待开票
     * IN_STATE          = 1; //开票中,商城初次开票推送给EAI的值也为此值
     * COMPLETE_STATE    = 4; //已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
     * CANCEL_STATE      = 5; //已取消开票
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置 发票状态(FPZT)
     * WAIT_STATE        = 0; //待开票
     * IN_STATE          = 1; //开票中,商城初次开票推送给EAI的值也为此值
     * COMPLETE_STATE    = 4; //已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
     * CANCEL_STATE      = 5; //已取消开票
     * @param value 属性值
     */
    public void setState(Integer value) {
        this.state = value;
    }

    private String lessOrderSn;

    /**
     * 获取 less订单号(WLNO)LES返回的以10和79开票的单号
     */
    public String getLessOrderSn() {
        return this.lessOrderSn;
    }

    /**
     * 设置 less订单号(WLNO)LES返回的以10和79开票的单号
     * @param value 属性值
     */
    public void setLessOrderSn(String value) {
        this.lessOrderSn = value;
    }

    private String paymentName;

    /**
     * 获取 付款方式(SKFS)
     */
    public String getPaymentName() {
        return this.paymentName;
    }

    /**
     * 设置 付款方式(SKFS)
     * @param value 属性值
     */
    public void setPaymentName(String value) {
        this.paymentName = value;
    }

    private String sCode;

    /**
     * 获取 库位编码(KWBM)
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 库位编码(KWBM)
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    private String orderType;

    /**
     * 获取 订单类型(DDLX)目前不管普票还是增票暂都默认ZBCC
     */
    public String getOrderType() {
        return this.orderType;
    }

    /**
     * 设置 订单类型(DDLX)目前不管普票还是增票暂都默认ZBCC
     * @param value 属性值
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    private String invoiceNumber;

    /**
     * 获取 税控号码(FPHM)
     */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    /**
     * 设置 税控号码(FPHM)
     * @param value 属性值
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    private Long billingTime;

    /**
     * 获取 开票时间(KPRQ)
     */
    public Long getBillingTime() {
        return this.billingTime;
    }

    /**
     * 设置 开票时间(KPRQ)
     * @param value 属性值
     */
    public void setBillingTime(Long value) {
        this.billingTime = value;
    }

    private Long eaiWriteTime;

    /**
     * 获取 eai回写商城时间(SKRQ)
     */
    public Long getEaiWriteTime() {
        return this.eaiWriteTime;
    }

    /**
     * 设置 eai回写商城时间(SKRQ)
     * @param value 属性值
     */
    public void setEaiWriteTime(Long value) {
        this.eaiWriteTime = value;
    }

    private String drawer;

    /**
     * 获取 开票人(KPMAN)
     */
    public String getDrawer() {
        return this.drawer;
    }

    /**
     * 设置 开票人(KPMAN)
     * @param value 属性值
     */
    public void setDrawer(String value) {
        this.drawer = value;
    }

    private String eaiWriteState;

    /**
     * 获取 开票状态(KPZT)
     */
    public String getEaiWriteState() {
        return this.eaiWriteState;
    }

    /**
     * 设置 开票状态(KPZT)
     * @param value 属性值
     */
    public void setEaiWriteState(String value) {
        this.eaiWriteState = value;
    }

    private Long invalidTime;

    /**
     * 获取 发票作废时间(ZFRQ)
     */
    public Long getInvalidTime() {
        return this.invalidTime;
    }

    /**
     * 设置 发票作废时间(ZFRQ)
     * @param value 属性值
     */
    public void setInvalidTime(Long value) {
        this.invalidTime = value;
    }

    private Integer firstPushTime;

    /**
     * 获取 首次推送开票时间(RRRQ)
     */
    public Integer getFirstPushTime() {
        return this.firstPushTime;
    }

    /**
     * 设置 首次推送开票时间(RRRQ)
     * @param value 属性值
     */
    public void setFirstPushTime(Integer value) {
        this.firstPushTime = value;
    }

    private Integer lastModifyTime;

    /**
     * 获取 电商最后更新开票信息时间(GXRQ)
     */
    public Integer getLastModifyTime() {
        return this.lastModifyTime;
    }

    /**
     * 设置 电商最后更新开票信息时间(GXRQ)
     * @param value 属性值
     */
    public void setLastModifyTime(Integer value) {
        this.lastModifyTime = value;
    }

    private String internalSettlement;

    /**
     * 获取 内部结算单号(LBJSDH)
     */
    public String getInternalSettlement() {
        return this.internalSettlement;
    }

    /**
     * 设置 内部结算单号(LBJSDH)
     * @param value 属性值
     */
    public void setInternalSettlement(String value) {
        this.internalSettlement = value;
    }

    private String branchOfficeCode;

    /**
     * 获取 分公司代码(FGSNO)默认为空
     */
    public String getBranchOfficeCode() {
        return this.branchOfficeCode;
    }

    /**
     * 设置 分公司代码(FGSNO)默认为空
     * @param value 属性值
     */
    public void setBranchOfficeCode(String value) {
        this.branchOfficeCode = value;
    }

    private String orderLineNumber;

    /**
     * 获取 订单行号(DDHNO)默认为空
     */
    public String getOrderLineNumber() {
        return this.orderLineNumber;
    }

    /**
     * 设置 订单行号(DDHNO)默认为空
     * @param value 属性值
     */
    public void setOrderLineNumber(String value) {
        this.orderLineNumber = value;
    }

    private String backupFieldA;

    /**
     * 获取 备用字段1(ADD1)默认为空
     */
    public String getBackupFieldA() {
        return this.backupFieldA;
    }

    /**
     * 设置 备用字段1(ADD1)默认为空
     * @param value 属性值
     */
    public void setBackupFieldA(String value) {
        this.backupFieldA = value;
    }

    private String backupFieldB;

    /**
     * 获取 备用字段1(ADD2)默认为空
     */
    public String getBackupFieldB() {
        return this.backupFieldB;
    }

    /**
     * 设置 备用字段1(ADD2)默认为空
     * @param value 属性值
     */
    public void setBackupFieldB(String value) {
        this.backupFieldB = value;
    }

    private BigDecimal integralAmount;

    /**
     * 获取 积分金额(JFJE)
     */
    public BigDecimal getIntegralAmount() {
        return this.integralAmount;
    }

    /**
     * 设置 积分金额(JFJE)
     * @param value 属性值
     */
    public void setIntegralAmount(BigDecimal value) {
        this.integralAmount = value;
    }

    private BigDecimal energySavingAmount;

    /**
     * 获取 节能补贴金额(JLJE)
     */
    public BigDecimal getEnergySavingAmount() {
        return this.energySavingAmount;
    }

    /**
     * 设置 节能补贴金额(JLJE)
     * @param value 属性值
     */
    public void setEnergySavingAmount(BigDecimal value) {
        this.energySavingAmount = value;
    }

    private String energySavingRemark;

    /**
     * 获取 节能补贴备注(JLBZ)
     */
    public String getEnergySavingRemark() {
        return this.energySavingRemark;
    }

    /**
     * 设置 节能补贴备注(JLBZ)
     * @param value 属性值
     */
    public void setEnergySavingRemark(String value) {
        this.energySavingRemark = value;
    }

    //1:首次推送开票 2:修改发票信息 3:取消开票 4:作废发票 5:商城接收EAI的推送的发票信息
    private Integer statusType;

    /**
     * 获取 状态类型
     */
    public Integer getStatusType() {
        return this.statusType;
    }

    /**
     * 设置 状态类型
     * @param value 属性值
     */
    public void setStatusType(Integer value) {
        this.statusType = value;
    }

    private Integer success;

    /**
     * 获取 此状态类型下是否推送成功
     */
    public Integer getSuccess() {
        return this.success;
    }

    /**
     * 设置 此状态类型下是否推送成功
     * @param value 属性值
     */
    public void setSuccess(Integer value) {
        this.success = value;
    }

    private Long addTime;

    /**
     * 获取 写入时间
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 写入时间
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer tryNum;

    /**
     * 获取 尝试传递的次数
     */
    public Integer getTryNum() {
        return this.tryNum;
    }

    /**
     * 设置 尝试传递的次数
     * @param value 属性值
     */
    public void setTryNum(Integer value) {
        this.tryNum = value;
    }

    /**
     * 电子发票标志
     * 1：电子发票；
     * 0：纸质发票
     */
    private Integer electricFlag = 0;

    public Integer getElectricFlag() {
        return electricFlag;
    }

    public void setElectricFlag(Integer electricFlag) {
        this.electricFlag = electricFlag;
    }

    /**
     * 作废原因
     * @param value 属性值
     */
    private String invalidReason;

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    /**
     * 电子发票查看地址
     * @param value 属性值
     */
    private String eInvViewUrl;

    public String getEInvViewUrl() {
        return eInvViewUrl;
    }

    public void setEInvViewUrl(String eInvViewUrl) {
        this.eInvViewUrl = eInvViewUrl;
    }

    private String fiscalCode;

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    /**
     * 发票解决升级：校验码（新增返回字段信息，为20位）
     */
    private String checkCode;

    /**
     * 获取 发票校验码（新增返回字段信息，为20位）
     * @return
     */
    public String getCheckCode() {
        return checkCode;
    }

    /**
     * 设置 发票校验码（新增返回字段信息，为20位）
     * @param checkCode
     */
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

}