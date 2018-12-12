package com.haier.svc.api.controller.stock.mode;


import com.haier.common.ServiceResult;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockAgeLog;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeHandler;
import com.haier.stock.model.StockAgeWapped;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockInvStockAgeService;
import com.haier.stock.service.StockinvStockAgeLogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;

@Service
public class StockAgeModel {
    private Logger              logger          = LogManager.getLogger(StockAgeModel.class);
   @Autowired
   private StockCommonService stockCommonService;
   @Autowired
    private StockinvStockAgeLogService stockinvStockAgeLogService;
    @Autowired
    private StockInvStockAgeService stockInvStockAgeService;
    /**
     * 按渠道统计库龄
     * @param clearDate
     * @return
     */
    public List<StockAgeWapped> countStockGroupByChannel(Date clearDate) {

        List<InvStockAgeLog> stockAgeGroupByChannel = stockinvStockAgeLogService
            .countStockGroupByChannel(clearDate);

        return prepareReportChannelList(stockAgeGroupByChannel);

    }
    private List<StockAgeWapped> prepareReportChannelList(List<InvStockAgeLog> stockAgeGroupByChannel){
        List<InvStockAge> results = new ArrayList<InvStockAge>();
        List<InvStockChannel> channels = getChannels();
        channels.remove(0);
        for (InvStockChannel channel : channels) {
            InvStockAge stockAge = new InvStockAge();
            stockAge.setChannelCode(channel.getChannelCode());
            stockAge.setChannelName(channel.getName());

            List<InvStockAge.StockAgeData> stockAgeDatas = new ArrayList<InvStockAge.StockAgeData>();

            for (InvStockAgeLog stockAgeLog : stockAgeGroupByChannel) {
                if (channel.getChannelCode().equalsIgnoreCase(stockAgeLog.getChannelCode())) {
                    InvStockAge.StockAgeData ageData = stockAge.new StockAgeData();
                    ageData.setAge(stockAgeLog.getAge());
                    ageData.setStockQuantity(stockAgeLog.getQuantity());
                    ageData.setValue(stockAgeLog.getAmount());
                    stockAgeDatas.add(ageData);
                }
            }
            stockAge.setStockAgeDatas(stockAgeDatas);
            results.add(stockAge);
        }

        List<StockAgeWapped> wappedAges = wappenAgeDatasForStatistics(results);

        //总计
        StockAgeWapped totalAgeWapped = new StockAgeWapped(new InvStockAge());
        totalAgeWapped.wappenAgeDatasForStatistics();
        for (StockAgeWapped ageWapped : wappedAges) {
            for (InvStockAge.StockAgeData ageData : ageWapped.getAgeDatas()) {

                if (ageData.getAge() == -1004 || ageData.getAge() == -1005)
                    continue;

                InvStockAge.StockAgeData _ageData = totalAgeWapped.getAgeData(ageData.getAge());
                _ageData.setStockQuantity(_ageData.getStockQuantity() + ageData.getStockQuantity());
                BigDecimal _value = ageData.getValue();
                _ageData.setValue(_ageData.getValue().add(_value));
                _value.setScale(2);
                //修正金额为万元
                ageData.setValue(_value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP));
            }
        }
        for (InvStockAge.StockAgeData ageData : totalAgeWapped.getAgeDatas()) {
            if (ageData.getAge() == -1004) {
                int quantityExtended = totalAgeWapped.getAgeData(-1002).getStockQuantity();
                BigDecimal valueExtended = totalAgeWapped.getAgeData(-1002).getValue();
                int quantity = totalAgeWapped.getAgeData(-1003).getStockQuantity();
                BigDecimal value = totalAgeWapped.getAgeData(-1003).getValue();
                int quantityBalance = 0;
                BigDecimal valueBalance = new BigDecimal("0.00");
                if (quantity != 0) {
                    BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(
                            new BigDecimal(quantity), BigDecimal.ROUND_HALF_UP);
                    quantityBalance = balance.multiply(new BigDecimal(100)).intValue();
                }
                if (value.compareTo(new BigDecimal(0)) > 0) {
                    BigDecimal balance = valueExtended.divide(value, BigDecimal.ROUND_HALF_UP);
                    valueBalance = balance.multiply(new BigDecimal(100));
                }
                ageData.setStockQuantity(quantityBalance);
                ageData.setValue(valueBalance);
            } else {
                BigDecimal value = ageData.getValue();
                value.setScale(2);
                value = value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP);
                ageData.setValue(value);
            }
        }
        wappedAges.add(0, totalAgeWapped);
        return wappedAges;
    }
    private List<StockAgeWapped> wappenAgeDatasForStatistics(List<InvStockAge> stockAges) {
        List<StockAgeWapped> stockAgeWappeds = new ArrayList<StockAgeWapped>();
        for (InvStockAge invStockAge : stockAges) {
            StockAgeWapped ageWapped = new StockAgeWapped(invStockAge);
            ageWapped.wappenAgeDatasForStatistics();
            stockAgeWappeds.add(ageWapped);
        }
        return stockAgeWappeds;
    }
    public List<InvStockChannel> getChannels() {
        List<InvStockChannel> channels = null;
        ServiceResult<List<InvStockChannel>> result = stockCommonService.getChannels();
        if (!result.getSuccess()) {
            logger.error("获取渠道信息失败:" + result.getMessage());
            channels = new ArrayList<InvStockChannel>();
        } else {
            channels = result.getResult();
        }
        InvStockChannel channel = new InvStockChannel();
        channel.setChannelCode("ALL");
        channel.setName("全部");
        channels.add(0, channel);
        InvStockChannel channel2 = new InvStockChannel();
        channel2.setChannelCode("SHH");
        channel2.setName("社会化渠道");
        channels.add(channel2);
        return channels;
    }
    /**
     * 按产品统计指定渠道的库存
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeWapped>> countStockGroupBySkuWithChannel(Date clearDate,
                                                                             String channel) {

        List<Map<String, Object>> stockAgeGroupSku = stockInvStockAgeService
                .countStockGroupBySkuWithChannel(clearDate, channel);
        return prepareReportMap(stockAgeGroupSku);
    }
    private Map<String, List<StockAgeWapped>> prepareReportMap(List<Map<String, Object>> datas){
        Map<String, List<StockAgeWapped>> retMap = new HashMap<String, List<StockAgeWapped>>();

        String product_type_name = "-1";

        Map<String, List<Map<String, Object>>> sortedMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> map : datas) {
            String _productTypeName = map.get("product_type_name") == null ? "" : map.get(
                    "product_type_name").toString();
            if (!product_type_name.equalsIgnoreCase(_productTypeName)) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                sortedMap.put(_productTypeName, list);
                product_type_name = _productTypeName;
            }
            sortedMap.get(product_type_name).add(map);
        }

        for (Map.Entry<String, List<Map<String, Object>>> entry : sortedMap.entrySet()) {
            String product_group_name = "-1";
            String key = entry.getKey();
            List<Map<String, Object>> list = entry.getValue();
            List<InvStockAge> stockAges = new ArrayList<InvStockAge>();

            InvStockAge stockAge = null;
            for (Map<String, Object> map : list) {
                String _productGroupName = map.get("product_group_name") == null ? "" : map.get(
                        "product_group_name").toString();
                if (!product_group_name.equals(_productGroupName)) {
                    stockAge = new InvStockAge();
                    stockAge.setProductGroupName(_productGroupName);
                    product_group_name = _productGroupName;
                    stockAges.add(stockAge);
                }
                InvStockAge.StockAgeData ageData = stockAge.new StockAgeData();
                ageData.setAge((Integer) (map.get("age")));
                ageData.setStockQuantity(((BigDecimal) map.get("quantity")).intValue());
                ageData.setValue((BigDecimal) map.get("amount"));
                stockAge.getStockAgeDatas().add(ageData);
            }
            retMap.put(key, wappenAgeDatasForStatistics(stockAges));
        }

        StockAgeWapped totalAgeWapped = new StockAgeWapped(new InvStockAge());
        totalAgeWapped.wappenAgeDatasForStatistics();
        for (Map.Entry<String, List<StockAgeWapped>> entry : retMap.entrySet()) {
            List<StockAgeWapped> list = entry.getValue();
            for (StockAgeWapped ageWapped : list) {
                for (InvStockAge.StockAgeData ageData : ageWapped.getAgeDatas()) {

                    int _age = ageData.getAge();

                    if (_age == -1004)
                        continue;

                    int _quantity = ageData.getStockQuantity();
                    BigDecimal _value = ageData.getValue();
                    InvStockAge.StockAgeData totalAgeData = totalAgeWapped.getAgeData(_age);
                    totalAgeData.setStockQuantity(totalAgeData.getStockQuantity() + _quantity);
                    totalAgeData.setValue(totalAgeData.getValue().add(_value));

                    _value.setScale(2);
                    _value = _value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP);
                    ageData.setValue(_value);
                }
            }
        }
        for (InvStockAge.StockAgeData ageData : totalAgeWapped.getAgeDatas()) {
            if (ageData.getAge() == -1004) {
                int quantityExtended = totalAgeWapped.getAgeData(-1002).getStockQuantity();
                BigDecimal valueExtended = totalAgeWapped.getAgeData(-1002).getValue();
                int quantity = totalAgeWapped.getAgeData(-1003).getStockQuantity();
                BigDecimal value = totalAgeWapped.getAgeData(-1003).getValue();
                int quantityBalance = 0;
                BigDecimal valueBalance = new BigDecimal("0.00");
                if (quantity != 0) {
                    BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(
                            new BigDecimal(quantity), BigDecimal.ROUND_HALF_UP);
                    quantityBalance = balance.multiply(new BigDecimal(100)).intValue();
                }
                if (value.compareTo(new BigDecimal(0)) > 0) {
                    BigDecimal balance = valueExtended.divide(value, BigDecimal.ROUND_HALF_UP);
                    valueBalance = balance.multiply(new BigDecimal(100));
                }
                ageData.setStockQuantity(quantityBalance);
                ageData.setValue(valueBalance);
            } else {
                BigDecimal value = ageData.getValue();
                value.setScale(2);
                ageData.setValue(value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP));
            }
        }
        List<StockAgeWapped> totalList = new ArrayList<StockAgeWapped>();
        totalList.add(totalAgeWapped);
        retMap.put("total", totalList);

        return retMap;
    }
    /**
     * 预测准确率-到渠道到产品统计库龄
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeHandler>> countStockGroupToChannelsToProducts(Date clearDate){
        //渠道-产品，汇总数据
        List<Map<String, Object>> stockAgeGroup = stockInvStockAgeService
                .countStockGroupToChannelsToProducts(clearDate, clearDate);
        //总计-产品，汇总数据
        List<Map<String, Object>> sumstockAgeGroup = stockInvStockAgeService
                .sumStockGroupToChannelsToProducts(clearDate, clearDate);
        //总计-小计，汇总数据
        List<Map<String, Object>> totalstockAgeGroup = stockInvStockAgeService
                .totalStockGroupToChannelsToProducts(clearDate, clearDate);
        //渠道-小计，汇总数据
        List<Map<String, Object>> subsumstockAgeGroup = stockInvStockAgeService
                .subSumStockGroupToChannelsToProducts(clearDate, clearDate);
        sumstockAgeGroup.addAll(totalstockAgeGroup);
        sumstockAgeGroup.addAll(stockAgeGroup);
        sumstockAgeGroup.addAll(subsumstockAgeGroup);

        //将总计-小计的超期库龄数量和金额保存在一个变量中，后面算超期权重时使用
        Map<String, List<StockAgeHandler>> totalstockAgeHandler = prepareReportHandler(
                totalstockAgeGroup, "channel_name", "product_type_name");
        if (totalstockAgeGroup != null && totalstockAgeGroup.size() > 0
                && totalstockAgeHandler.get("总计") != null) {
            if (totalstockAgeHandler.get("总计").get(0).getAgeDatas() != null
                    && totalstockAgeHandler.get("总计").get(0).getAgeDatas().size() > 6) {//第7个是超期库龄，取第七个
                Integer quantity = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getStockQuantity();
                BigDecimal amount = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getValue();
                totalTimeOutMap.put("stockQuantity", quantity);
                totalTimeOutMap.put("value", amount);
            }
        }
        Map<String, List<StockAgeHandler>> resultMap = prepareReportHandler(sumstockAgeGroup,
                "channel_name", "product_type_name");
        Map<String, List<StockAgeHandler>> resultMap_jt = countStockGroupToChannelsToProducts_jt(clearDate);
        if (resultMap != null && resultMap_jt != null) {//将计提中的超期库龄小计*3%加到resultMap中，覆盖原来的计提损失
            for (Map.Entry entry : resultMap.entrySet()) {
                String key = entry.getKey().toString();
                List<StockAgeHandler> stockagelist = (List<StockAgeHandler>) entry.getValue();//第一分组-渠道
                if (stockagelist != null && stockagelist.size() > 0) {
                    for (int i = 0; i < stockagelist.size(); i++) {
                        List<InvStockAge.StockAgeData> stockagedatalist = stockagelist.get(i).getAgeDatas();//第二分组-产品
                        if (stockagedatalist != null && stockagedatalist.size() > 0) {
                            for (int j = 0; j < stockagedatalist.size(); j++) {
                                if (stockagedatalist.get(j).getAge() == -1003) {//第三分组-时间段
                                    //计提
                                    List<StockAgeHandler> stockagelist_jt = resultMap_jt.get(key);//第一分组-渠道
                                    if (stockagelist_jt != null && stockagelist_jt.size() > 0) {
                                        for (int k = 0; k < stockagelist_jt.size(); k++) {
                                            if (stockagelist
                                                    .get(i)
                                                    .getStockAge()
                                                    .getProductGroupName()
                                                    .equals(
                                                            stockagelist_jt.get(k).getStockAge()
                                                                    .getProductGroupName())) {//第二分组-产品
                                                List<InvStockAge.StockAgeData> stockagedatalist_jt = stockagelist_jt
                                                        .get(k).getAgeDatas();
                                                if (stockagedatalist_jt != null
                                                        && stockagedatalist_jt.size() > 0) {
                                                    for (int m = 0; m < stockagedatalist_jt.size(); m++) {
                                                        if (stockagedatalist_jt.get(m).getAge() == -1003) {//第三分组-时间段
                                                            //设置合计新的值
                                                            stockagedatalist.get(j)
                                                                    .setStockQuantity(
                                                                            stockagedatalist_jt.get(m)
                                                                                    .getStockQuantity());
                                                            stockagedatalist.get(j).setValue(
                                                                    stockagedatalist_jt.get(m)
                                                                            .getValue());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultMap;
    }
    /**
     * 预测准确率-产品、渠道统计库龄-统计汇总计算
     * @return
     */
    private Map<String, List<StockAgeHandler>> prepareReportHandler(List<Map<String, Object>> datas,
                                                                    String firstGroupField,
                                                                    String secondGroupField){
        Map<String, List<StockAgeHandler>> retMap = new LinkedHashMap<String, List<StockAgeHandler>>();
        firstGroupField = firstGroupField == null || firstGroupField.equals("") ? "product_type_name"
                : firstGroupField;
        secondGroupField = secondGroupField == null || secondGroupField.equals("") ? "channel_name"
                : secondGroupField;
        //        String product_type_name = "-1";

        Map<String, List<Map<String, Object>>> sortedMap = new LinkedHashMap<String, List<Map<String, Object>>>();
        //按照datas数据里的产品类型名(product_type_name)生成一个map对象，产品类型名称为key,
        //将相同产品类型名的记录保存到一个list里，放在刚生成的map同一个产品名作为key的value下
        for (Map<String, Object> map : datas) {
            String _productTypeName = map.get(firstGroupField) == null ? "" : map.get(
                    firstGroupField).toString();
            /*if (!product_type_name.equalsIgnoreCase(_productTypeName)) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                sortedMap.put(_productTypeName, list);
                product_type_name = _productTypeName;
            }*/
            if (sortedMap.get(_productTypeName) == null) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                sortedMap.put(_productTypeName, list);
            }
            sortedMap.get(_productTypeName).add(map);
        }

        //汇总统计---定义存储总计对象
        //        List<StockAgeHandler> totalList = new ArrayList<StockAgeHandler>();
        //        StockAgeHandler totalAgeWapped = new StockAgeHandler(new InvStockAge());
        //        totalAgeWapped.wappenAgeDatasForStatistics();

        //按照sortedMap数据里的产品组名(product_group_name)生成一个map对象，产品类型名称为key,
        //将相同产品类型名的记录保存到一个list里，放在刚生成的map同一个产品名作为key的value下
        for (Map.Entry<String, List<Map<String, Object>>> entry : sortedMap.entrySet()) {
            String product_group_name = "-1";
            String key = entry.getKey();
            List<Map<String, Object>> list = entry.getValue();
            List<InvStockAge> stockAges = new ArrayList<InvStockAge>();

            InvStockAge stockAge = null;
            for (Map<String, Object> map : list) {//添加第二分组
                String _productGroupName = map.get(secondGroupField) == null ? "" : map.get(
                        secondGroupField).toString();
                if (!product_group_name.equals(_productGroupName)) {
                    stockAge = new InvStockAge();
                    stockAge.setProductGroupName(_productGroupName);
                    product_group_name = _productGroupName;
                    stockAges.add(stockAge);
                }
                //添加一行中的所有库龄未分组数据
                InvStockAge.StockAgeData ageData = stockAge.new StockAgeData();
                ageData.setAge((Integer) (map.get("age")));
                ageData.setStockQuantity(((BigDecimal) map.get("quantity")).intValue());
                ageData.setValue((BigDecimal) map.get("amount"));
                stockAge.getStockAgeDatas().add(ageData);
            }
            retMap.put(key, wappenAgeDatasForStatisticsForecast(stockAges));
        }

        for (Map.Entry<String, List<StockAgeHandler>> entry : retMap.entrySet()) {
            List<StockAgeHandler> list = entry.getValue();
            for (StockAgeHandler ageWapped : list) {
                for (InvStockAge.StockAgeData ageData : ageWapped.getAgeDatas()) {
                    int _age = ageData.getAge();
                    if (_age == -1004)
                        continue;
                    BigDecimal _value = ageData.getValue();
                    //修正金额为万元
                    _value = _value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP);
                    ageData.setValue(_value);
                }
            }
        }
        //retMap.put("总计", wappenAgeDatasForStatisticsForecast(totalstockAgeList));
        //总计各个阶段数量和金额
        //        for (Entry<String, List<StockAgeHandler>> entry : retMap.entrySet()) {
        //            List<StockAgeHandler> list = entry.getValue();
        //            for (StockAgeHandler ageWapped : list) {
        //                for (StockAgeData ageData : ageWapped.getAgeDatas()) {
        //
        //                    int _age = ageData.getAge();
        //
        //                    if (_age == -1004)
        //                        continue;
        //
        //                    int _quantity = ageData.getStockQuantity();
        //                    BigDecimal _value = ageData.getValue();
        //                    StockAgeData totalAgeData = totalAgeWapped.getAgeData(_age);
        //                    totalAgeData.setStockQuantity(totalAgeData.getStockQuantity() + _quantity);
        //                    totalAgeData.setValue(totalAgeData.getValue().add(_value));
        //
        //                    _value.setScale(2);
        //                    _value = _value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP);
        //                    ageData.setValue(_value);
        //                }
        //            }
        //        }
        //总计超期，占比，合计等的数量和金额
        //        for (StockAgeData ageData : totalAgeWapped.getAgeDatas()) {
        //            if (ageData.getAge() == -1004) {
        //                int quantityExtended = totalAgeWapped.getAgeData(-1002).getStockQuantity();
        //                BigDecimal valueExtended = totalAgeWapped.getAgeData(-1002).getValue();
        //                int quantity = totalAgeWapped.getAgeData(-1003).getStockQuantity();
        //                BigDecimal value = totalAgeWapped.getAgeData(-1003).getValue();
        //                int quantityBalance = 0;
        //                BigDecimal valueBalance = new BigDecimal("0.00");
        //                if (quantity != 0) {
        //                    BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(
        //                        new BigDecimal(quantity), BigDecimal.ROUND_HALF_UP);
        //                    quantityBalance = balance.multiply(new BigDecimal(100)).intValue();
        //                }
        //                if (value.compareTo(new BigDecimal(0)) > 0) {
        //                    BigDecimal balance = valueExtended.divide(value, BigDecimal.ROUND_HALF_UP);
        //                    valueBalance = balance.multiply(new BigDecimal(100));
        //                }
        //                ageData.setStockQuantity(quantityBalance);
        //                ageData.setValue(valueBalance);
        //            } else {
        //                BigDecimal value = ageData.getValue();
        //                value.setScale(2);
        //                ageData.setValue(value.divide(new BigDecimal(10000), BigDecimal.ROUND_HALF_UP));
        //            }
        //        }
        //        totalList.add(totalAgeWapped);
        //        retMap.put("total", totalList);

        return retMap;
    }
    /**
     * 预测准确率
     * @return
     */
    //汇总超期小计-数量（stockQuantity），金额（value）
    private Map<String, Object> totalTimeOutMap = new HashMap<String, Object>();
    private List<StockAgeHandler> wappenAgeDatasForStatisticsForecast(List<InvStockAge> stockAges) {
        List<StockAgeHandler> stockAgeHandlers = new ArrayList<StockAgeHandler>();
        for (InvStockAge invStockAge : stockAges) {
            StockAgeHandler ageHandler = new StockAgeHandler(invStockAge);
            ageHandler.wappenAgeDatasForStatistics(totalTimeOutMap);
            stockAgeHandlers.add(ageHandler);
        }
        return stockAgeHandlers;
    }
    /**
     * 预测准确率-到渠道到产品统计库龄-计提
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeHandler>> countStockGroupToChannelsToProducts_jt(Date clearDate){
        Date startdate = new Date();
        startdate.setYear(clearDate.getYear());
        startdate.setMonth(clearDate.getMonth());
        startdate.setDate(1);
        startdate.setHours(clearDate.getHours());
        startdate.setMinutes(clearDate.getMinutes());
        startdate.setSeconds(clearDate.getSeconds());

        clearDate.setHours(23);
        clearDate.setMinutes(59);
        clearDate.setSeconds(59);
        //渠道-产品，汇总数据
        List<Map<String, Object>> stockAgeGroup = stockInvStockAgeService
                .countStockGroupToChannelsToProducts(startdate, clearDate);
        //总计-产品，汇总数据
        List<Map<String, Object>> sumstockAgeGroup = stockInvStockAgeService
                .sumStockGroupToChannelsToProducts(startdate, clearDate);
        //总计-小计，汇总数据
        List<Map<String, Object>> totalstockAgeGroup = stockInvStockAgeService
                .totalStockGroupToChannelsToProducts(startdate, clearDate);
        //渠道-小计，汇总数据
        List<Map<String, Object>> subsumstockAgeGroup = stockInvStockAgeService
                .subSumStockGroupToChannelsToProducts(startdate, clearDate);
        sumstockAgeGroup.addAll(totalstockAgeGroup);
        sumstockAgeGroup.addAll(stockAgeGroup);
        sumstockAgeGroup.addAll(subsumstockAgeGroup);

        //将总计-小计的超期库龄数量和金额保存在一个变量中，后面算超期权重时使用
        Map<String, List<StockAgeHandler>> totalstockAgeHandler = prepareReportHandler(
                totalstockAgeGroup, "channel_name", "product_type_name");
        if (totalstockAgeGroup != null && totalstockAgeGroup.size() > 0
                && totalstockAgeHandler.get("总计") != null) {
            if (totalstockAgeHandler.get("总计").get(0).getAgeDatas() != null
                    && totalstockAgeHandler.get("总计").get(0).getAgeDatas().size() > 6) {//第7个是超期库龄，取第七个
                Integer quantity = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getStockQuantity();
                BigDecimal amount = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getValue();
                totalTimeOutMap.put("stockQuantity", quantity);
                totalTimeOutMap.put("value", amount);
            }
        }

        return prepareReportHandler(sumstockAgeGroup, "channel_name", "product_type_name");
    }
    /**
     * 预测准确率-到产品到渠道统计库龄
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeHandler>> countStockGroupToProductsToChannels(Date clearDate){
        //产品-渠道，汇总数据
        List<Map<String, Object>> stockAgeGroup = stockInvStockAgeService
                .countStockGroupToProductsToChannels(clearDate, clearDate);
        //总计-渠道，汇总数据
        List<Map<String, Object>> sumstockAgeGroup = stockInvStockAgeService
                .sumStockGroupToProductsToChannels(clearDate, clearDate);
        //总计-小计，汇总数据
        List<Map<String, Object>> totalstockAgeGroup = stockInvStockAgeService
                .totalStockGroupToProductsToChannels(clearDate, clearDate);
        //产品-小计，汇总数据
        List<Map<String, Object>> subsumstockAgeGroup = stockInvStockAgeService
                .subSumStockGroupToProductsToChannels(clearDate, clearDate);
        sumstockAgeGroup.addAll(totalstockAgeGroup);
        sumstockAgeGroup.addAll(stockAgeGroup);
        sumstockAgeGroup.addAll(subsumstockAgeGroup);

        //将总计-小计的超期库龄数量和金额保存在一个变量中，后面算超期权重时使用
        Map<String, List<StockAgeHandler>> totalstockAgeHandler = prepareReportHandler(
                totalstockAgeGroup, "product_type_name", "channel_name");
        if (totalstockAgeGroup != null && totalstockAgeGroup.size() > 0
                && totalstockAgeHandler.get("总计") != null) {
            if (totalstockAgeHandler.get("总计").get(0).getAgeDatas() != null
                    && totalstockAgeHandler.get("总计").get(0).getAgeDatas().size() > 6) {//第7个是超期库龄，取第七个
                Integer quantity = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getStockQuantity();
                BigDecimal amount = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getValue();
                totalTimeOutMap.put("stockQuantity", quantity);
                totalTimeOutMap.put("value", amount);
            }
        }
        Map<String, List<StockAgeHandler>> resultMap = prepareReportHandler(sumstockAgeGroup,
                "product_type_name", "channel_name");
        Map<String, List<StockAgeHandler>> resultMap_jt = countStockGroupToProductsToChannels_jt(clearDate);
        if (resultMap != null && resultMap_jt != null) {//将计提中的超期库龄小计*3%加到resultMap中，覆盖原来的计提损失
            for (Map.Entry entry : resultMap.entrySet()) {
                String key = entry.getKey().toString();
                List<StockAgeHandler> stockagelist = (List<StockAgeHandler>) entry.getValue();//第一分组-产品
                if (stockagelist != null && stockagelist.size() > 0) {
                    for (int i = 0; i < stockagelist.size(); i++) {
                        List<InvStockAge.StockAgeData> stockagedatalist = stockagelist.get(i).getAgeDatas();//第二分组-渠道
                        if (stockagedatalist != null && stockagedatalist.size() > 0) {
                            for (int j = 0; j < stockagedatalist.size(); j++) {
                                if (stockagedatalist.get(j).getAge() == -1003) {//第三分组-时间段
                                    //计提
                                    List<StockAgeHandler> stockagelist_jt = resultMap_jt.get(key);//第一分组-产品
                                    if (stockagelist_jt != null && stockagelist_jt.size() > 0) {
                                        for (int k = 0; k < stockagelist_jt.size(); k++) {
                                            if (stockagelist
                                                    .get(i)
                                                    .getStockAge()
                                                    .getProductGroupName()
                                                    .equals(
                                                            stockagelist_jt.get(k).getStockAge()
                                                                    .getProductGroupName())) {//第二分组-渠道
                                                List<InvStockAge.StockAgeData> stockagedatalist_jt = stockagelist_jt
                                                        .get(k).getAgeDatas();
                                                if (stockagedatalist_jt != null
                                                        && stockagedatalist_jt.size() > 0) {
                                                    for (int m = 0; m < stockagedatalist_jt.size(); m++) {
                                                        if (stockagedatalist_jt.get(m).getAge() == -1003) {//第三分组-时间段
                                                            //设置合计新的值
                                                            stockagedatalist.get(j)
                                                                    .setStockQuantity(
                                                                            stockagedatalist_jt.get(m)
                                                                                    .getStockQuantity());
                                                            stockagedatalist.get(j).setValue(
                                                                    stockagedatalist_jt.get(m)
                                                                            .getValue());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultMap;
    }
    /**
     * 预测准确率-到产品到渠道统计库龄-计提
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeHandler>> countStockGroupToProductsToChannels_jt(Date clearDate){
        Date startdate = new Date();
        startdate.setYear(clearDate.getYear());
        startdate.setMonth(clearDate.getMonth());
        startdate.setDate(1);
        startdate.setHours(clearDate.getHours());
        startdate.setMinutes(clearDate.getMinutes());
        startdate.setSeconds(clearDate.getSeconds());

        clearDate.setHours(23);
        clearDate.setMinutes(59);
        clearDate.setSeconds(59);
        //产品-渠道，汇总数据
        List<Map<String, Object>> stockAgeGroup = stockInvStockAgeService
                .countStockGroupToProductsToChannels(clearDate, clearDate);
        //总计-渠道，汇总数据
        List<Map<String, Object>> sumstockAgeGroup = stockInvStockAgeService
                .sumStockGroupToProductsToChannels(clearDate, clearDate);
        //总计-小计，汇总数据
        List<Map<String, Object>> totalstockAgeGroup = stockInvStockAgeService
                .totalStockGroupToProductsToChannels(clearDate, clearDate);
        //产品-小计，汇总数据
        List<Map<String, Object>> subsumstockAgeGroup = stockInvStockAgeService
                .subSumStockGroupToProductsToChannels(clearDate, clearDate);
        sumstockAgeGroup.addAll(totalstockAgeGroup);
        sumstockAgeGroup.addAll(stockAgeGroup);
        sumstockAgeGroup.addAll(subsumstockAgeGroup);

        //将总计-小计的超期库龄数量和金额保存在一个变量中，后面算超期权重时使用
        Map<String, List<StockAgeHandler>> totalstockAgeHandler = prepareReportHandler(
                totalstockAgeGroup, "product_type_name", "channel_name");
        if (totalstockAgeGroup != null && totalstockAgeGroup.size() > 0
                && totalstockAgeHandler.get("总计") != null) {
            if (totalstockAgeHandler.get("总计").get(0).getAgeDatas() != null
                    && totalstockAgeHandler.get("总计").get(0).getAgeDatas().size() > 6) {//第7个是超期库龄，取第七个
                Integer quantity = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getStockQuantity();
                BigDecimal amount = totalstockAgeHandler.get("总计").get(0).getAgeDatas().get(6)
                        .getValue();
                totalTimeOutMap.put("stockQuantity", quantity);
                totalTimeOutMap.put("value", amount);
            }
        }

        return prepareReportHandler(sumstockAgeGroup, "product_type_name", "channel_name");
    }
    /**
     * 按产品统计库龄
     * @param clearDate
     * @return
     */
    public Map<String, List<StockAgeWapped>> countStockGroupBySku(Date clearDate) {

        List<Map<String, Object>> stockAgeGroupSku = stockInvStockAgeService.countStockGroupBySku(clearDate);

        return prepareReportMap(stockAgeGroupSku);
    }
    /**
     * 按渠道统计指定产品组的库龄
     * @param clearDate
     * @return
     */
    public List<StockAgeWapped> countStockGroupByChannelWithSku(Date clearDate,
                                                                String productGroupName) {

        List<InvStockAgeLog> stockAgeGroupByChannel = stockinvStockAgeLogService
                .countStockGroupByChannelWhthSku(clearDate, productGroupName);

        return prepareReportChannelList(stockAgeGroupByChannel);
    }
}
