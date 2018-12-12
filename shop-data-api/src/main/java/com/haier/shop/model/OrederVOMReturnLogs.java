package com.haier.shop.model;

import java.util.Date;

/**
 * 接收VOM主动推送过来的原始数据
 * @author wukunyang
 *
 */
public class OrederVOMReturnLogs implements java.io.Serializable{
    /***/
    private Integer id;

    private String outCode;

    private Date notifyTime;

    private String buType;

    private String source;

    private String type;

    private String sign;

    private int addTime;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode == null ? null : outCode.trim();
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getBuType() {
        return buType;
    }

    public void setBuType(String buType) {
        this.buType = buType == null ? null : buType.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }


    public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}