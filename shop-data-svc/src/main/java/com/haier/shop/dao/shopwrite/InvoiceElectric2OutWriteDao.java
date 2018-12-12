package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceElectric2Out;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectric2OutWriteDao {

    /**
     * 创建电子发票同步外部列表
     * @param invoiceElectric2Out
     */
    int insert(InvoiceElectric2Out invoiceElectric2Out);
    
    /**
     * 同步发票后更新表
     * @return
     */
    Integer updateAfterSync(InvoiceElectric2Out invoiceElectric2Out);
}
