package com.haier.stock.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.service.InvBaseStockService;
import com.haier.stock.service.SvcInvBaseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SvcInvBaseStockServiceImpl implements SvcInvBaseStockService {
    @Autowired
    private InvBaseStockService invBaseStockService;
    public  List<InvBaseStockExcel> queryInvBaseStockList1(InvBaseStockExcel invBaseStock, PagerInfo pager){
        List<InvBaseStockExcel> list=invBaseStockService.queryInvBaseStockList1(invBaseStock,pager);
        for (InvBaseStockExcel invBaseStockExcel :list){
            invBaseStockExcel.setCreateTime(invBaseStockExcel.getCreateTime().substring(0, invBaseStockExcel.getCreateTime().length()-2));
            invBaseStockExcel.setUpdateTime(invBaseStockExcel.getUpdateTime().substring(0, invBaseStockExcel.getUpdateTime().length()-2));
        }
        return list;
    }
    public JSONObject queryInvBaseStockList(Map<String,String> map, PagerInfo pager){
        List<InvBaseStockExcel> list=invBaseStockService.queryInvBaseStockList(map,pager);
        for (InvBaseStockExcel invBaseStockExcel :list){
            invBaseStockExcel.setCreateTime(invBaseStockExcel.getCreateTime().substring(0, invBaseStockExcel.getCreateTime().length()-2));
            invBaseStockExcel.setUpdateTime(invBaseStockExcel.getUpdateTime().substring(0, invBaseStockExcel.getUpdateTime().length()-2));
        }
        List<InvBaseStockExcel> countList=invBaseStockService.queryInvBaseStockList(map);
        return jsonResult(list,countList.size());
    }
    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }
    public int getRowCnt(){
       return invBaseStockService.getRowCnt();
    }
}
