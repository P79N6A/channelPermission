package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;


/**
 * 预测准确率-库龄汇总处理
 * @return
 */
public class StockAgeHandler implements Serializable {
	
	private String     sku;
    private String     secCode;
    
    private List<Data> dataList = new LinkedList<Data>();

    /**
     * StockAgeLogs的数据是准确的，通过stockAgeLogs初始化
     * @param sku
     * @param secCode
     * @param stockAgeLogs
     */
    public StockAgeHandler(String sku, String secCode, List<InvStockAgeLog> stockAgeLogs) {
        this(sku, secCode);
        buildUpAgeDataList(stockAgeLogs);
    }
    
    /**
     * 通过StockAgeLogs初始化库龄信息
     * @param stockAgeLogs
     */
    public void buildUpAgeDataList(List<InvStockAgeLog> stockAgeLogs) {
        if (dataList.size() > 0)
            dataList.clear();
        for (InvStockAgeLog stockAgeLog : stockAgeLogs) {
            Data data = new Data(stockAgeLog.getChannelCode(), stockAgeLog.getAge(),
                stockAgeLog.getQuantity(), stockAgeLog.getWaStockQty());
            dataList.add(data);
        }
        Collections.sort(dataList);
    }
    
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = 1L;

    private InvStockAge        stockAge;

    private List<InvStockAge.StockAgeData> ageDatas;

    public StockAgeHandler(InvStockAge stockAge) {
        this.stockAge = stockAge;
    }

    public StockAgeHandler(String sku, String secCode) {
        this.sku = sku;
        this.secCode = secCode;
    }


	private static int[]  ageRanges = { 30, 60, 30 * 3, 30 * 4, -1 };

    private static int    ageEnd    = 120;

    private static double loseRate  = 0.03;                          //计提损失=超期小计  * 3%

