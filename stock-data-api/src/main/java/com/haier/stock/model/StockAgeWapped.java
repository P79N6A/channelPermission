package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.haier.stock.model.InvStockAge.StockAgeData;



public class StockAgeWapped implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = 1L;

    private InvStockAge        stockAge;

    private List<StockAgeData> ageDatas;

    public StockAgeWapped(InvStockAge invStockAge) {
        this.stockAge = invStockAge;
    }

    private static int[] ageRanges = { 7, 14, 21, 30, 44, 60, 75, 90, 30 * 4, 30 * 6, 365, -1 };

    /**
     * 统计超期库存等
     */
    public void wappenAgeDatasForStatistics() {
        if (ageDatas == null)
            wappenAgeDatas();

        //正常
        int quantityNormal = 0;
        BigDecimal valueNormal = new BigDecimal("0.0");
        //超期
        int quantityExtended = 0;
        BigDecimal valueExtended = new BigDecimal("0.0");
        //总计
        int quantity = 0;
        BigDecimal value = new BigDecimal("0.0");

        for (StockAgeData ageData : ageDatas) {
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

        int quantityBalance = 0;
        BigDecimal valueBalance = new BigDecimal("0");

        if (quantity != 0) {
            BigDecimal balance = new BigDecimal(quantityExtended + ".00").divide(new BigDecimal(
                quantity), BigDecimal.ROUND_HALF_UP);
            quantityBalance = balance.multiply(new BigDecimal(100)).intValue();
        }

        if (value.compareTo(new BigDecimal(0)) > 0) {
            BigDecimal balance = valueExtended.divide(value, BigDecimal.ROUND_HALF_UP);
            valueBalance = balance.multiply(new BigDecimal(100));
        }

        StockAgeData normalAgeData = stockAge.new StockAgeData();
        normalAgeData.setAge(-1001);
        normalAgeData.setStockQuantity(quantityNormal);
        normalAgeData.setValue(valueNormal);
        ageDatas.add(normalAgeData);
        StockAgeData extendedAgeData = stockAge.new StockAgeData();
        extendedAgeData.setAge(-1002);
        extendedAgeData.setStockQuantity(quantityExtended);
        extendedAgeData.setValue(valueExtended);
        ageDatas.add(extendedAgeData);
        StockAgeData allAgeData = stockAge.new StockAgeData();
        allAgeData.setAge(-1003);
        allAgeData.setStockQuantity(quantity);
        allAgeData.setValue(value);
        ageDatas.add(allAgeData);
        StockAgeData balanceAgeData = stockAge.new StockAgeData();
        balanceAgeData.setAge(-1004);
        balanceAgeData.setStockQuantity(quantityBalance);
        balanceAgeData.setValue(valueBalance);
        ageDatas.add(balanceAgeData);
    }

    public void wappenAgeDatas() {

        ageDatas = new ArrayList<InvStockAge.StockAgeData>();
        List<StockAgeData> orgAgeDatas = stockAge.getStockAgeDatas();

        int index = 0;
        for (int ageRange : ageRanges) {
            StockAgeData newAgeData = stockAge.new StockAgeData();
            newAgeData.setAge(ageRange);
            newAgeData.setStockQuantity(0);
            newAgeData.setValue(new BigDecimal(0.00));
            for (int i = index; i < orgAgeDatas.size(); i++) {
                StockAgeData ageData = orgAgeDatas.get(i);
                if (ageRange == -1) {
                    if (ageData.getAge() > 365) {
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
     *  { 7, 14, 21, 30, 44, 60, 75, 90, 30 * 4, 30 * 6, 365, -1 ,-1001,-1002,-1003,-1004}
     *  <br>
     *  否则返回-1的值
     * @param ageRange
     * @return
     */
    public StockAgeData getAgeData(int ageRange) {
        StockAgeData ageData = null;
        if (ageDatas == null)
            wappenAgeDatasForStatistics();
        switch (ageRange) {
            case -1001:
                ageData = ageDatas.get(12);
                break;
            case -1002:
                ageData = ageDatas.get(13);
                break;
            case -1003:
                ageData = ageDatas.get(14);
                break;
            case -1004:
                ageData = ageDatas.get(15);
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

    public List<StockAgeData> getAgeDatas() {
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

    public static void main(String[] args) {

        //定义一个数字格式化对象，格式化模板为".##"，即保留2位小数.
        DecimalFormat a = new DecimalFormat(".##");
        String s = a.format(333.335);
        System.err.println(s);
        //说明：如果小数点后面不够2位小数，不会补零.

        //可以在运行时刻用函数applyPattern(String)修改格式模板
        //保留2位小数，如果小数点后面不够2位小数会补零
        a.applyPattern(".00");
        s = a.format(333.3);
        System.err.println(s);

        //添加千分号
        a.applyPattern(".##\u2030");
        s = a.format(0.78934);
        System.err.println(s);//添加千位符后,小数会进三位并加上千位符

        //添加百分号
        a.applyPattern("#.##%");
        s = a.format(0.78645);
        System.err.println(s);

        //添加前、后修饰字符串，记得要用单引号括起来
        a.applyPattern("'这是我的钱$',###.###'美圆'");
        s = a.format(33333443.3333);
        System.err.println(s);

        //添加货币表示符号(不同的国家，添加的符号不一样
        a.applyPattern("\u00A4");
        s = a.format(34);
        System.err.println(s);

        //定义正负数模板,记得要用分号隔开
        a.applyPattern("0.0;'@'-#.0");
        s = a.format(33);
        System.err.println(s);
        s = a.format(-33);
        System.err.println(s);

        //综合运用，正负数的不同前后缀
        String pattern = "###,###.##";
        a.applyPattern(pattern);
        System.out.println(a.format(11111111111133.456));
    }

}
