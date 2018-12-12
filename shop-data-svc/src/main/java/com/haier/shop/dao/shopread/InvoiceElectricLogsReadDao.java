package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceElectricLogDispItem;
import com.haier.shop.model.InvoiceElectricLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectricLogsReadDao {

    /**
     * 分页查询电子发票信息列表，用于前台显示
     * @param paramMap
     * @return
     */
    List<InvoiceElectricLogDispItem> getElectronicInvoiceLogsList(Map<String, Object> paramMap);

    /**
     * 获得记录数（带限制）
     * @return
     */
    int getRowCnts();

    /**
     * 查询电子发票日志
     * @param invoiceId
     * @param type
     */
    InvoiceElectricLogs getByInvoiceIdAndType(@Param("invoiceId") Integer invoiceId,
                                              @Param("type") Integer type);

    /**
     * 查询电子发票日志列表
     * @param invoiceId
     * @param type
     */
    List<InvoiceElectricLogs> getByInvoiceIdAndTypeList(@Param("invoiceId") Integer invoiceId,
                                                        @Param("type") Integer type);
}
