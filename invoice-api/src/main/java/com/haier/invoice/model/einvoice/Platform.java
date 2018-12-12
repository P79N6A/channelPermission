package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * 电商平台
 *                       
 * @Filename: Platform.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "platform", propOrder = { "code", "name", "url" })
public class Platform implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1498115534857171752L;
    /**
     * 销货平台编号（开票对接平台）
     */
    @XmlAttribute
    String code;
    /**
     * 平台名称
     */
    @XmlAttribute
    String name;
    /**
     * 平台网址
     */
    @XmlAttribute
    String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
