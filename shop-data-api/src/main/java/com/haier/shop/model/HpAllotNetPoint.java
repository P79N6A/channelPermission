package com.haier.shop.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "ORDERNUM")
@XmlAccessorType(XmlAccessType.FIELD)
public class HpAllotNetPoint  implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long   serialVersionUID = 5383453893599440339L;

    @XmlElement(name = "ITEM")
    private List<AllotNetPoint> item;

    public List<AllotNetPoint> getItem() {
        return item;
    }

    public void setItem(List<AllotNetPoint> item) {
        this.item = item;
    }
}
