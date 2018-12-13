package com.haier.shop.model;

import java.io.Serializable;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/11 17:44
 */
public class SysMdmVO implements Serializable{
    private String rowId;
    private String materialCode;
    private String materialDescrition;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialDescrition() {
        return materialDescrition;
    }

    public void setMaterialDescrition(String materialDescrition) {
        this.materialDescrition = materialDescrition;
    }
}
