package com.haier.shop.model;

import java.io.Serializable;

import java.util.Date;

/***
 * 品类表
 */
public class Producttypes implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private Integer id;

    private Integer siteId;

    private Integer attrCateId;

    private String typeName;

    private Integer supportCod;

    private Date modified;

    private String codExcludeSkus;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getAttrCateId() {
        return attrCateId;
    }

    public void setAttrCateId(Integer attrCateId) {
        this.attrCateId = attrCateId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSupportCod() {
        return supportCod;
    }

    public void setSupportCod(Integer supportCod) {
        this.supportCod = supportCod;
    }

    public String getCodExcludeSkus() {
        return codExcludeSkus;
    }

    public void setCodExcludeSkus(String codExcludeSkus) {
        this.codExcludeSkus = codExcludeSkus;
    }

}