package com.haier.order.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.haier.dbconfig.model.ChanneLinventoryResponse;
import com.haier.dbconfig.model.InterfaceEnum;
import com.haier.dbconfig.model.ReturnOrderRequest;
import com.haier.dbconfig.model.SyncOrderConfigs;
import com.haier.order.model.ShopEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.haier.eis.model.GQGYSStock;
import com.haier.eis.model.SkuStockRelation;
import com.haier.eis.service.EisGQGYSStockService;
import com.haier.eis.service.EisSkuStockRelationService;

/**
 * Created by Administrator on 2017/11/27 0027.
 */
@Service
public class OrderCenterSkuStockRelationServiceImpl {
    private static final Logger log = LogManager.getLogger(OrderCenterSkuStockRelationServiceImpl.class);

    @Resource
    private OrderCenterCainiaoInterfaceServiceImpl orderThirdCenterCainiaoInterfaceService;
    @Resource
    private EisSkuStockRelationService eisSkuStockRelationService;
    @Resource
    private EisGQGYSStockService eisGQGYSStockService;

//    
//    public String syncSkuStock(ShopEnum shop) {
//        log.info("3w获取接口开始:" + System.currentTimeMillis());
//        // 获取店铺认证信息
////        SyncOrderConfigs config = readSyncOrderConfigsDao.getById(shop.getConfigId());
//        SyncOrderConfigs config = null;
//        // 奇门接口返回的数据是否到最后了，如果不是就循环
//        boolean hasNext = true;
//        // 循环执行的状态，默认为false，如果都正常执行，则为true，则更新最后执行时间
//        boolean status = false;
//        int currentPage = 1;
//        // 执行次数，如果请求发生异常，则重复发送一次
//        int count = 0;
//        List<GQGYSStock> list = eisGQGYSStockService.getAll();
//        while (hasNext) {
//            // 声明入参，所有接口使用同一入参对象，通过赋值字段区分不同接口
//            for (GQGYSStock gQGYSStock : list) {
//                ReturnOrderRequest request = new ReturnOrderRequest();
//                request.setOwnerCode("2998123754");
//                List<String> channelCodeslist = new ArrayList<String>();
//                ReturnOrderRequest.ChannelCodes channelCodes = new ReturnOrderRequest.ChannelCodes();
//                channelCodes.setChannelCode(channelCodeslist);
//                request.setChannelCodes(channelCodes);
//                request.setWarehouseCodes(new ReturnOrderRequest.WarehouseCodes());
//                List<String> itemCodeslist = new ArrayList<String>();
//                itemCodeslist.add(gQGYSStock.getSku());
//                ReturnOrderRequest.ItemCodes itemCodes = new ReturnOrderRequest.ItemCodes();
//                itemCodes.setItemCode(itemCodeslist);
//                request.setItemCodes(itemCodes);
//                // 入参赋值，不同的接口使用不同的字段值
//                // 初始化接口请求对象
//                OrderThirdCenterCainiaoInterfaceServiceImpl service = new OrderThirdCenterCainiaoInterfaceServiceImpl();
////                service.setTargetUrl("http://qimen.api.taobao.com/router/qimen/service");
//                ChanneLinventoryResponse response = null;
//                try {
//                    response = (ChanneLinventoryResponse) orderThirdCenterCainiaoInterfaceService.getMethodInfo(InterfaceEnum.STOCK_QUERY, config, request);
//                    String flag = response.getFlag();
//                    if (null != flag && flag.equals("success")) {
//                        ChanneLinventoryResponse.ItemInventories itemInventories = response.getItemInventories();
//                        List<ChanneLinventoryResponse.ItemInventories.itemInventory> itList = itemInventories.getItemInventory();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
//                        Date d = new Date();
//                        String now = sdf.format(d);
//                        String createTime = sdf1.format(d);
//                        List<SkuStockRelation> insertList = new ArrayList<SkuStockRelation>();
//                        for (ChanneLinventoryResponse.ItemInventories.itemInventory it : itList) {
//                            String channelCode = it.getChannelCode();
//                            String itemCode = it.getItemCode();
//                            String lockQuantity = it.getLockQuantity();
//                            String quantity = it.getQuantity();
//                            String warehouseCode = it.getWarehouseCode();
//                            SkuStockRelation ssr = new SkuStockRelation();
//                            ssr.setSku(itemCode);
//                            ssr.setStockCode(warehouseCode);
//                            ssr.setChannelCode(channelCode);
//                            ssr.setQuantity(Integer.parseInt(quantity));
//                            ssr.setLockQuantity(Integer.parseInt(lockQuantity));
//                            ssr.setCreateDate(now);
//                            ssr.setCreateTime(createTime);
//                            insertList.add(ssr);
//                        }
//                        eisSkuStockRelationService.insertList(insertList);
//                    } else {
//                        log.info("菜鸟库存返回flag = " + flag + " ,message = " + response.getMessage());
//                    }
//                } catch (Exception e) {
//                    log.info("菜鸟库存返回信息:" + response);
//                    log.info("菜鸟库存存储异常:" + e.getMessage());
//                }
//            }
//            log.info("3w获取接口结束:" + System.currentTimeMillis());
//            hasNext = false;
//        }
//
//        return null;
//    }

