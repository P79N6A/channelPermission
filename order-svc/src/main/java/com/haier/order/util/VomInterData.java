package com.haier.order.util;

import java.io.Serializable;

public class VomInterData implements Serializable {
    private String notifyid;  // 消息id
    private String notifytime; // 消息时间
    private String source;    // 来源，根据系统区域
    private String butype;    // 通知类型，接口方法名
    private String type;      // 报文格式:Json或xml
    private String sign;      // 签名
    private String content;   // 报文内容

    public String getNotifyid() {
        return notifyid;
    }

    public void setNotifyid(String notifyid) {
        this.notifyid = notifyid;
    }

    public String getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(String notifytime) {
        this.notifytime = notifytime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getButype() {
        return butype;
    }

    public void setButype(String butype) {
        this.butype = butype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
