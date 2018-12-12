package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderProducts;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrderProductsWriteDao {

    Integer updateForsyncInvoice(OrderProducts orderProduct);

    Integer updateMakeReceiptType(OrderProducts orderProduct);
}