    /**
     * 统计超期库存等
     * totalTimeOutMap：汇总超期小计-数量（stockQuantity），金额（value），算超期权重时使用
     */
    public void wappenAgeDatasForStatistics(Map<String, Object> totalTimeOutMap) {
        if (ageDatas == null)
            wappenAgeDatas();//分段统计库龄数量

        //每行-正常
        int quantityNormal = 0;
        BigDecimal valueNormal = new BigDecimal("0.0");
        //每行-超期
        int quantityExtended = 0;
        BigDecimal valueExtended = new BigDecimal("0.0");
        //每行-总计
        int quantity = 0;
        BigDecimal value = new BigDecimal("0.0");
        //全部-总计
        int quantityAll = 0;
        BigDecimal valueAll = new BigDecimal("0.0");
        if (totalTimeOutMap != null && totalTimeOutMap.get("stockQuantity") != null) {
            quantityAll = (Integer) totalTimeOutMap.get("stockQuantity");
        }
        if (totalTimeOutMap != null && totalTimeOutMap.get("value") != null) {
            valueAll = (BigDecimal) totalTimeOutMap.get("value");
        }
        //统计超期库龄和正常库龄数量和金额，总计
        for (InvStockAge.StockAgeData ageData : ageDatas) {
            int ageRange = ageData.getAge();
            if (ageRange == -1 || ageRange >= 120) {//2014-06-03 wyj修改 :old-75 new-(30*4)
                quantityExtended += ageData.getStockQuantity();
                valueExtended = valueExtended.add(ageData.getValue());
            } else if (ageRange <= 90) {//2014-06-03 wyj修改 :old-60 new-90
                quantityNormal += ageData.getStockQuantity();
                valueNormal = valueNormal.add(ageData.getValue());
            }
            quantity += ageData.getStockQuantity();
            value = value.add(ageData.getValue());
        }
        //超期占比
        int quantityBalance = 0;
        BigDecimal valueBalance = new BigDecimal("0");
        //计算超期占比
        if (quantity != 0) {
            BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(new BigDecimal(
                quantity), BigDecimal.ROUND_HALF_UP);
            quantityBalance = balance.multiply(new BigDecimal(100)).intValue();
        }

        if (value.compareTo(new BigDecimal(0)) > 0) {
            BigDecimal balance = valueExtended.divide(value, BigDecimal.ROUND_HALF_UP);
            valueBalance = balance.multiply(new BigDecimal(100));
        }
        //超期权重
        int quantityWeight = 0;
        BigDecimal valueWeight = new BigDecimal("0");
        //计算超期权重
        if (quantityAll != 0) {
            BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(new BigDecimal(
                quantityAll), BigDecimal.ROUND_HALF_UP);
            quantityWeight = balance.multiply(new BigDecimal(100)).intValue();
        }

        if (valueAll.compareTo(new BigDecimal(0)) > 0) {
            BigDecimal balance = valueExtended.divide(valueAll, BigDecimal.ROUND_HALF_UP);
            valueWeight = balance.multiply(new BigDecimal(100));
        }
        //        //正常小计
        //        StockAgeData normalAgeData = stockAge.new StockAgeData();
        //        normalAgeData.setAge(-1001);
        //        normalAgeData.setStockQuantity(quantityNormal);
        //        normalAgeData.setValue(valueNormal);
        //        ageDatas.add(normalAgeData);
        //合计
        InvStockAge.StockAgeData allAgeData = stockAge.new StockAgeData();
        allAgeData.setAge(-1001);
        allAgeData.setStockQuantity(quantity);
        allAgeData.setValue(value);
        ageDatas.add(allAgeData);
        //超期小计
        InvStockAge.StockAgeData extendedAgeData = stockAge.new StockAgeData();
        extendedAgeData.setAge(-1002);
        extendedAgeData.setStockQuantity(quantityExtended);
        extendedAgeData.setValue(valueExtended);
        ageDatas.add(extendedAgeData);
        //计提损失
        InvStockAge.StockAgeData extendedLoseAgeData = stockAge.new StockAgeData();
        extendedLoseAgeData.setAge(-1003);
        extendedLoseAgeData.setStockQuantity(quantityExtended);
        extendedLoseAgeData.setValue(valueExtended.multiply(new BigDecimal(loseRate)));
        ageDatas.add(extendedLoseAgeData);
        //超期占比
        InvStockAge.StockAgeData balanceAgeData = stockAge.new StockAgeData();
        balanceAgeData.setAge(-1004);
        balanceAgeData.setStockQuantity(quantityBalance);
        balanceAgeData.setValue(valueBalance);
        ageDatas.add(balanceAgeData);
        //超期权重
        InvStockAge.StockAgeData weightAgeData = stockAge.new StockAgeData();
        weightAgeData.setAge(-1005);
        weightAgeData.setStockQuantity(quantityWeight);
        weightAgeData.setValue(valueWeight);
        ageDatas.add(weightAgeData);
    }
    
    public List<InvStockAgeLog> buildInvStockAgeLogs(Date clearDate) {
        List<InvStockAgeLog> stockAgeLogs = new ArrayList<InvStockAgeLog>();
        for (Data data : dataList) {
            if (data.quantity <= 0)
                continue;
            InvStockAgeLog invStockAgeLog = new InvStockAgeLog();
            invStockAgeLog.setItemId(0);
            invStockAgeLog.setSku(sku);
            invStockAgeLog.setSecCode(secCode);
            invStockAgeLog.setChannelCode(data.channel);
            invStockAgeLog.setCreateTime(new Date());
            invStockAgeLog.setAge(data.age);
            invStockAgeLog.setQuantity(data.quantity);
            invStockAgeLog.setWaStockQty(data.waQty);
            BigDecimal amount = new BigDecimal("0.00");
            invStockAgeLog.setAmount(amount);
            invStockAgeLog.setDate(clearDate);
            stockAgeLogs.add(invStockAgeLog);
        }
        return stockAgeLogs;
    }
    
    /**
     * 库龄增长
     * @param increasedAge
     */
    public void riseTheAge(int increasedAge) {
        for (Data data : dataList)
            data.age += increasedAge;
    }

