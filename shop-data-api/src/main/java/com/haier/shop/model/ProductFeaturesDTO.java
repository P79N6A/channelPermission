package com.haier.shop.model;

import java.io.Serializable;

/*
*
* 特征图实体类
* */
public class ProductFeaturesDTO implements Serializable{
    public ProductFeaturesDTO() {

    }
    private Integer id;
    private Integer productId;
    private String name;
    private String url;
    private String displayName;
    private String fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getProductId() {

        return productId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFileId() {
        return fileId;
    }
}
