package com.haier.distribute.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.TAdjustData;
import com.haier.distribute.data.service.OrderProductsService;
import com.haier.distribute.data.service.TAdjustDataService;
import com.haier.distribute.service.DistributeCenterAdjustService;
import com.haier.shop.service.InvoicesService;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 16:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class DistributeCenterAdjustServiceImpl implements DistributeCenterAdjustService {

   @Autowired
    private TAdjustDataService tAdjustDataService;
    public JSONObject productList(PagerInfo pager, TAdjustData condition) {

        List<TAdjustData> list = tAdjustDataService.getPageByCondition(condition,
                pager.getStart(), pager.getPageSize());

        List<TAdjustData> exportList = tAdjustDataService.getPageByCondition(condition,
                0, Integer.MAX_VALUE);
        long total = tAdjustDataService.getPagerCount(condition);
        BigDecimal amount = new BigDecimal(0);
        for (TAdjustData order : list) {
            String field1 = "";
            if("1".equals(order.getInvoiceStatus())){
                field1 = "已开票";
            } else if("2".equals(order.getInvoiceStatus())){
                field1 = "当月作废";
            } else if("3".equals(order.getInvoiceStatus())){
                field1 = "跨月冲红";
            }
            order.setInvoiceStatus(field1);// 开票状态invoiceStatus
            //状态dataStatus
            String field2 = "";
            if("1".equals(order.getDataStatus())){
                field2 = "已开票";
            } else if("2".equals(order.getDataStatus())){
                field2 = "作废/冲红";
            }
            order.setDataStatus(field2);

            //结算方式settleType
            String jsfs = "";
            if("1".equals(order.getSettleType())){
                jsfs = "系统";
            } else if("2".equals(order.getSettleType())){
                jsfs = "人工";
            }
            order.setSettleType(jsfs);

            //审核状态auditStatus
            String shzt = "";
            if("1".equals(order.getAuditStatus())){
                shzt = "业务审核";
            } else if("2".equals(order.getAuditStatus())){
                shzt = "财务审核";
            } else if("3".equals(order.getAuditStatus())){
                shzt = "审核完成";
            }
            order.setAuditStatus(shzt);
            order.setpAmount(order.getProductAmount().multiply(new BigDecimal(order.getNumber())));
        }
        for (TAdjustData order : exportList) {
            amount = amount.add(order.getProductAmount().multiply(new BigDecimal(order.getNumber())));
        }

        return jsonResultForProduct(list, total,amount);
    }

    @Override
    public Map<String, Object> update(String id, String type) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        TAdjustData adjustDTO = new TAdjustData();
        adjustDTO.setId(Long.parseLong(id));
        adjustDTO.setAuditStatus(type);
        if("2".equals(type)){
            adjustDTO.setBusinessAuditPeople("no one");
            adjustDTO.setBusinessAuditTime(sdf.format(new Date()));
        }else{
            adjustDTO.setFinanceAuditPeople("no one");
            adjustDTO.setFinanceAuditTime(sdf.format(new Date()));

        }
        int r = tAdjustDataService.updateSelectiveByAdjustNo(adjustDTO);
        Map<String, Object> map=new HashMap<String, Object>();

        if(r<1){
            map.put("success", false);
            map.put("del_jg", "更新失败");
        }else{
            map.put("success", true);
            map.put("del_jg", "更新成功");
        }

        return map;
    }

    @Override
    public List<TAdjustData> exportAdjustList(TAdjustData tad) {
        return tAdjustDataService.exportAdjustList(tad);
    }

    private <T> JSONObject jsonResultForProduct(List<T> list, long total,BigDecimal a) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        List<Map<String,Object>> mapList1=new ArrayList<Map<String,Object>>();
        Map<String,Object> tmap1=new HashMap<String,Object>();
        tmap1.put("cOrderSn", "总金额");
        tmap1.put("cOrderSnOld", a);
        mapList1.add(tmap1);

        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
            json.put("footer", mapList1);
        } else {
            json.put("rows", list);
            json.put("footer", mapList1);
        }
        return json;
    }
}