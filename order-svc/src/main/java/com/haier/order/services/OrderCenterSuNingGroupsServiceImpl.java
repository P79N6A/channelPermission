package com.haier.order.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.util.StringUtil;
import com.haier.order.model.Ustring;
import com.haier.order.service.OrderCenterSuNingGroupsService;
import com.haier.shop.model.Products;
import com.haier.shop.model.SuningGroups;
import com.haier.shop.service.ProductsService;
import com.haier.shop.service.ShopSuningGroupsService;
import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderCenterSuNingGroupsServiceImpl implements OrderCenterSuNingGroupsService {

    @Autowired
    ShopSuningGroupsService shopSuningGroupsService;
    @Autowired
    ProductsService productsService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        shopSuningGroupsService.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public String insert(SuningGroups record) {
        StringBuilder sBuilder = new StringBuilder();
        String skuandamounts[] = record.getProductSpecs().split(";");
        sBuilder.append("[");
        //1、校验套装中的sku是否存在
        if(StringUtil.isEmpty(record.getSku())) {
            for (int i = 0; i < skuandamounts.length; i++) {
                String[] aa = skuandamounts[i].split("--");
                if (aa.length == 0 || aa.length == 1 || aa.length == 2) {
                    return "操作失败！你有商品输入不完全";
                }
                if ("".equals(aa[1]) || aa[1] == null) {
                    return "操作失败！sku编码:" + aa[0] + "订金价格没有设置";
                }
                if ("".equals(aa[2]) || aa[2] == null) {
                    return "操作失败！sku编码:" + aa[0] + "尾款价格没有设置";
                }
                Products products = productsService.getBySku(aa[0]);
                if (products == null) {
                    return "操作失败！sku编码:" + aa[0] + " 不存在";
                } else {
                    if (i == skuandamounts.length - 1) {
                        sBuilder.append("{\"sku\":\"" + aa[0] + "\",\"depositAmount\":\"" + aa[1] + "\",\"tailAmount\":\"" + aa[2] + "\"}");
                    } else {
                        sBuilder.append("{\"sku\":\"" + aa[0] + "\",\"depositAmount\":\"" + aa[1] + "\",\"tailAmount\":\"" + aa[2] + "\"},");
                    }
                }

            }
        }
        sBuilder.append("]");
        record.setProductSpecs(sBuilder.toString());
        record.setSiteId(1);
        shopSuningGroupsService.insert(record);
        return "操作成功";
    }

    @Override
    public String updateByPrimaryKey(SuningGroups record) {
        //流程逻辑
        StringBuilder sBuilder = new StringBuilder();
        String skuandamounts[] = record.getProductSpecs().split(";");
        sBuilder.append("[");
        //1、校验套装中的sku是否存在
        for(int i= 0; i < skuandamounts.length;i++ ){
            String[] aa = skuandamounts[i].split("--");
            if (aa.length == 0 ||aa.length == 1|| aa.length == 2){
                return "操作失败！你有商品输入不完全";
            }
            if ("".equals(aa[1]) ||aa[1]==null){
                return "操作失败！sku编码:"+aa[0]+"订金价格没有设置";
            }
            if ("".equals(aa[2]) ||aa[2]==null){
                return "操作失败！sku编码:"+aa[0]+"尾款价格没有设置";
            }
            Products products = productsService.getBySku(aa[0]);
            if(products== null){
                return "操作失败！sku编码:"+aa[0]+" 不存在";
            }else {
                if (i == skuandamounts.length-1){
                    sBuilder.append("{\"sku\":\""+aa[0]+"\",\"depositAmount\":\""+aa[1]+"\",\"tailAmount\":\""+aa[2]+"\"}");
                }else {
                    sBuilder.append("{\"sku\":\""+aa[0]+"\",\"depositAmount\":\""+aa[1]+"\",\"tailAmount\":\""+aa[2]+"\"},");
                }
            }

        }
        sBuilder.append("]");
        record.setProductSpecs(sBuilder.toString());
        record.setSiteId(1);
        shopSuningGroupsService.updateByPrimaryKeySelective(record);
        return "操作成功";

    }

    @Override
    public JSONObject Listf(PagerInfo pager, SuningGroups condition) {
        SuningGroups u = new SuningGroups();
        List<SuningGroups> list = shopSuningGroupsService.Listf(condition,pager.getStart(), pager.getPageSize());
        int total = shopSuningGroupsService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (SuningGroups o : list) {
            SuningGroups dto =  o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("source",dto.getSource());
            json.put("siteId",dto.getSiteId());
            json.put("groupName",dto.getGroupName());
            json.put("sku",dto.getSku());
            json.put("depositStartTime",sdf.format(new Date(Long.valueOf(dto.getDepositStartTime()+"000"))));
            json.put("depositEndTime",sdf.format(new Date(Long.valueOf(dto.getDepositEndTime()+"000"))));
            json.put("balanceStartTime",sdf.format(new Date(Long.valueOf(dto.getBalanceStartTime()+"000"))));
            json.put("balanceEndTime",sdf.format(new Date(Long.valueOf(dto.getBalanceEndTime()+"000"))));
            json.put("depositAmount",dto.getDepositAmount());
            json.put("balanceAmount",dto.getBalanceAmount());
            json.put("status",dto.getStatus());
            json.put("shippingOpporunity", dto.getShippingOpporunity());
            json.put("productSpecs", dto.getProductSpecs());
            res.add(json);
        }
        return jsonResult(res,total);
    }

    @Override
    public int jiaoyan(String sku) {
        Products  products=productsService.getBySku(sku);
        int id=0;
        if(products != null){
            id=products.getId();
        }
        return id;
    }

    public List<Products> getProductList(String productSpecs) {
        List<Products> list = new ArrayList<Products>();
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(productSpecs);
        Object result = serializedPhpParser.parse(); //通过php序列化后的字符串，获取java对象
        if (result != null) {
            @SuppressWarnings("rawtypes")
            Map myMap = (HashMap) result;
            @SuppressWarnings("rawtypes")
            Set keys = myMap.keySet();
            for (@SuppressWarnings("rawtypes")
                 Iterator i = keys.iterator(); i.hasNext();) {
                String key = (String) i.next(); //获取sku编号
                BigDecimal price = new BigDecimal(Ustring.getString0(myMap.get(key).toString()));//外部促销价格
                String skutrim = key.trim();
                Products p = productsService.getBySku(skutrim);
                p.setExternalSalePrice(price);
                list.add(p);
            }
        }
        return list;
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
}
