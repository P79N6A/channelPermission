package com.haier.stock.dao.stock;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvStockInOut;

import java.util.Date;
import java.util.List;

public interface InvStockInOutDao {

    /**
     * 获取指定时间段的出库数
     * @param fromDate
     * @param toDate
     * @return
     */
    public Integer getOutStock(@Param("sku") String sku, @Param("sCode") String sCode,
                               @Param("channel") String channel, @Param("fromDate") Date fromDate,
                               @Param("toDate") Date toDate);

    /**
     * 获取指定时间段的入库数
     * @param sku
     * @param sCode
     * @param channel
     * @param fromDate
     * @param toDate
     * @return
     */
    public Integer getInStock(@Param("sku") String sku, @Param("sCode") String sCode,
                              @Param("channel") String channel, @Param("fromDate") Date fromDate,
                              @Param("toDate") Date toDate);

    /**
     * 插入
     * @param invStockInOut
     * @return
     */
    public Integer insert(InvStockInOut invStockInOut);

    /**
     * 获取billNo对应的记录数
     * @param billNo
     * @return
     */
    public Integer getCountByBillNo(String billNo);

    /**
     * 获取关联的记录
     * @param sku
     * @param secCode
     * @param from
     * @param to
     * @return
     */
    public List<InvStockInOut> getReference(@Param("sku") String sku,
                                            @Param("sec_code") String secCode,
                                            @Param("from") Date from, @Param("to") Date to);

    public List<InvStockInOut> getByAgeStatus(@Param("status") Integer ageStatus);

    public Integer updateAgeStatus(@Param("id") Integer id, @Param("status") Integer status,
                                   @Param("old_status") Integer oldStatus);

}
