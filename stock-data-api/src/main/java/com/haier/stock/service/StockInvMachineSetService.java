package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvStore;

public interface StockInvMachineSetService {

   public  List<InvMachineSet> getByMainSku(String mainSku);


    public List<Map<String,Object>> select_sku(List<Map<String,Object>> list);
    
    public List<InvMachineSet> getBySubSku(String subSku);
    
    /**
     * 根据主sku和bom项目号获取
     * @param mainSku
     * @param bomNum
     * @return
     */
    public InvMachineSet getByMainSkuAndBomNum(String mainSku,String bomNum);
    
    public Integer insert(InvMachineSet machineSet);

    public  Integer update(InvMachineSet machineSet);

 List<InvMachineSet> getPageByCondition(InvMachineSet condition, int start, int pageSize);

 long getPagerCount(InvMachineSet condition);

 Integer updateSubSku(String sku, Integer s, String currentUser);
}
