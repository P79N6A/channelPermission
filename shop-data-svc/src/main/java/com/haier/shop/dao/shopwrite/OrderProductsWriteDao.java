package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrderProductsWriteDao {

    Integer updateForsyncInvoice(OrderProducts orderProduct);

    Integer updateMakeReceiptType(OrderProducts orderProduct);
    Integer updateCorderSnById2(@Param(value="oPid") int oPid, @Param(value="cOrderSn")String cOrderSn);

    Integer updateOrderProductsPriceAndProductAmount(Map<String,Object> params);


}
