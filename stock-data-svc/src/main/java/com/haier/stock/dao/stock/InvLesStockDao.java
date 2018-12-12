package com.haier.stock.dao.stock;

import com.haier.stock.model.InvLesStock;
import org.apache.ibatis.annotations.Param;



public interface InvLesStockDao {

    /**
     * 根据逻辑主键，获取库存对象
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvLesStock getBySecCodeAndSku(@Param("secCode") String secCode, @Param("sku") String sku);

    Integer insert(InvLesStock stock);

    Integer update(InvLesStock stock);
}
