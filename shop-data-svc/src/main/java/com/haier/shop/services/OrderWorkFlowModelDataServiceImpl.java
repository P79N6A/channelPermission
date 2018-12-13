package com.haier.shop.services;

import com.haier.common.util.StringUtil;
import com.haier.shop.dao.settleCenter.OrderWorkflowsDao;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.service.OrderWorkFlowModelDataService;
import com.haier.shop.util.NumberParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/28.
 */
@Service
public class OrderWorkFlowModelDataServiceImpl implements OrderWorkFlowModelDataService{
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderWorkFlowModelDataServiceImpl.class);
    ThreadLocal<Map<Integer, OrderWorkflowRegion>> threadRegionMap = new ThreadLocal<Map<Integer, OrderWorkflowRegion>>();
    ThreadLocal<Map<String, String>> threadStorageMap = new ThreadLocal<Map<String, String>>();
    ThreadLocal<Map<String, List<BigStoragesRela>>> threadCodeBigStoragesRelaMap = new ThreadLocal<Map<String, List<BigStoragesRela>>>();

    @Autowired
    private OrderWorkflowsDao orderWorkflowsDao;


    ThreadLocal<Map<Long, Integer>> threadShippingTimeMap = new ThreadLocal<Map<Long, Integer>>();

    ThreadLocal<Map<Long, Integer>> threadReceivingTimeMap = new ThreadLocal<Map<Long, Integer>>();
    /**
     * 取得区县id和区域工贸对应数据
     * @return
     */
    @Override
    public Map<Integer, OrderWorkflowRegion> getRegionMap() {
        Map<Integer, OrderWorkflowRegion> regionMap = threadRegionMap.get();
        if (null == regionMap) {
            regionMap = new HashMap<Integer, OrderWorkflowRegion>();
            try {
                List<OrderWorkflowRegion> list = orderWorkflowsDao.getOwfRegion();
                if (null == list || list.isEmpty()) {
                    return regionMap;
                }
                for (OrderWorkflowRegion orderWorkflowRegion : list) {
                    regionMap.put(orderWorkflowRegion.getRegionId(), orderWorkflowRegion);
                    //                orderWorkflowRegion.getQyName();//区域
                    //                orderWorkflowRegion.getGmName();//工贸
                    //                orderWorkflowRegion.getSecName();//中心,仓库
                }
            } catch (Exception e) {
                log.error("查询区县工贸中心出错", e);
            }
            threadRegionMap.set(regionMap);
        }
        return regionMap;
    }
    /**
     * 取得库位编码和名称对应关系
     */
    public Map<String, String> getStorageName() {
        Map<String, String> storageMap = threadStorageMap.get();
        if (null == storageMap) {
            storageMap = new HashMap<String, String>();
            try {
                List<Map<String, String>> storageList = orderWorkflowsDao.getStorages();
                for (Map<String, String> map : storageList) {
                    storageMap.put((String) map.get("code"), (String) map.get("name"));
                }
            } catch (Exception e) {
                log.error("查询库位名称出错", e);
            }
            threadStorageMap.set(storageMap);
        }
        return storageMap;
    }
    public Map<String, List<BigStoragesRela>> getCodeBigStoragesRelaMap() {
        Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap = threadCodeBigStoragesRelaMap
                .get();
        if (null == codeBigStoragesRelaMap) {
            codeBigStoragesRelaMap = new HashMap<String, List<BigStoragesRela>>();
            //查询大家电多级库位关系列表
            try {
                List<BigStoragesRela> bigStoragesRelaList = orderWorkflowsDao
                        .getBigStoragesRelaList();
                for (BigStoragesRela bigStoragesRela : bigStoragesRelaList) {
                    if (!codeBigStoragesRelaMap.containsKey(bigStoragesRela.getCode())) {
                        codeBigStoragesRelaMap.put(bigStoragesRela.getCode(),
                                new ArrayList<BigStoragesRela>());
                    }
                    codeBigStoragesRelaMap.get(bigStoragesRela.getCode()).add(bigStoragesRela);
                }
            } catch (Exception e) {
                log.error("查询大家电多级库位关系列表出错", e);
            }
            threadCodeBigStoragesRelaMap.set(codeBigStoragesRelaMap);
        }
        return codeBigStoragesRelaMap;
    }

    /**
     * 获取改约信息
     */
    public List<Map<String, Object>> getGaiyueInfo(Long orderProductId) {
        return orderWorkflowsDao.getGaiyueInfo(orderProductId);
    }

    @Override
    public List<Map<String, Object>> getShippingTimeByRegionId(Long region) {
        return orderWorkflowsDao.getShippingTimeByRegionId(region);
    }

    @Override
    public List<OrderWorkflowRegion> getOwfRegion() {
        return orderWorkflowsDao.getOwfRegion();
    }

    @Override
    public List<Map<String, String>> getStorages() {
        return orderWorkflowsDao.getStorages();
    }

    @Override
    public List<BigStoragesRela> getBigStoragesRelaList() {
        return orderWorkflowsDao.getBigStoragesRelaList();
    }

    @Override
    public List<Map<String, String>> getTradePersonCfg() {
        return orderWorkflowsDao.getTradePersonCfg();
    }

    @Override
    public double getDistances(Long regionId) {
        String rtn = orderWorkflowsDao.getDistances(regionId);
        if (StringUtil.isEmpty(rtn)) {
            return 0.0;
        }
        return Double.parseDouble(rtn);
    }
    @Override
    public Integer formatShippingTime(Integer shippingTime, Long region, Integer type) {
        //配送时效map<regionID,配送时效天数>
        Map<Long, Integer> shippingTimeMap = threadShippingTimeMap.get();
        //入库时效map<regionID,入库时效天数>
        Map<Long, Integer> receivingTimeMap = threadReceivingTimeMap.get();
        //判断配送时效是否为0，如果是0，根据region计算
        if (shippingTime > 0)
            return shippingTime;
        //计算配送时效
        if (shippingTimeMap == null) {
            shippingTimeMap = new HashMap<Long, Integer>();
            receivingTimeMap = new HashMap<Long, Integer>();
            try {
                List<Map<String, Object>> shippingTimeList = orderWorkflowsDao
                        .getShippingTimeByRegionId(region);
                for (Map<String, Object> map : shippingTimeList) {
                    shippingTimeMap.put(NumberParseUtil.parseLong(map.get("id")),
                            NumberParseUtil.parseInteger(map.get("shippingTime")));
                    receivingTimeMap.put(NumberParseUtil.parseLong(map.get("id")),
                            NumberParseUtil.parseInteger(map.get("receivingTime")));
                }
            } catch (Exception e) {
                log.error("查询配送时效出错", e);
            }
            threadShippingTimeMap.set(shippingTimeMap);
            threadReceivingTimeMap.set(receivingTimeMap);
        }
        if (0 == type) {
            shippingTime = shippingTimeMap.get(region);
            //配送时效为空时，用户不可以下单，但是有下单的情况，暂时改为15推迟差的时间
            if (NumberParseUtil.parseInteger(shippingTime) == 0) {
                //新逻辑，时效0天的按0天计算
                //shippingTime = 15;
                shippingTime = 0;
            }
        } else {
            shippingTime = receivingTimeMap.get(region);
        }
        if (null == shippingTime) {
            shippingTime = 0;
        }
        return shippingTime;
    }
}
