package com.haier.afterSale.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.HPRejectionService;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.service.ShopDepartMentCodeSkuService;
import com.haier.shop.service.ShopOperationAreaService;
import com.haier.shop.service.WwwHpRecordService;
import com.haier.stock.service.StockInvMachineSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//@Configuration
//@EnableScheduling
@Service
public class HPRejectionServiceImpl implements HPRejectionService {
    private static Logger logger = LoggerFactory.getLogger(HopStockServiceImpl.class);
    @Autowired
    private WwwHpRecordService wwwHpRecordService;
    @Autowired
    private OperationAreaService operationAreaService;
    @Autowired
    private ShopOperationAreaService shopOperationAreaService;
    @Autowired
    private StockInvMachineSetService stockInvMachineSetService;

    @Override
    //@Scheduled(cron = "0/10 * * * * ? ")
    public void handleHPRejection()throws Exception{
        List<Map<String,Object>> hpRecordsList=wwwHpRecordService.selectBysuccess();
        if (hpRecordsList.size()==0){
            logger.info("暂时没有需要重置异常的数据!");
            return ;
        }
        //hpRecordsList:查询出WwwHpRecords(HP拒收表的数据)
        for (Map map:hpRecordsList){
            String hpTbSn=map.get("hpTbSn")+"";
            //selectMap:根据TB单号查询网单
            List<Map<String,Object>> selectMapList=shopOperationAreaService.queryOrderProductByTB(hpTbSn);
            Map<String,Object> selectMap = null;

            if(selectMapList != null && selectMapList.size() > 0){
                selectMap = selectMapList.get(0);
            }

            //匹配网单失败
            if (selectMap==null){
                //更新WwwHpRecords表中的匹配次数
                map.put("remark","未匹配到网单号");
                shopOperationAreaService.updateHPjushouCount(map);
                continue;
            }
            //匹配网单成功
            if (selectMap!=null){
                //sku相同
                if (selectMap.get("sku").toString().equals(map.get("sku").toString())){
                    //根据TB查询出的数据中没有退货单号
                    if (selectMap.get("th_id") == null){
                        if (rejectionOrderNoRepairsOrder(map, selectMap)) {
                            continue;
                        }

                    }
                    //根据TB查询出的数据中有退货单号
                    if (selectMap.get("th_id") != null){
                        //如果退货单中的状态为以终止,重新生成退货单
                        rejectionOrderHaveRepairsOrder(map, selectMap);
                    }
                }
                //sku不同,有可能是套机,套机HP回传的是子物料,网单存的是主物料,需要去套机表中根据子物料
                //得到主物料,然后根据判断是不是和网单表里面的主物料是否一样
                if (!selectMap.get("sku").toString().equals(map.get("sku").toString())){
                    String sku=map.get("sku")+"";

                    Map<String,Object> skuMap=new HashMap<>();
                    skuMap.put("mainSku", selectMap.get("sku").toString());
                    skuMap.put("subSku",sku);

                    List<Map<String, Object>> skuList = stockInvMachineSetService.selectMainSkuAndSubSku(skuMap);

                    if (skuList != null && skuList.size() > 0){
                        List<Map<String,Object>> repairOrders = shopOperationAreaService.queryRepairsOrderProductByTB(hpTbSn, selectMap.get("sku").toString());
                        //已经匹配成功过了

                        if (repairOrders != null && repairOrders.size() > 0 && repairOrders.get(0).get("th_id") != null){

                            Map<String, Object> orderrepairsMap = repairOrders.get(0);
                            orderrepairsMap.put("sku", sku);
                            List<Map<String,Object>> list=new ArrayList<>();
                            list.add(orderrepairsMap);
                            operationAreaService.update_WwwHpRecordsInfo(list);
                            continue;
                        }

                            //根据TB查询出的数据中没有退货单号
                            if (selectMap.get("th_id") == null){
                                if (rejectionOrderNoRepairsOrder(map, selectMap)) {
                                    continue;
                                }

                            }
                            //根据TB查询出的数据中有退货单号
                            if (selectMap.get("th_id") != null){
                                rejectionOrderHaveRepairsOrder(map, selectMap);

                            }
                    }else{
                        //一个tb号会对应多个网单
                        List<Map<String,Object>> repairOrders = shopOperationAreaService.queryRepairsOrderProductByTBSKU(hpTbSn, sku);
                        if (repairOrders != null && repairOrders.size() > 0){
                            Map<String, Object> orderMaps = repairOrders.get(0);
                            if (orderMaps.get("th_id") == null){
                                if (rejectionOrderNoRepairsOrder(map, orderMaps)) {
                                    continue;
                                }

                            }else{
                                rejectionOrderHaveRepairsOrder(map, orderMaps);

                            }

                        }else{
                            map.put("remark","sku不存在,匹配失败");
                            //更新WwwHpRecords表中的匹配次数
                            shopOperationAreaService.updateHPjushouCount(map);
                        }

                    }

                }
            }
        }
        return ;
    }

