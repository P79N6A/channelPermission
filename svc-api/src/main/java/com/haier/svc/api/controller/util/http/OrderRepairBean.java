package com.haier.svc.api.controller.util.http;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class OrderRepairBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public OrderRepairBean() {
        // TODO Auto-generated constructor stub
    }

    private List<ItemBean> item;

    @XmlElement(name = "item")
    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

}