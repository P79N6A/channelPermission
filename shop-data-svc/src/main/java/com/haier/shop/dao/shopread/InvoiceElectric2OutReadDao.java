package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceElectric2Out;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectric2OutReadDao {

    /**
     * 根据发票ID（invoice_id）和业务类型（send_type），获取电子发票队列对象
     * @param invoiceElectric2Out
     * @return
     */
    List<InvoiceElectric2Out> getByInvoiceIdAndSendType(InvoiceElectric2Out invoiceElectric2Out);

    /**
     * 根据ID，获取电子发票队列对象
     * @param id
     * @return
     */
    InvoiceElectric2Out get(Integer id);

    /**
     * 获取待同步HP系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<InvoiceElectric2Out> getSendToHpList(Integer topx);

    /**
     * 获取待同步SAP系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<InvoiceElectric2Out> getSendToSapList(Integer topx);
}
