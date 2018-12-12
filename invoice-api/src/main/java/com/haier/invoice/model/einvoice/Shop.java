package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * 店铺
 *                       
 * @Filename: Shop.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shop")
public class Shop implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6178256088419002566L;
    /**
     * 店铺名称（纳税人在电商平台销售货物对应的店铺名称）
     */
    @XmlAttribute
    String name;
    /**
     * 纳税人在电商平台上的店铺 ID
     */
    @XmlAttribute
    String shopCode;
    /**
     * 店铺网址
     */
    @XmlAttribute
    String website;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
