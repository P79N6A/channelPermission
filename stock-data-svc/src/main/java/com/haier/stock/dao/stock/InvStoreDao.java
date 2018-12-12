package com.haier.stock.dao.stock;

import java.util.Date;
import java.util.List;

import com.haier.stock.model.InvStore;
import org.apache.ibatis.annotations.Param;


public interface InvStoreDao {

    public InvStore getForUpdate(@Param("sku") String sku, @Param("storeCode") String storeCode,
                                 @Param("itemProperty") String itemProperty);

    public InvStore getByStoreCodeAndSku(@Param("sku") String sku,
                                         @Param("storeCode") String storeCode,
                                         @Param("itemProperty") String itemProperty);

    public List<InvStore> getByStoreCodesAndSku(@Param("sku") String sku,
                                                @Param("storeCodeList") String storeCodes,
                                                @Param("itemProperty") String itemProperty);

    List<InvStore> getChangedStockQty(@Param("beginTime") Date beginTime);

    public List<InvStore> getByStoreCode(@Param("storeCodes") String storeCodes,
                                         @Param("itemProperty") String itemProperty);

    public Integer insert(InvStore invStore);

    public Integer updateQty(InvStore invStore);

    public Integer updateInvStore(InvStore invStore);

    public List<InvStore> getEStoreBySkuAndStoreCodeList(@Param("sku") String sku,
                                                         @Param("storeCode") String storeCode,
                                                         @Param("itemProperty") String itemProperty);

    public List<InvStore> getEStoreBySkuListAndStoreCode(@Param("sku") String sku,
                                                         @Param("storeCode") String storeCode,
                                                         @Param("itemProperty") String itemProperty);
    List<InvStore> getStorePageByCondition(@Param("store") InvStore condition, @Param("start")int start, @Param("rows")int pageSize);

    long getStorePagerCount(@Param("store")InvStore condition);
}
