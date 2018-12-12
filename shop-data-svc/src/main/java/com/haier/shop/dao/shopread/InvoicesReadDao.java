package com.haier.shop.dao.shopread;

import com.haier.shop.model.Invoices;
import com.haier.shop.model.InvoicesDispItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InvoicesReadDao {
    Invoices getInvoicesByCOrderSn (@Param("cOrderSn") String cOrderSn);

    Invoices getById(@Param("id") Integer id);

    List<InvoicesDispItem> getInvoiceMakeOutList(Map<String, Object> paramMap);

    int getRowCnts();

    List<Map<String, Object>> getExportInvoiceMakeOutList(Map<String, Object> paramMap);

    Invoices getUsableByOrderProductId(@Param("id") Integer id);

    Invoices selectByPrimaryKey(Integer id);

    List<Invoices> getForwardAdjust(@Param("startTime") long startTime, @Param("endTime") long endTime);

    List<Invoices> getNegativeAdjust(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 根据网单号，获取发票信息列表
     * @param opId
     * @return
     */
    List<Invoices> getByOrderProductId(Integer opId);

    /**
     * 获取待同步开票系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<Invoices> getSyncInvoiceList(Integer topx);

    /**
     * 根据网单号查询发票
     * @param cOrderSn
     * @return
     */
    Invoices getByCorderSn(String cOrderSn);

    /**
     * 根据网单号查询发票部分信息
     * @param cOrderSn
     * @return
     */
    Map<String, Object> searchInvoicesInfoByCOrderSn(String cOrderSn);

    /**
     * 获取待同步作废发票列表
     * @param topx 取数条数
     * @return
     */
    List<Invoices> getInvoiceInvalidNotEndList(Integer topx);

    /**
     * 根据发票id，查询发票信息包含网单信息
     * @param id
     * @return
     */
    List<InvoicesDispItem> showInvoiceInfo(Integer id);

}