    public List<SkuStockRelation> qryStockSyncLogDaily(ShopEnum shop, List<String> skuList, List<String> scodeList) {
        SyncOrderConfigs config = null;
        List<SkuStockRelation> insertList = new ArrayList<SkuStockRelation>();
        // 奇门接口返回的数据是否到最后了，如果不是就循环
        boolean hasNext = true;
        List<GQGYSStock> list = null;
        Set<String> skuSet = new HashSet<String>();
        Set<String> scodeSet = new HashSet<String>();
        List<String> _skuList = new ArrayList<String>();
        if (null != skuList) {
            for (String sku : skuList) {
                skuSet.add(sku);
            }
        } else {
            list = eisGQGYSStockService.getAll();
            for (GQGYSStock g : list) {
                skuSet.add(g.getSku());
            }
        }
        for (String str : skuSet) {
            _skuList.add(str);
        }
        if (null != scodeList) {
            for (String scode : scodeList) {
                scodeSet.add(scode);
            }
        }
        int compareCount = 0;
        while (hasNext) {
            // 声明入参，所有接口使用同一入参对象，通过赋值字段区分不同接口
            ReturnOrderRequest request = new ReturnOrderRequest();
            request.setOwnerCode("2998123754");
            List<String> channelCodeslist = new ArrayList<String>();
            ReturnOrderRequest.ChannelCodes channelCodes = new ReturnOrderRequest.ChannelCodes();
            ReturnOrderRequest.WarehouseCodes warehouseCodes = new ReturnOrderRequest.WarehouseCodes();
            List<String> wareHouseCodesList = new ArrayList<String>();
            warehouseCodes.setWarehouseCode(wareHouseCodesList);
            if (null != scodeSet) {
                for (String str : scodeSet) {
                    wareHouseCodesList.add(str);
                }
            }
            channelCodes.setChannelCode(channelCodeslist);
            request.setChannelCodes(channelCodes);
            request.setWarehouseCodes(warehouseCodes);
            List<String> itemCodeslist = new ArrayList<String>();
            int count = 0;
            if (null != _skuList) {
                if (count < 50) {
                    int num = (_skuList.size() - compareCount < 50) ? _skuList.size() : compareCount + 50;
                    for (int k = compareCount; k < num; k++) {
                        itemCodeslist.add(_skuList.get(k));
                        count++;
                    }
                }
            }
            compareCount += count;
            ReturnOrderRequest.ItemCodes itemCodes = new ReturnOrderRequest.ItemCodes();
            itemCodes.setItemCode(itemCodeslist);
            request.setItemCodes(itemCodes);
            // 入参赋值，不同的接口使用不同的字段值
            // 初始化接口请求对象
            OrderCenterCainiaoInterfaceServiceImpl service = new OrderCenterCainiaoInterfaceServiceImpl();
            service.setTargetUrl("http://qimen.api.taobao.com/router/qimen/service");
            ChanneLinventoryResponse response = null;
            try {
                response = (ChanneLinventoryResponse) orderThirdCenterCainiaoInterfaceService.getMethodInfo(InterfaceEnum.STOCK_QUERY, config, request);
                String flag = response.getFlag();
                if (null != flag && flag.equals("success")) {
                    if (null != response.getItemInventories()) {
                        ChanneLinventoryResponse.ItemInventories itemInventories = response.getItemInventories();
                        List<ChanneLinventoryResponse.ItemInventories.itemInventory> itList = itemInventories.getItemInventory();
//                        Date d = new Date();
                        for (ChanneLinventoryResponse.ItemInventories.itemInventory it : itList) {
                            String itemCode = it.getItemCode();
                            String lockQuantity = it.getLockQuantity();
                            String quantity = it.getQuantity();
                            String warehouseCode = it.getWarehouseCode();
                            SkuStockRelation ssr = new SkuStockRelation();
                            ssr.setSku(itemCode);
                            ssr.setStockCode(warehouseCode);
                            ssr.setQuantity(Integer.parseInt(quantity));
                            ssr.setLockQuantity(Integer.parseInt(lockQuantity));
//                            ssr.setCreateTime(d);
                            insertList.add(ssr);
                        }
                    } else {
                        //暂无数据
                    }
                } else {
                    log.info("菜鸟库存返回flag = " + flag + " ,message = " + response.getMessage());
                }
            } catch (Exception e) {
                log.info("菜鸟库存返回信息:" + response);
                log.info("菜鸟库存存储异常:" + e.getMessage());
            }
            log.info("3w获取接口结束:" + System.currentTimeMillis());
            if (_skuList.size() == compareCount) {
                hasNext = false;
            }
        }

        return insertList;
    }

    public List<SkuStockRelation> qryStockSyncLog(List skuList, String addTimeStart, List scodeList) {
        List<SkuStockRelation> result = new ArrayList<SkuStockRelation>();
        result = this.eisSkuStockRelationService.queryStockSyncLogList(skuList, addTimeStart, scodeList);
        return result;
    }
}
