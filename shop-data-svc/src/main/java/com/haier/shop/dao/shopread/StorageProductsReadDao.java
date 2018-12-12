package com.haier.shop.dao.shopread;

import com.haier.shop.model.StorageProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageProductsReadDao {
    /**
     * 获取库存对象
     * @param sCode 库位
     * @param sku 物料编码
     * @return
     */
    StorageProducts getBySCodeAndSku(@Param("sCode") String sCode, @Param("sku") String sku);

    /**
     * 获取库存对象并锁定
     * @param sCode
     * @param sku
     * @return
     */
    StorageProducts getBySCodeAndSkuForLock(@Param("sCode") String sCode, @Param("sku") String sku);

    /**
     * 根据sku和库位列表，批量获取库存
     * @param sku
     * @param scodeList
     * @return
     */
    List<StorageProducts> getBySkuAndScodeList(@Param("sku") String sku,
                                               @Param("scodeList") List<String> scodeList);

    /**
     * 根据库位列表，批量获取库存(只包含可用库存大于零的)
     * @param scodeList
     * @return
     */
    List<StorageProducts> getByScodeList(@Param("scodeList") List<String> scodeList);

    /**
     * 取得所有有效的锁定记录
     * 有效：库位、sku非空，商城、淘宝、企业字段有大于0的数据
     * @return
     */
    List<StorageProducts> getAllEffectiveLocks();

}
