package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/15 13:17
 */
public class TianMaoDataStatisticsVo implements Serializable {
    private static final long serialVersionUID = -8582599429762833215L;

    private String     typeName;
    private BigDecimal money;
    private int     countNumber;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }
}
