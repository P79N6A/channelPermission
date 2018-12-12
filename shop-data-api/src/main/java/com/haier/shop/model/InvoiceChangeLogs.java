package com.haier.shop.model;

import java.io.Serializable;

public class InvoiceChangeLogs implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4622630103485831138L;
    /**
     *Comment for <code>serialVersionUID</code>
     */

    private Integer           id;
    private Integer           invoiceId;                              // 发票ID
    private String            remark;                                 // 备注信息
    private Long              addTime;                                // 修改时间
    private String            operator;                               // 修改人

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
     * @return Returns the invoiceId
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId
     * The invoiceId to set.
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
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
     * @return Returns the addTime
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     * The addTime to set.
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**
     * @return Returns the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     * The operator to set.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}