    private boolean rejectionOrderNoRepairsOrder(Map map, Map<String, Object> selectMap) throws Exception {
        //生成退货单号
        JSONObject jsonObject=generateTHSn(selectMap,"定时任务");
        //1:生成退货单号失败
        if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
            //更新WwwHpRecords表中的匹配次数
            map.put("remark","生成退货单号失败");
            shopOperationAreaService.updateHPjushouCount(map);
            return true;
        }
        //2:生成退货单号成功
        if (!StringUtil.isEmpty(jsonObject.get("th_id")+"")){
            addOrderRepairLogs("定时任务",Integer.parseInt(jsonObject.get("th_id").toString()),"系统","拒收匹配生成退货单成功");

            selectMap.put("th_id",jsonObject.get("th_id"));
            map.put("th_id",jsonObject.get("th_id"));
            if (StringUtil.isEmpty(map.get("vomRepairSn").toString()) || "0".equals(map.get("vomRepairSn").toString()) || "null".equals(map.get("vomRepairSn").toString())){
                map.put("vomRepairSn","");
                //发起鉴定请求
                Json json=ModifyPushHP(jsonObject.get("th_id").toString());
                //1:发起一次质检失败
                if (json.getSuccess()==false){
                    map.put("remark","发起一次质检失败");
                    //更新WwwHpRecords表中的匹配次数
                    shopOperationAreaService.updateHPjushouCount(map);
                    return true;
                }
                addOrderRepairLogs("定时任务",Integer.parseInt(jsonObject.get("th_id").toString()),"系统","发起一次质检");

            }
            if (!StringUtil.isEmpty(map.get("vomRepairSn").toString()) && !"0".equals(map.get("vomRepairSn").toString()) && !"null".equals(map.get("vomRepairSn").toString())){
                addOrderRepairLogs("定时任务",Integer.parseInt(jsonObject.get("th_id").toString()),"系统","等待HP确认拒收信息");
            }
            selectMap.put("th_id",jsonObject.get("th_id").toString());
            map.put("handleStatus",2);
            //更改退货表中的vomRepairSn和handleStatus字段值
            shopOperationAreaService.updateOrderRepairsStatus(map);
            addOrderRepairLogs("定时任务",Integer.parseInt(jsonObject.get("th_id").toString()),"系统","更改退货单的状态为进行中");

            List<Map<String,Object>> list=new ArrayList<>();
            selectMap.put("sku", map.get("sku"));
            list.add(selectMap);
            operationAreaService.update_WwwHpRecordsInfo(list);
            addOrderRepairLogs("定时任务",Integer.parseInt(jsonObject.get("th_id").toString()),"系统","HP拒收匹配成功");

            return true;
        }
        return false;
    }

    private void rejectionOrderHaveRepairsOrder(Map map, Map<String, Object> selectMap) throws Exception {
        //如果退货单中的状态为以终止,重新生成退货单
        if (selectMap.get("handleStatus").toString().equals("6")){
            //生成退货单号
            JSONObject jsonObject=generateTHSn(selectMap,"定时任务");
            if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
                //更新WwwHpRecords表中的匹配次数
                map.put("remark","生成退货单号失败");
                shopOperationAreaService.updateHPjushouCount(map);
                return;
            }
            selectMap.put("th_id",jsonObject.get("th_id"));
        }
        map.put("th_id",selectMap.get("th_id"));
        if (StringUtil.isEmpty(map.get("vomRepairSn").toString()) || "0".equals(map.get("vomRepairSn").toString()) || "null".equals(map.get("vomRepairSn").toString())){
            map.put("vomRepairSn","");
            //发起鉴定请求
            Json json=ModifyPushHP(selectMap.get("th_id").toString());
            //1:发起一次质检失败
            if (json.getSuccess()==false){
                map.put("remark","生成一次质检失败");
                //更新WwwHpRecords表中的匹配次数
                shopOperationAreaService.updateHPjushouCount(map);
                return;
            }
            addOrderRepairLogs("定时任务",Integer.parseInt(selectMap.get("th_id").toString()),"系统","发起一次质检");
        }
        if (!StringUtil.isEmpty(map.get("vomRepairSn").toString()) && !"0".equals(map.get("vomRepairSn").toString()) && !"null".equals(map.get("vomRepairSn").toString())){
            addOrderRepairLogs("定时任务",Integer.parseInt(selectMap.get("th_id").toString()),"系统","等待HP确认拒收");
        }
        //如果退货单中的状态为以驳回,直接匹配异常
        if (selectMap.get("handleStatus").toString().equals("5")){
            map.put("remark","退货单中的状态为以驳回");
            //更新WwwHpRecords表中的匹配次数
            shopOperationAreaService.updateHPjushouCount(map);
            addOrderRepairLogs("定时任务",Integer.parseInt(selectMap.get("th_id").toString()),"系统","退货单中的状态为以驳回");
            return;
        }
        map.put("handleStatus",2);
        //更改退货表中的vomRepairSn和handleStatus字段值
        shopOperationAreaService.updateOrderRepairsStatus(map);
        addOrderRepairLogs("定时任务",Integer.parseInt(selectMap.get("th_id").toString()),"系统","更改退货单的状态为进行中");
        List<Map<String,Object>> list=new ArrayList<>();
        selectMap.put("sku", map.get("sku"));
        list.add(selectMap);
        operationAreaService.update_WwwHpRecordsInfo(list);
        addOrderRepairLogs("定时任务",Integer.parseInt(selectMap.get("th_id").toString()),"系统","HP拒收匹配成功");
        return;
    }

    //生成退货单号拼装信息
    public JSONObject generateTHSn(Map<String,Object> map, String userName){
        JSONObject jsonObject=new JSONObject();
        String cOrderSn=map.get("cOrderSn").toString();
        Map<String,Object>  maps=shopOperationAreaService.selectPhoneAndName(cOrderSn);
        OrderRepairsVo orderRepairsVo=new OrderRepairsVo();
        orderRepairsVo.setcOrderSnId(cOrderSn);
        orderRepairsVo.setReason("其他");
        orderRepairsVo.setDescription("拒收商品");
        orderRepairsVo.setContactMobile(maps.get("mobile").toString());
        orderRepairsVo.setContactName((maps.get("consignee").toString()));
        orderRepairsVo.setType("1");
        Json json=operationAreaService.SaveRepairs(orderRepairsVo,userName);
        if (json.getSuccess()){
            String x=json.getObj().toString();
            //退货表的主键
            jsonObject.put("th_id",x);
            List<OrderRepairsVo> voList=new ArrayList<>();
            OrderRepairsVo orderRepairsVo1=new OrderRepairsVo();
            orderRepairsVo1.setId(Integer.parseInt(x));
            orderRepairsVo1.setMenuflag("JS");
            voList.add(orderRepairsVo1);
            return jsonObject;
        }
        jsonObject.put("th_id","");
        return jsonObject;
    }
    //发起鉴定请求
    public Json ModifyPushHP(String th_id)throws Exception{
        List<OrderRepairsVo> voList=new ArrayList<>();
        OrderRepairsVo vo=new OrderRepairsVo();
        vo.setId(Integer.parseInt(th_id));
        vo.setMenuflag("JS");
        voList.add(vo);
        Json json=operationAreaService.ModifyPushHP(voList,"定时任务");
        return json;
    }
    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }
    //添加退换货日志表
    public void addOrderRepairLogs(String userName,Integer orderRepairId,String operate,String remark){
        long addTime=new Date().getTime()/1000;
        Map<String,Object> map=new HashMap<>();
        map.put("userName",userName);
        map.put("addTime",addTime);
        map.put("orderRepairId",orderRepairId);
        map.put("remark",remark);
        map.put("operate",operate);
        shopOperationAreaService.insertOrderRepairLog(map);
    }
}
