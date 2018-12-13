package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvTransferLineSales;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvTransferLineSalesDao {
    void insertSalse(InvTransferLineSales invTransferLineSales);

    /**
     * 更新inv_transfer_line_sales 表中invoiceState=1（开票中）
     * @param invoiceState
     */
    void updateInvoiceState(int invoiceState,Integer id);
}
