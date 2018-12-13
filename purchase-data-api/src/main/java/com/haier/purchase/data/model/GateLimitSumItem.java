package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author 刘骥飞
 * @date 2014-8-18
 * @email jifei.liu@dhc.com.cn
 */
public class GateLimitSumItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7327783337319341946L;
    private BigDecimal        limit_sum_num;                           //总额度
    private Integer           month;                                   //月份
    private Integer           use_flag;                                //使用标识

    /**
     * @return Returns the limit_sum_num
     */
    public BigDecimal getLimit_sum_num() {
        return limit_sum_num;
    }

    /**
     * @param limit_sum_num
     * The limit_sum_num to set.
     */
    public void setLimit_sum_num(BigDecimal limit_sum_num) {
        this.limit_sum_num = limit_sum_num;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @param month
     * The modify_time to set.
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return Returns the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return Returns the use_flag
     */
    public Integer getUse_flag() {
        return use_flag;
    }

    /**
     * @param use_flag
     * The  to set.
     */
    public void setUse_flag(Integer use_flag) {
        this.use_flag = use_flag;
    }

}