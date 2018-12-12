package com.haier.svc.api.form;

public class MenuItemForm {
    private Integer miid;

    public Integer getMiid() {
        return this.miid;
    }

    public void setMiid(Integer value) {
        this.miid = value;
    }

    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    private String data;

    public String getData() {
        return this.data;
    }

    public void setData(String value) {
        this.data = value;
    }
}