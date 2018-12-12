package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 发票
 *                       
 * @Filename: Invoice.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoice")
//@XmlType(name = "invoice", propOrder = { "code", "customer", "customerCode", "downloadUrl",
//                                        "drawer", "fiscalCode", "generateTime", "noTaxAmount",
//                                        "status", "taxAmount", "totalAmount", "viewUrl", "items",
//                                        "remarks", "order", "platform", "taxer", "shop" })
public class Invoice implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1185456983921129529L;
    /**
     * 20 位发票号码，前 12 位表示发票代码，后 8 为表示发票号码
     */
    @XmlAttribute
    String code;
    /**
     * 购货方
     */
    @XmlAttribute
    String customer;
    /**
     * 购货方企业纳税人识别号
     */
    @XmlAttribute
    String customerCode;
    /**
     * 发票详细信息的 XML 文件，纳税人可通过下载此 xml 文件获取发票的所有信息
     */
    @XmlAttribute
    String downloadUrl;
    /**
     * 开票员
     */
    @XmlAttribute
    String drawer;
    /**
     * 税控码
     */
    @XmlAttribute
    String fiscalCode;
    /**
     * 发票生成时间（格式为： yyyy-MM-dd HH:mm）
     */
    @XmlAttribute
    String generateTime;
    /**
     * 不含税总额
     */
    @XmlAttribute
    String noTaxAmount;
    /**
     * 发票状态 1 正常；2 作废；3 红冲；4 被红冲
     */
    @XmlAttribute
    String status;
    /**
     * 税金（按照商品税率应缴的税金）
     */
    @XmlAttribute
    String taxAmount;
    /**
     * 含税总额，发票票面展示金额
     */
    @XmlAttribute
    String totalAmount;

    /**
     * 发票查看地址
     */
    @XmlAttribute
    String viewUrl;

    /**
     * 作废红冲原因
     */
    @XmlAttribute
    String validReason;

    /**
     * 作废红冲时间（格式为： yyyy-MM-dd HH:mm）
     */
    @XmlAttribute
    String validTime;

    /**
     * 关联发票编号（如果 status=3，表示该发票为红冲发票，
     * 则对应的关联发票号为被红冲的发票，如果 status=4，则关联的为红冲的发票
     */
    @XmlAttribute
    String relatedCode;

    /**
     * 货物清单
     */
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item", required = true)
    List<Item> items;
    /**
     * 备注列表
     */
    @XmlElementWrapper(name = "remarks")
    @XmlElement(name = "remark", required = true)
    List<Remark> remarks;
    /**
     * 原始订单
     */
    @XmlElement(name = "order", required = true)
    Order                     order;
    /**
     * 电商平台
     */
    @XmlElement(name = "platform", required = true)
    Platform                  platform;
    /**
     * 纳税人
     */
    @XmlElement(name = "taxer", required = true)
    Taxer                     taxer;
    /**
     * 店铺
     */
    @XmlElement(name = "shop", required = true)
    Shop                      shop;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public String getNoTaxAmount() {
        return noTaxAmount;
    }

    public void setNoTaxAmount(String noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getValidReason() {
        return validReason;
    }

    public void setValidReason(String validReason) {
        this.validReason = validReason;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getRelatedCode() {
        return relatedCode;
    }

    public void setRelatedCode(String relatedCode) {
        this.relatedCode = relatedCode;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Taxer getTaxer() {
        return taxer;
    }

    public void setTaxer(Taxer taxer) {
        this.taxer = taxer;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