    //分段统计各个库龄数量
    public void wappenAgeDatas() {

        ageDatas = new ArrayList<InvStockAge.StockAgeData>();
        List<InvStockAge.StockAgeData> orgAgeDatas = stockAge.getStockAgeDatas();

        int index = 0;
        for (int ageRange : ageRanges) {
            InvStockAge.StockAgeData newAgeData = stockAge.new StockAgeData();
            newAgeData.setAge(ageRange);
            newAgeData.setStockQuantity(0);
            newAgeData.setValue(new BigDecimal(0.00));
            for (int i = index; i < orgAgeDatas.size(); i++) {
                InvStockAge.StockAgeData ageData = orgAgeDatas.get(i);
                if (ageRange == -1) {
                    if (ageData.getAge() > ageEnd) { //365
                        newAgeData.setStockQuantity(newAgeData.getStockQuantity()
                                                    + ageData.getStockQuantity());
                        newAgeData.setValue(newAgeData.getValue().add(ageData.getValue()));
                        index++;
                    }
                } else {
                    if (ageData.getAge() <= ageRange) {
                        newAgeData.setStockQuantity(newAgeData.getStockQuantity()
                                                    + ageData.getStockQuantity());
                        newAgeData.setValue(newAgeData.getValue().add(ageData.getValue()));
                        index++;
                    } else {
                        break;
                    }
                }
            }
            ageDatas.add(newAgeData);
        }
    }

    /**
     *  { 30, 60, 30 * 3, 30 * 4, -1 ,-1001,-1002,-1003,-1004}
     *  <br>
     *  否则返回-1的值
     * @param ageRange
     * @param totalTimeOutMap：汇总超期小计-数量（stockQuantity），金额（value），算超期权重时使用
     * @return
     */
    public InvStockAge.StockAgeData getAgeData(int ageRange, Map<String, Object> totalTimeOutMap) {
        InvStockAge.StockAgeData ageData = null;
        if (ageDatas == null)
            wappenAgeDatasForStatistics(totalTimeOutMap);
        switch (ageRange) {
            case -1001:
                ageData = ageDatas.get(5);
                break;
            case -1002:
                ageData = ageDatas.get(6);
                break;
            case -1003:
                ageData = ageDatas.get(7);
                break;
            case -1004:
                ageData = ageDatas.get(8);
                break;
            default:
                int idx = 0;
                for (int r : ageRanges) {
                    if (r == ageRange) {
                        ageData = ageDatas.get(idx);
                        break;
                    }
                    idx++;
                }
                break;
        }

        return ageData;
    }

    public List<InvStockAge.StockAgeData> getAgeDatas() {
        if (ageDatas == null)
            wappenAgeDatas();
        return ageDatas;
    }

    /**
     * @return Returns the stockAge
     */
    public InvStockAge getStockAge() {
        return stockAge;
    }
    //根据库龄表列表生成数据，
    public void buildUpAgeDataListByStockAge(List<InvStockAge> stockAges) {
        if (dataList.size() > 0)
            dataList.clear();
        for (InvStockAge stockAge : stockAges) {
            String channel = stockAge.getChannelCode();
            for (InvStockAge.StockAgeData ageData : stockAge.getStockAgeDatas()) {
                Data data = new Data(channel, ageData.getAge(), ageData.getStockQuantity());
                dataList.add(data);
            }
        }
        Collections.sort(dataList);
        for (InvStockAge stockAge : stockAges) {
            int waQty = stockAge.getWaStockQty();
            while (waQty > 0) {
                Data data = getMaxData(stockAge.getChannelCode());
                if (data == null)
                    break;
                int qty = data.quantity > waQty ? waQty : data.quantity;
                data.waQty += qty;
                waQty -= qty;
            }
        }
    }
    
    /**
     * 装配inv_stock_age
     * @param invStockAges
     */
    public void buildInvStockAges(List<InvStockAge> invStockAges) {
        for (InvStockAge invStockAge : invStockAges)
            buildInvStockAge(invStockAge);
    }
    
