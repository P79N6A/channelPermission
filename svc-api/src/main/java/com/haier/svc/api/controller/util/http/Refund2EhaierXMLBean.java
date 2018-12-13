package com.haier.svc.api.controller.util.http;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "root")
public class Refund2EhaierXMLBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public Refund2EhaierXMLBean() {

    }

    public Refund2EhaierXMLBean(ItemBean item) {
        List<ItemBean> _list = new ArrayList<ItemBean>();
        _list.add(item);
        OrderRepairBean _bean = new OrderRepairBean();
        _bean.setItem(_list);
        List<OrderRepairBean> _list2 = new ArrayList<OrderRepairBean>();
        _list2.add(_bean);
        this.setOrderRepair(_list2);
    }

    public Refund2EhaierXMLBean(List<ItemBean> itemList) {
        OrderRepairBean _bean = new OrderRepairBean();
        _bean.setItem(itemList);
        List<OrderRepairBean> _list2 = new ArrayList<OrderRepairBean>();
        _list2.add(_bean);
        this.setOrderRepair(_list2);
    }

    private List<OrderRepairBean> orderRepair;

    @XmlElement(name = "orderRepair")
    public List<OrderRepairBean> getOrderRepair() {
        return orderRepair;
    }

    public void setOrderRepair(List<OrderRepairBean> orderRepair) {
        this.orderRepair = orderRepair;
    }

}