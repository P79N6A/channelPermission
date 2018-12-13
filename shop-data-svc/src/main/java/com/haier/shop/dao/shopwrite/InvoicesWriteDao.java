package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Invoices;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface InvoicesWriteDao {


    Integer updateInvoices(Invoices invoices);

    /**
     * 电子发票批量重开
     * @param params
     * @return
     */
    int eInvoiceBatchInvalid(Map<String, Object> params);

    /**
     * 电子发票批量重开
     * @param params
     * @return
     */
    int eInvoiceBatchReBilling(Map<String, Object> params);

    /**
     * 修改发票信息
     * @param invoice
     */
    int update(Invoices invoice);

    /**
     * 创建发票信息
     * @param invoice
     */
    int insertInvoice(Invoices invoice);

    /**
     * 发票批量修改
     * @param params
     * @return
     */
    int invoiceBatchModify(Map<String, Object> params);


    int deleteByPrimaryKey(Integer id);

    int insertSelective(Invoices record);

    int updateByPrimaryKeySelective(Invoices record);

    int updateByPrimaryKeyWithBLOBs(Invoices record);

    int updateByPrimaryKey(Invoices record);
}