    /**
     * 装配inv_stock_age
     * @param invStockAge
     */
    public void buildInvStockAge(InvStockAge invStockAge) {
        List<InvStockAge.StockAgeData> ageDataList = new ArrayList<InvStockAge.StockAgeData>();
        List<InvStockAge.StockAgeData> waAgeDataList = new ArrayList<InvStockAge.StockAgeData>();
        InvStockAge.StockAgeData ageData;
        InvStockAge.StockAgeData waAgeData;
        int waQty = 0;
        for (Data data : dataList) {
            if (data.channel.equalsIgnoreCase(invStockAge.getChannelCode())) {
                ageData = invStockAge.new StockAgeData();
                ageData.setAge(data.age);
                ageData.setStockQuantity(data.quantity);
                ageDataList.add(ageData);
                waAgeData = invStockAge.new StockAgeData();
                waAgeData.setAge(data.age);
                waAgeData.setStockQuantity(data.waQty);
                waAgeDataList.add(waAgeData);
                waQty += data.waQty;
            }
        }

        invStockAge.setStockAgeDatas(ageDataList);
        invStockAge.setWaStockAgeDatas(waAgeDataList);
        invStockAge.setWaStockQty(waQty);

    }
    /**
     * 渠道中减少库存
     * @param channel
     * @param quantity
     * @return 不够扣减的数量
     */
    public int deducted(String channel, int quantity, boolean isWA) {
        Data data;

        for (int i = dataList.size() - 1; i >= 0; i--) {
            if (quantity <= 0)
                break;
            data = dataList.get(i);
            if (!data.channel.equalsIgnoreCase(channel))
                continue;
            if (isWA) {
                if (data.waQty > quantity) {
                    data.waQty -= quantity;
                    data.quantity -= quantity;
                    quantity = 0;
                } else {
                    quantity -= data.waQty;
                    data.quantity -= data.waQty;
                    data.waQty = 0;
                    if (data.quantity <= 0)
                        dataList.remove(i);
                }
            } else {
                if (data.quantity > quantity) {
                    data.quantity -= quantity;
                    quantity = 0;
                } else {
                    quantity -= data.quantity;
                    data.quantity = 0;
                    dataList.remove(i);
                }
                int _qty = data.waQty - data.quantity;
                if (_qty > 0) {
                    data.waQty = data.quantity;
                    increasedWaQty(channel, _qty);
                }

            }
        }
        return quantity;
    }
    
    public int increasedWaQty(String channel, int waQty) {
        for (int i = dataList.size() - 1; i >= 0; i--) {
            Data data = dataList.get(i);
            if (!data.channel.equalsIgnoreCase(channel))
                continue;
            if (waQty <= 0)
                break;

            int qty = data.quantity - data.waQty;
            if (waQty <= qty) {
                data.waQty += waQty;
                waQty = 0;
            } else {
                data.waQty = data.quantity;
                if (qty >= 0)
                    waQty -= qty;
            }
        }
        return waQty;
    }
    
    public int panOfAgeSelf(String toChannel, int quantity) {
        Data data;
        int qtyFrom = quantity;
        //先使用本渠道共享的
        for (Data aDataList : dataList) {
            if (qtyFrom <= 0)
                break;
            if (aDataList.channel.equalsIgnoreCase(toChannel)) {
                data = aDataList;
                if (data.waQty > 0) {
                    if (data.waQty > qtyFrom) {
                        data.waQty -= qtyFrom;
                        qtyFrom = 0;
                    } else {
                        qtyFrom -= data.waQty;
                        data.waQty = 0;
                    }
                }
            }
        }
        //使用其他渠道共享的
        while (qtyFrom > 0) {
            data = getMaxWAData();
            if (data == null)
                break;
            Data dataTo = getData(toChannel, data.age);
            if (dataTo == null) {
                dataTo = new Data(toChannel, data.age, 0);
                dataList.add(dataTo);
            }
            if (data.waQty > qtyFrom) {
                data.waQty -= qtyFrom;
                data.quantity -= qtyFrom;
                dataTo.quantity += qtyFrom;
                qtyFrom = 0;
            } else {
                dataTo.quantity += data.waQty;
                data.quantity -= data.waQty;
                qtyFrom -= data.waQty;
                data.waQty = 0;
                if (data.quantity <= 0)
                    dataList.remove(data);
            }
            int _qty = data.waQty - data.quantity;
            if (_qty > 0) {
                data.waQty = data.quantity;
                increasedWaQty(data.channel, _qty);
            }
        }
        Collections.sort(dataList);
        return qtyFrom;
    }

