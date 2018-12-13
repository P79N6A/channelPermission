package com.haier.shop.model;

public class ReviewErrorData {
    private String id;

    private String errorcontent;

    private String addtime;

    private String adduser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorcontent() {
        return errorcontent;
    }

    public void setErrorcontent(String errorcontent) {
        this.errorcontent = errorcontent == null ? null : errorcontent.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser == null ? null : adduser.trim();
    }
}