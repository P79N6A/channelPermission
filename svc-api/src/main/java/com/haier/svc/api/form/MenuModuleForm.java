package com.haier.svc.api.form;

import java.util.List;

public class MenuModuleForm {
    private Integer mmid;

    public Integer getMmid() {
        return this.mmid;
    }

    public void setMmid(Integer value) {
        this.mmid = value;
    }

    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    private List<MenuItemForm> items;

    public List<MenuItemForm> getItems() {
        return this.items;
    }

    public void setItems(List<MenuItemForm> value) {
        this.items = value;
    }
}