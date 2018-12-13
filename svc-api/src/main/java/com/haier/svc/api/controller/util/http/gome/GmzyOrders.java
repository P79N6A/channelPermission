package com.haier.svc.api.controller.util.http.gome;

import java.util.List;

/**
 * Created by LuJun on 16/5/11.
 */
public class GmzyOrders {
    private String resultCode;//调用结果
    private String resultInfo;//结果说明
    private List<OrderList> list;

    public List<OrderList> getList() {
        return list;
    }

    public void setList(List<OrderList> list) {
        this.list = list;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
