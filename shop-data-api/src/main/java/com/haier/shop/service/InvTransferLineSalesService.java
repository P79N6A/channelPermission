package com.haier.shop.service;

import com.haier.shop.model.InvTransferLineSales;
import com.haier.shop.model.InvTransferLineSalesVo;

import java.util.List;

public interface InvTransferLineSalesService {
    void insertSalse(InvTransferLineSales invTransferLineSales);

    /**
     * 分页查询 InvTransferLineSales
     * @param vo
     * @param start
     * @param rows
     * @return
     */
    List<InvTransferLineSales> findListByVo(InvTransferLineSalesVo vo, int start, int rows);

    /**
     * 分页查询计算总数据
     * @param vo
     * @return
     */
    int getPagerCountS(InvTransferLineSalesVo vo);

    /**
     * 导出excel
     * @param vo
     * @return
     */
    List<InvTransferLineSales> exportListByVo(InvTransferLineSalesVo vo);

    /**
     * 通过调拨网单号码查询
     * @param
     * @return
     */
    InvTransferLineSales getByLine__num(String line_num);

    /**
     * 更新inv_transfer_line_sales 表中invoiceState=1（开票中）
     * @param invoiceState
     */
    void updateInvoiceState(int invoiceState,Integer id);
}