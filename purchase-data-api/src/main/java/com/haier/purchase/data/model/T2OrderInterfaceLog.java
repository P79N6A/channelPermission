package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李超 on 2018/3/7.
 */
public class T2OrderInterfaceLog implements Serializable{

    private static final long serialVersionUID = -25265909664894010L;

    private Integer interfaceId;

    private String interfaceName;

    private String interfaceCategory;

    private Date interfaceDate;

    private String interfaceMessage;

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceCategory() {
        return interfaceCategory;
    }

    public void setInterfaceCategory(String interfaceCategory) {
        this.interfaceCategory = interfaceCategory;
    }

    public Date getInterfaceDate() {
        return interfaceDate;
    }

    public void setInterfaceDate(Date interfaceDate) {
        this.interfaceDate = interfaceDate;
    }

    public String getInterfaceMessage() {
        return interfaceMessage;
    }

    public void setInterfaceMessage(String interfaceMessage) {
        this.interfaceMessage = interfaceMessage;
    }
}
