package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceApiLogDispItem;
import com.haier.shop.model.InvoiceApiLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceApiLogsReadDao {

    /**
     * 获得条数
     *
     * @return
     */
    public int getRowCnts();

    /**
     * 开票日志列表
     * @param paramMap
     * @return
     */
    List<InvoiceApiLogDispItem> getInvoiceApiLogs(Map<String, Object> paramMap);

    /**
     * 查询开票日志信息
     * @param id
     */
    InvoiceApiLogs get(Integer id);

    /**
     * 查询开票日志信息
     * @param invoiceId
     * @param type
     */
    InvoiceApiLogs getByInvoiceIdAndType(@Param("invoiceId") Integer invoiceId,
                                         @Param("type") Integer type);
}
