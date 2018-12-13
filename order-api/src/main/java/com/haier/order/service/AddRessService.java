package com.haier.order.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.Merchandise;
import com.haier.shop.dto.RegionsDTO;
import com.haier.shop.model.Orders;
import com.haier.shop.model.Products;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AddRessService {
    public List<RegionsDTO> getRegionsAll() ;
    public List<Map<String,Object>> getProductCates();
    public List<Map<String,Object>> getBrands();
    public List<Map<String,Object>> getProducts();
    public List<Map<String,Object>> getProductBy(Map<String,Object> map);
    public List<Map<String,Object>> getProductInfo(List<String> list);
    String addProduct(Map<String,Object> map, List<Merchandise> list1 );
    ServiceResult<Map<String,Integer>> insertInvWarehouses(List<Merchandise> invWarehouses, HashMap<String,String> param);

    Products getProductIsBySku(String sku);

    Orders getOrderByOrderSn(String sourceOrderSn);

    List<RegionsDTO> getRegionsParentId(String parentId);

    Orders getOrderByRelationOrderSn(String connectOrderNum);
}