    /**
     * 根据库龄和所属渠道获取库龄数据
     * @param channel
     * @param age
     * @return
     */
    public Data getData(String channel, int age) {
        for (Data data : dataList) {
            if (data.age > age)
                break;
            if (channel.equalsIgnoreCase(data.channel) && age == data.age)
                return data;
        }
        return null;
    }
    public int panOfAge(String toChannel, int quantity, boolean isWa) {
        int qtyFrom = quantity;
        if (!isWa) {
            for (int i = dataList.size() - 1; i >= 0; i--) {
                if (qtyFrom <= 0)
                    break;
                Data data = dataList.get(i);
                if (data.channel.equals(toChannel))
                    continue;
                Data dataTo = getData(toChannel, data.age);
                if (dataTo == null) {
                    dataTo = new Data(toChannel, data.age, 0);
                    dataList.add(dataTo);
                }
                if (data.quantity > qtyFrom) {
                    data.quantity -= qtyFrom;
                    dataTo.quantity += qtyFrom;
                    qtyFrom = 0;
                } else {
                    dataTo.quantity += data.quantity;
                    data.quantity = 0;
                    qtyFrom -= data.quantity;
                    dataList.remove(i);
                }
                int _qty = data.waQty - data.quantity;
                if (_qty > 0) {
                    data.waQty = data.quantity;
                    increasedWaQty(data.channel, _qty);
                }
            }
        } else {
            for (int i = dataList.size() - 1; i >= 0; i--) {
                if (qtyFrom <= 0)
                    break;
                Data data = dataList.get(i);
                if (data.channel.equals(toChannel))
                    continue;
                Data dataTo = getData(toChannel, data.age);
                if (dataTo == null) {
                    dataTo = new Data(toChannel, data.age, 0);
                    dataList.add(dataTo);
                }
                if (data.waQty > qtyFrom) {
                    data.waQty -= qtyFrom;
                    data.quantity -= qtyFrom;
                    dataTo.quantity += qtyFrom;
                    qtyFrom = 0;
                } else {
                    dataTo.quantity += data.waQty;
                    data.quantity -= data.waQty;
                    qtyFrom -= data.waQty;
                    data.waQty = 0;
                    if (data.quantity <= 0)
                        dataList.remove(data);
                }
            }
        }
        Collections.sort(dataList);
        refreshWaQty(toChannel);
        return qtyFrom;
    }

    public void increased(String channel, int age, int quantity, int waQty) {
        if (waQty > quantity)
            waQty = quantity;
        Data data = getData(channel, age);
        if (data == null) {
            data = new Data(channel, age, quantity, waQty);
            dataList.add(data);
        } else {
            data.quantity += quantity;
            data.waQty += waQty;
        }
        Collections.sort(dataList);
    }
    public int panOfAge(String fromChannel, String toChannel, int quantity, boolean isWa) {
        Data data;
        int qtyFrom = quantity;
        if (!isWa) {
            while (qtyFrom > 0) {
                data = getMaxData(fromChannel);
                if (data == null)
                    break;
                Data dataTo = getData(toChannel, data.age);
                if (dataTo == null) {
                    dataTo = new Data(toChannel, data.age, 0);
                    dataList.add(dataTo);
                }
                if (data.quantity > qtyFrom) {
                    data.quantity -= qtyFrom;
                    dataTo.quantity += qtyFrom;
                    qtyFrom = 0;
                } else {
                    dataTo.quantity += data.quantity;
                    qtyFrom -= data.quantity;
                    data.quantity = 0;
                    dataList.remove(data);
                }
                int _qty = data.waQty - data.quantity;
                if (_qty > 0) {
                    data.waQty = data.quantity;
                    increasedWaQty(data.channel, _qty);
                }
            }
        } else {
            while (qtyFrom > 0) {
                data = getMaxWAData(fromChannel);
                if (data == null)
                    break;
                Data dataTo = getData(toChannel, data.age);
                if (dataTo == null) {
                    dataTo = new Data(toChannel, data.age, 0);
                    dataList.add(dataTo);
                }
                if (data.waQty > qtyFrom) {
                    data.waQty -= qtyFrom;
                    data.quantity -= qtyFrom;
                    dataTo.quantity += qtyFrom;
                    qtyFrom = 0;
                } else {
                    dataTo.quantity += data.waQty;
                    data.quantity -= data.waQty;
                    qtyFrom -= data.waQty;
                    data.waQty = 0;
                    if (data.quantity <= 0)
                        dataList.remove(data);
                }
            }
        }
        Collections.sort(dataList);
        refreshWaQty(toChannel);
        return qtyFrom;
    }
    public void refreshWaQty(String channel) {
        int qty = 0;
        for (Data data : dataList) {
            if (data.channel.equalsIgnoreCase(channel)) {
                qty += data.waQty;
                data.waQty = 0;
            }
        }
        for (int i = dataList.size() - 1; i >= 0; i--) {
            if (qty <= 0)
                break;
            Data data = dataList.get(i);
            if (!data.channel.equals(channel))
                continue;
            if (data.quantity >= qty) {
                data.waQty = qty;
                qty = 0;
            } else {
                data.waQty = data.quantity;
                qty -= data.quantity;
            }
        }
    }

