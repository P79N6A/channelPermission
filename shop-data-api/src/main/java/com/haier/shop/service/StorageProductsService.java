package com.haier.shop.service;



import java.util.List;

import com.haier.shop.model.StorageProducts;

public interface StorageProductsService {
    /**
     * 获取库存对象
     * @param sCode 库位
     * @param sku 物料编码
     * @return
     */
    StorageProducts getBySCodeAndSku(  String sCode,   String sku);

    /**
     * 获取库存对象并锁定
     * @param sCode
     * @param sku
     * @return
     */
    StorageProducts getBySCodeAndSkuForLock(  String sCode,   String sku);

    /**
     * 根据sku和库位列表，批量获取库存
     * @param sku
     * @param scodeList
     * @return
     */
    List<StorageProducts> getBySkuAndScodeList( String sku,
                                                 List<String> scodeList);

    /**
     * 根据库位列表，批量获取库存(只包含可用库存大于零的)
     * @param scodeList
     * @return
     */
    List<StorageProducts> getByScodeList( List<String> scodeList);

    /**
     * 取得所有有效的锁定记录
     * 有效：库位、sku非空，商城、淘宝、企业字段有大于0的数据
     * @return
     */
    List<StorageProducts> getAllEffectiveLocks();

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
    Integer releaseFrozenQty( Integer id,  Integer releaseQty);

    /**
     * 释放冻结库存 - 日日顺库存专用
     * @param id 库存id
     * @param releaseQty 释放数量
     * @return
     */
    Integer releaseFrozenQtyForRRS( Integer id,  Integer releaseQty);

    Integer frozenStock(  Integer id,  Integer frozenQty);

    /**
     * 更改企业预留数
     * @param id
     * @param locksQY 增加预留数用正数，扣减用负数
     * @return
     */
    Integer updateLocksQY( Integer id,  Integer locksQY);
}
