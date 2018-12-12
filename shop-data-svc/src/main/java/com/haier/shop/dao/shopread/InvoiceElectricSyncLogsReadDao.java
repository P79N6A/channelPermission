package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceElectricSyncLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectricSyncLogsReadDao {

    /**
     * 根据网单号，获取电子发票同步日志对象
     * @param cOrderSn
     * @return
     */
    List<InvoiceElectricSyncLogs> getByCOrderSn(String cOrderSn);

    List<InvoiceElectricSyncLogs> getByCOrderSnAngStatusType(@Param("cOrderSn") String cOrderSn,
                                                             @Param("statusType") Integer statusType);
}
