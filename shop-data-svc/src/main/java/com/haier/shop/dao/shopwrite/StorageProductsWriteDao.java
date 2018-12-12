package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.StorageProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageProductsWriteDao {

    /**
     * 新增库存
     * @param storageProduct 库存对象
     * @return 库存id
     */
    Integer insert(StorageProducts storageProduct);

    /**
     * LES库存变化后，更新CBS库存，并且释放冻结库存
     * @param storageProduct
     * @return
     */
    Integer updateAfterLesChanged(StorageProducts storageProduct);

    /**
     * 更新库存
     * @param storageProduct 库存对象
     * @return
     */
    Integer updateStockQty(StorageProducts storageProduct);

    /**
     * 释放冻结库存
     * @param id 库存id
     * @param releaseQty 释放数量
     * @return
     */
    Integer releaseFrozenQty(@Param("id") Integer id, @Param("releaseQty") Integer releaseQty);

    /**
     * 释放冻结库存 - 日日顺库存专用
     * @param id 库存id
     * @param releaseQty 释放数量
     * @return
     */
    Integer releaseFrozenQtyForRRS(@Param("id") Integer id, @Param("releaseQty") Integer releaseQty);

    Integer frozenStock(@Param("id") Integer id, @Param("forzen_qty") Integer frozenQty);

    /**
     * 更改企业预留数
     * @param id
     * @param locksQY 增加预留数用正数，扣减用负数
     * @return
     */
    Integer updateLocksQY(@Param("id") Integer id, @Param("locksQY") Integer locksQY);
}
