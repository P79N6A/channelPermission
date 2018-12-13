package com.haier.eop.data.model;

import java.io.Serializable;

/**
 * 仓库编码实体类
 */
public class StoreCodeMapping implements Serializable {

    private Integer id;                 //id值
    private String storeName;           //仓库名称
    private String haierStoreCode;      //海尔仓库编码
    private String cainiaoStoreCode;    //菜鸟仓库编码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getHaierStoreCode() {
        return haierStoreCode;
    }

    public void setHaierStoreCode(String haierStoreCode) {
        this.haierStoreCode = haierStoreCode;
    }

    public String getCainiaoStoreCode() {
        return cainiaoStoreCode;
    }

    public void setCainiaoStoreCode(String cainiaoStoreCode) {
        this.cainiaoStoreCode = cainiaoStoreCode;
    }
}
