package com.haier.shop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvTransferLineSales implements Serializable {

    private static final long serialVersionUID = -5867036879992176869L;

    private Integer id;
    private String lineId;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    private String lineNum;
    private String soLineNum;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date createTime;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getSoLineNum() {
        return soLineNum;
    }

    public void setSoLineNum(String soLineNum) {
        this.soLineNum = soLineNum;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSecTo() {
        return secTo;
    }

    public void setSecTo(String secTo) {
        this.secTo = secTo;
    }

    public Integer getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(Integer transferQty) {
        this.transferQty = transferQty;
    }

    public BigDecimal getSalesAmounts() {
        return salesAmounts;
    }

    public void setSalesAmounts(BigDecimal salesAmounts) {
        this.salesAmounts = salesAmounts;
    }

    private String itemCode;
    private String secTo;
    private Integer transferQty;
    private BigDecimal salesAmounts;
    //是否生成invoice 0：待开票；1：开票中；2：开票成功
    private int invoiceState;
    //销售出库推送sap是否插入队列 0：待推送；1：推送中；2：推送成功
    private int saleOutState;

    public int getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(int invoiceState) {
        this.invoiceState = invoiceState;
    }

    public int getSaleOutState() {
        return saleOutState;
    }

    public void setSaleOutState(int saleOutState) {
        this.saleOutState = saleOutState;
    }
}

