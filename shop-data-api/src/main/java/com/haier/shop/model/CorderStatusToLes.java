package com.haier.shop.model;

import java.io.Serializable;

public class CorderStatusToLes implements Serializable{
    /**
	 * wukunyang
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer orderproductid;

    private String cordersn;

    private Integer success;

    private Integer count;

    private Integer addtime;

    private Integer successtime;

    private Integer createtype;

    private String corderflag;
    
    private String pushdata;

    private String lastmessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderproductid() {
        return orderproductid;
    }

    public void setOrderproductid(Integer orderproductid) {
        this.orderproductid = orderproductid;
    }

    public String getCordersn() {
        return cordersn;
    }

    public void setCordersn(String cordersn) {
        this.cordersn = cordersn == null ? null : cordersn.trim();
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    public Integer getSuccesstime() {
        return successtime;
    }

    public void setSuccesstime(Integer successtime) {
        this.successtime = successtime;
    }

    public Integer getCreatetype() {
        return createtype;
    }

    public void setCreatetype(Integer createtype) {
        this.createtype = createtype;
    }

    public String getCorderflag() {
        return corderflag;
    }

    public void setCorderflag(String corderflag) {
        this.corderflag = corderflag == null ? null : corderflag.trim();
    }
    
    public String getPushdata() {
        return pushdata;
    }

    public void setPushdata(String pushdata) {
        this.pushdata = pushdata == null ? null : pushdata.trim();
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage == null ? null : lastmessage.trim();
    }
}