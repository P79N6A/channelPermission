//package com.haier.orderthird.service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import com.haier.common.ServiceResult;
//import com.haier.shop.model.BigStoragesRela;
//import com.haier.shop.model.StockChangeQueue;
//import com.haier.shop.model.StorageProducts;
//import com.haier.shop.model.Storages;
//import com.haier.stock.model.InvDbmBase;
//import com.haier.stock.model.InvDbmBaseSendAddress;
//import com.haier.stock.model.InvMachineSet;
//import com.haier.stock.model.InvStock;
//import com.haier.stock.model.InvStock2Channel;
//import com.haier.stock.model.InvStockBatch;
//import com.haier.stock.model.InvStockTransaction;
//import com.haier.stock.model.InventoryBusinessTypes;
//import com.haier.store.model.SgFlagShipStoreAuthorization;
//
///**
// * 库存服务，库存更新和占用
// */
//public interface OrderThirdCenterStockService {
//    void hello();
//
//    /**
//     * CBS库存接口--入库接口（正品，非正品等入库）
//     * @param sku 实际入库或出库物料编码
//     * @param lesSecCode LES对应的库位编号
//     * @param channelCode 渠道 例：SC商城渠道， TB天猫渠道，DKH大客户渠道，JD京东渠道， YX易迅渠道
//     * @param itemProperty 批次， 例：10正品，21不良品，40样品，22开箱正品
//     * @param qty 数量
//     * @param bizType 业务类型
//     * @param mark 借贷标记
//     * @param refNo 单号
//     * @param isFrozeStock 是否冻结库存
//     * @param mainSku 暂无用
//     * @return
//     */
//    @Deprecated
//    ServiceResult<Boolean> updateCbsStockByInventoryBusiness(String sku, String lesSecCode,
//                                                             String channelCode,
//                                                             String itemProperty, Integer qty,
//                                                             InventoryBusinessTypes bizType,
//                                                             String mark, String refNo,
//                                                             boolean isFrozeStock, String mainSku);
//
//    /**
//     * CBS库存接口--库存交易记录(inv_stock_transaction)
//     *              更新基础库存(inv_base_stock)和销售库存(inv_stock)
//     * @param stockTransaction
//     * @return
//     */
//    ServiceResult<Boolean> updateCbsStockByStockTransaction(InvStockTransaction stockTransaction);
//
//    /**
//     * CBS库存接口--日日顺库存接入
//     * @param sku 物料编码
//     * @param secCode 库位
//     * @param channelCode 渠道
//     * @param newQty 数量
//     * @return
//     */
//    ServiceResult<Boolean> updateLesStock(String sku, String secCode, String channelCode,
//                                          Integer newQty);
//
//    /**
//     * CBS库存接口--基地库存接入
//     * @param sku 物料编码
//     * @param secCode 库位
//     * @param qty 数量
//     * @return
//     */
//    ServiceResult<Boolean> updateJiDiStock(String sku, String secCode, Integer qty);
//
//    /**
//     * CBS库存接口--单库位（虚拟库位）冻结
//     * @param sku 实际冻结物料  可包括套机
//     * @param secCode 库位编码 虚拟库位编码
//     * @param refNo 网单编号
//     * @param frozenQty 冻结数量
//     * @param billType 交易类型
//     * @param optUser 操作人
//     * @return
//     */
//    ServiceResult<Boolean> frozeStockQty(String sku, String secCode, String refNo,
//                                         Integer frozenQty, InventoryBusinessTypes billType,
//                                         String optUser);
//
//    /**
//     * CBS库存接口--冻结库存出库
//     * @param sku 物料编码
//     * @param refNo 单号
//     * @param outQty 出库数量
//     * @param billType 交易类型
//     * @param optUser 操作人
//     * @return
//     */
//    ServiceResult<Boolean> outFrozenStockQty(String sku, String refNo, Integer outQty,
//                                             InventoryBusinessTypes billType, String optUser);
//
//    /**
//     * CBS库存接口--释放库存占用
//    * @param sku 实际物料编号
//     * @param refNo 网单号
//     * @param releaseQty 释放数量
//     * @param billType 交易类型
//     * @return
//     */
//    ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo, Integer releaseQty,
//                                                 InventoryBusinessTypes billType);
//
//    /**
//     * CBS库存接口--释放库存占用（带操作用户）
//     * @param sku 实际物料编号
//     * @param refNo 网单号
//     * @param releaseQty 释放数量
//     * @param billType 交易类型
//     * @param optUser 操作人
//     * @return
//     */
//    ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo, Integer releaseQty,
//                                                 InventoryBusinessTypes billType, String optUser);
//
//    /**
//     * CBS库存接口--开提单重新分配库位
//     * @param sku 实际物料编码
//     * @param secCode 库位编码
//     * @param refNo 网单编号
//     * @param source 订单来源
//     * @param usRRS 是否用日日顺库存
//     * @return
//     */
//    @Deprecated
//    ServiceResult<String> reassignSecCodeForCreateOrderToLes(String sku, String secCode,
//                                                             String refNo, String source,
//                                                             Integer num, boolean usRRS);
//
//    /**
//     * CBS库存接口--初始化待销售的子键数据
//     * @param sku 子件物料编码
//     * @return 是否已经同步子件
//     */
//    ServiceResult<Boolean> saleSubMachine(String sku);
//
//    /**
//     * CBS库存接口--取消子件销售
//     * @param sku 子件物料编码
//     * @return 是否已经取消子件销售
//     */
//    ServiceResult<Boolean> deleteSaleSubMachine(String sku);
//
//    /**
//     * 根据库存获取仓库信息
//     * @param sCode
//     * @return
//     */
//    ServiceResult<Storages> getStoragesBySCode(String sCode);
//
//    /**
//     * 获取CBS库存销售库存
//     * @param sku
//     * @param secCode
//     * @return
//     */
//    ServiceResult<InvStock> getInvStock(String sku, String secCode);
//
//    ServiceResult<List<InvStock2Channel>> getInvStockChannelLst();
//
//    /**
//     * 获取待处理的变化库存队列
//     * @param topX 前X条
//     * @return
//     */
//    ServiceResult<List<StockChangeQueue>> getStockChangeQueue(Integer topX);
//
//    /**
//     * 处理后，删除变化队列
//     * @param id 队列id
//     * @return 影响行数
//     */
//    ServiceResult<Integer> deleteStockChangeQueue(Integer id);
//
//    /**
//     * 同步套机数据
//     * @param machineSet
//     * @return
//     */
//    ServiceResult<Boolean> machineSetSync(InvMachineSet machineSet);
//
//    /**
//     * 取得所有有效的锁定记录
//     * 有效：库位、sku非空，商城、淘宝、企业字段有大于0的数据
//     * @return
//     */
//    ServiceResult<List<StorageProducts>> getAllEffectiveLocks();
//
//    /**
//     * 根据sku和库位，获取对应的库存
//     * @param sku
//     * @param secCode
//     * @return
//     */
//    ServiceResult<StorageProducts> getStockBySkuAndSecCode(String sku, String secCode);
//
//    /**
//     * 查询库存LIST计算预留库存数据
//     * @param lastBatchId
//     * @param startIndex
//     * @param pageSize
//     * @return
//     */
//    ServiceResult<List<InvStockBatch>> queryInvStockBatchList(Integer lastBatchId, int startIndex,
//                                                              int pageSize);
//
//    /**
//     * 比较库存不同并记录
//     * @return
//     */
//    ServiceResult<Boolean> compareStockQtyDifLog();
//
//    /**
//     * 查询大家电多级库位关系列表
//     * @return
//     */
//    ServiceResult<List<BigStoragesRela>> getBigStoragesRelaList();
//
//    /**
//     * 根据套机sku查询子机sku列表
//     * @param sku
//     * @return
//     */
//    ServiceResult<List<String>> getSubSkuListByMainSku(String sku);
//
//    /**
//     * 检索DBM发运基地运输周期表
//     * @param params
//     * @return
//     */
//    ServiceResult<List<InvDbmBase>> getInvDbmBaseList(Map<String, Object> params);
//
//    /**
//     * 检索电商与DBM送达方表
//     * @param params
//     * @return
//     */
//    ServiceResult<List<InvDbmBaseSendAddress>> getInvDbmBaseSendAddressList(Map<String, Object> params);
//
//    /**
//     * 获取所有基地库信息
//     * @param params
//     * @return
//     */
//    ServiceResult<List<String>> findAllBaseStorage(Map params);
//
//    /**
//     * 更新库存到商城
//     * @param lastSyncDate 上次同步库存的更新时间
//     * @return 本次同步库存的最大更新时间
//     */
//    ServiceResult<Date> syncStockToShop(Date lastSyncDate);
//
//	/**
//	 * 更新区县库存到顺逛
//	 * @param lastSyncDate 上次同步库存的更新时间
//	 * @return
//	 */
//    ServiceResult<Date> syncStockToRegion(Date lastSyncDate);
//
//    /**
//     * JPB：同步顺逛库存从Eis库到Shop库
//     * @return
//     */
//    ServiceResult<Date> syncSgStockFromEisToShop(Date lastSyncDate);
//
//
//
//    /**
//	 * 定时同步LES库存到inv_base_stock_diff表中
//	 * @return
//	 */
//	ServiceResult<Integer> synLesStockToInvBaseStockDiff(Integer maxId,Integer maxNum);
//
//
//	/**
//	 * 定时删除InvBaseStockDiffLog日志表中数据
//	 * @return
//	 */
//	ServiceResult<Boolean> sysAutoDeleteInvBaseStockDiffLog();
//
//
//	/**
//	 * 更新EC库存到区县
//	 * @param storeId
//	 * @param brandId
//	 * @param department
//	 * @return
//	 */
//    ServiceResult<List<SgFlagShipStoreAuthorization>> syncStockToRegionEc(Integer storeId, Integer brandId, String department);
//
//}
