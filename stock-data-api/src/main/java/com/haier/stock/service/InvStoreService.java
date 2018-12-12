package com.haier.stock.service;

import com.haier.stock.model.InvStore;

import java.util.Date;
import java.util.List;

public interface InvStoreService {

    public InvStore getForUpdate(String sku, String storeCode,
                                 String itemProperty);

    public InvStore getByStoreCodeAndSku(  String sku,
                                        String storeCode,
                                          String itemProperty);

    public List<InvStore> getByStoreCodesAndSku(  String sku,
                                                String storeCodes,
                                                  String itemProperty);

    List<InvStore> getChangedStockQty( Date beginTime);

    public List<InvStore> getByStoreCode(  String storeCodes,
                                        String itemProperty);

    public Integer insert(InvStore invStore);

    public Integer updateQty(InvStore invStore);

    public Integer updateInvStore(InvStore invStore);

    public List<InvStore> getEStoreBySkuAndStoreCodeList(  String sku,
                                                         String storeCode,
                                                           String itemProperty);

    public List<InvStore> getEStoreBySkuListAndStoreCode( String sku,
                                                           String storeCode,
                                                           String itemProperty);

}
