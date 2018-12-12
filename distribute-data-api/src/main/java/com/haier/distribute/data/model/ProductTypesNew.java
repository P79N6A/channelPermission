package com.haier.distribute.data.model;

import java.io.Serializable;

/*
* 作者:张波
* 2017/12/25
*/
public class ProductTypesNew implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer attrCateId;

    public Integer getAttrCateId() {
        return this.attrCateId;
    }

    public void setAttrCateId(Integer value) {
        this.attrCateId = value;
    }

    private String typeName;

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String value) {
        this.typeName = value;
    }

    private Integer supportCod = 0;

    public Integer getSupportCod() {
        return this.supportCod;
    }

    public void setSupportCod(Integer value) {
        this.supportCod = value;
    }

    private String codExcludeSkus;

    public String getCodExcludeSkus() {
        return this.codExcludeSkus;
    }

    public void setCodExcludeSkus(String value) {
        this.codExcludeSkus = value;
    }
}