    private Data getMaxWAData(String channel) {
        Data data;
        for (int i = dataList.size() - 1; i >= 0; i--) {
            data = dataList.get(i);
            if (data.channel.equals(channel) && data.waQty > 0)
                return data;
        }
        return null;
    }
    private Data getMaxWAData() {
        Data data;
        for (int i = dataList.size() - 1; i >= 0; i--) {
            data = dataList.get(i);
            if (data.waQty > 0)
                return data;
        }
        return null;
    }
    
    /**
     * 减少库存
     * @param quantity
     * @return 不够扣减的数量
     */
    public int deducted(int quantity, boolean isWA) {
        Data data;
        int index;

        if (!isWA) {
            while (quantity > 0 && !dataList.isEmpty()) {
                index = dataList.size() - 1;
                data = dataList.get(index);
                if (data.quantity > quantity) {
                    data.quantity -= quantity;
                    quantity = 0;
                } else {
                    quantity -= data.quantity;
                    data.quantity = 0;
                    dataList.remove(index);
                }
                int _qty = data.waQty - data.quantity;
                if (_qty > 0) {
                    data.waQty = data.quantity;
                    increasedWaQty(data.channel, _qty);
                }
            }
        } else {
            for (index = dataList.size() - 1; index >= 0; index--) {
                if (quantity <= 0)
                    break;
                data = dataList.get(index);
                if (data.waQty > quantity) {
                    data.waQty -= quantity;
                    data.quantity -= quantity;
                    quantity = 0;
                } else {
                    quantity -= data.waQty;
                    data.quantity -= data.waQty;
                    data.waQty = 0;
                    if (data.quantity <= 0)
                        dataList.remove(index);
                }
            }
        }

        return quantity;
    }
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public List<Data> getDataList() {
        return dataList;
    }
    
    /**
     * 获取所属渠道的最大库龄信息
     * @param channel
     * @return
     */
    public Data getMaxData(String channel) {
        for (int i = dataList.size() - 1; i >= 0; i--)
            if (dataList.get(i).channel.equalsIgnoreCase(channel))
                return dataList.get(i);
        return null;
    }
    
    public class Data implements Comparable<Data> {
        /**
         * 渠道
         */
        public String  channel;
        /**
         * 库龄
         */
        public Integer age;
        /**
         * 数量
         */
        public Integer quantity;

        public Integer waQty;

        public Data(String channel, int age, int quantity) {
            this.channel = channel;
            this.age = age;
            this.quantity = quantity;
            this.waQty = 0;
        }

        public Data(String channel, int age, int quantity, int waQty) {
            this(channel, age, quantity);
            this.waQty = waQty;
        }

        @Override
        public int compareTo(Data o) {
            return age.compareTo(o.age);
        }

        @Override
        public String toString() {
            return "DATA{" + channel + "," + age + "," + quantity + "," + waQty + "}";
        }
    }
}
