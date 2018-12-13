package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 菜鸟补货单确认展示信息
 **/
public class ReplenishOrderConfirmDisplayItem implements Serializable{

    private static final long serialVersionUID = 8619322315291497560L;

    private Integer id;
    private Long scItemId; //货品id
    private String scItemCode; //货品编码
    private String scItemName; //货品名称
    private String replNo; //补货单号
    private Integer planReplQty; //计划补货量
    private String deadLine; //最晚入库时间
    private Integer suggestQty; //建议补货量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getScItemId() {
        return scItemId;
    }

    public void setScItemId(Long scItemId) {
        this.scItemId = scItemId;
    }

    public String getScItemCode() {
        return scItemCode;
    }

    public void setScItemCode(String scItemCode) {
        this.scItemCode = scItemCode;
    }

    public String getScItemName() {
        return scItemName;
    }

    public void setScItemName(String scItemName) {
        this.scItemName = scItemName;
    }

    public String getReplNo() {
        return replNo;
    }

    public void setReplNo(String replNo) {
        this.replNo = replNo;
    }

    public Integer getPlanReplQty() {
        return planReplQty;
    }

    public void setPlanReplQty(Integer planReplQty) {
        this.planReplQty = planReplQty;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getSuggestQty() {
        return suggestQty;
    }

    public void setSuggestQty(Integer suggestQty) {
        this.suggestQty = suggestQty;
    }
}
