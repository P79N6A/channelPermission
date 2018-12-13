package com.haier.shop.model;

import java.io.Serializable;

public class ReviewDataDictionaryEntity implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -178060471472515946L;

    private Integer           id;                                      //

    private String            valueSetId;                              //类别

    private String            valueSetMeaning;                         //对应中文名称

    private String            value;                                   //常量代码

    private String            valueMeaning;                            //常量值-中文

    private String            parentValue;                             //父列表代码

    /**
     * @return Returns the parentValue
     */
    public String getParentValue() {
        return parentValue;
    }

    /**
     * @param parentValue
     * The parentValue to set.
     */
    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    private Integer orderFlag;

    private Short   deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueSetId() {
        return valueSetId;
    }

    public void setValueSetId(String valueSetId) {
        this.valueSetId = valueSetId == null ? null : valueSetId.trim();
    }

    public String getValueSetMeaning() {
        return valueSetMeaning;
    }

    public void setValueSetMeaning(String valueSetMeaning) {
        this.valueSetMeaning = valueSetMeaning == null ? null : valueSetMeaning.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getValueMeaning() {
        return valueMeaning;
    }

    public void setValueMeaning(String valueMeaning) {
        this.valueMeaning = valueMeaning == null ? null : valueMeaning.trim();
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "ReviewDataDictionaryEntity [id=" + id + ", valueSetId=" + valueSetId
               + ", valueSetMeaning=" + valueSetMeaning + ", value=" + value + ", valueMeaning="
               + valueMeaning + ", parentValue=" + parentValue + ", orderFlag=" + orderFlag
               + ", deleteFlag=" + deleteFlag + "]";
    }

}