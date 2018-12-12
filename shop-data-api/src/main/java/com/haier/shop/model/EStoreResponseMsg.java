package com.haier.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询EStore库存实体                    
 * @Filename: EStoreStock.java
 * @Version: 1.0
 */
public class EStoreResponseMsg {

    private String            errorMessage;
    private List<EStoreStock> items = new ArrayList<EStoreStock>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<EStoreStock> getItems() {
        return items;
    }

    public void setItems(List<EStoreStock> items) {
        this.items = items;
    }

}
