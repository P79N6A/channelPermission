package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * 响应头部信息
 *                       
 * @Filename: Header.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "header")
public class Header implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3019379481534880469L;
    /**
     * 电商平台身份标识
     */
    @XmlAttribute
    String platformCode;
    /**
     * 响应发送时间
     */
    @XmlAttribute
    String postTime;
    /**
     * 会话 ID
     */
    @XmlAttribute
    String sessionID;
    /**
     * 版本，1.1
     */
    @XmlAttribute
    String version;

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
