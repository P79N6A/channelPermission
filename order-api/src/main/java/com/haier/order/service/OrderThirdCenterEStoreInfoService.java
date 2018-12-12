//package com.haier.orderthird.service;
//
//import java.util.List;
//
//import com.haier.common.ServiceResult;
//import com.haier.shop.model.EStoreStock;
//
//public interface OrderThirdCenterEStoreInfoService {
//
//    /**
//     * 由店铺码、物料编码查询EC库存
//     * @param storeCode
//     * @param sku
//     * @return
//     */
//    ServiceResult<List<EStoreStock>> getEStoreStock(String storeCode, String sku,
//                                                    String itemProperty);
//
//    /**
//     * 按照门店，物料，批次编码查询
//     * @param storeCode
//     * @param sku
//     * @param itemProperty 批次编号 暂时只支持10
//     * @return
//     */
//    ServiceResult<EStoreStock> getByStoreCode(String storeCode, String sku, String itemProperty);
//}
