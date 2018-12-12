//package com.haier.orderthird.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.haier.common.ServiceResult;
//import com.haier.purchase.data.model.InvBudgetOrg;
//import com.haier.purchase.data.model.WAAddress;
//import com.haier.shop.model.BigStoragesRela;
//import com.haier.shop.model.StorageCities;
//import com.haier.shop.model.Storages;
//import com.haier.stock.model.*;
//import com.haier.stock.model.InvSection;
//
///**
// * 基础库存服务
// * Created by 钊 on 14-3-27.
// */
//public interface OrderThirdCenterStockCommonService {
//
//    /**
//     * 根据库存获取仓库信息
//     *
//     * @param sCode
//     * @return
//     */
//    ServiceResult<Storages> getStoragesBySCode(String sCode);
//
//    /**
//     * 根据ID查询区县
//     * @param provinceId
//     * @param cityId
//     * @param regionId
//     * @return
//     */
//    ServiceResult<StorageCities> getStorageCityByIds(Integer provinceId, Integer cityId,
//                                                     Integer regionId);
//
//    /**
//     * 通过送达方编码获取渠道编码
//     *
//     * @param receiverCode
//     * @return
//     */
//    ServiceResult<String> getChannelCodeByReceiverCode(String receiverCode);
//
//    /**
//     * 通过订单来源获取渠道
//     *
//     * @param source
//     * @return
//     */
//    ServiceResult<String> getChannelCodeByOrderSource(String source);
//
//    /**
//     * 获取渠道列表
//     *
//     * @return
//     */
//    ServiceResult<List<InvStockChannel>> getChannels();
//
//    /**
//     * 根据库位编码获取库位对象
//     *
//     * @param secCode
//     * @return
//     */
//    ServiceResult<InvSection> getSectionByCode(String secCode);
//
//    /**
//     * 根据渠道获取库位信息
//     *
//     * @param whCode
//     * @param channelCode
//     * @return
//     */
//    ServiceResult<List<InvSection>> getSectionByWhCodeAndChannelCode(String whCode,
//                                                                     String channelCode);
//
//    /**
//     * 根据状态，获取库位列表
//     *
//     * @param status
//     *            状态，可为空
//     * @return
//     */
//    ServiceResult<List<InvSection>> getSectionByStatus(Integer status);
//
//    /**
//     * 通过仓库(TC)代码获取仓库
//     *
//     * @param whCode
//     * @return
//     */
//    ServiceResult<InvWarehouse> getWarehouse(String whCode);
//
//    /**
//     * 获取所有OMS库位匹配WA库位码
//     *
//     * @return
//     */
//    ServiceResult<List<InvSection2Oms>> getAllOMSSectionInfo();
//
//    /**
//     * 取得所有订单渠道数据
//     *
//     * @return
//     */
//    ServiceResult<List<InvChannel2OrderSource>> getAllOrder2ChannelSource();
//
//    /**
//     * 查询大家电多级库位关系列表
//     *
//     * @return
//     */
//    ServiceResult<List<BigStoragesRela>> getBigStoragesRelaList();
//
//    /**
//     * 通过主SKU查询子SKU
//     *
//     * @param machineSet
//     * @return
//     */
//    ServiceResult<List<InvMachineSet>> getSubMachinesByMainSku(InvMachineSet machineSet);
//
//    /**
//     * 根据套机sku查询子机sku列表
//     *
//     * @param sku
//     * @return
//     */
//    ServiceResult<List<String>> getSubSkuListByMainSku(String sku);
//
//    /**
//     * 通过电商库位码获取仓库
//     *
//     * @param estorge_id
//     * @return
//     */
//    ServiceResult<InvWarehouse> getWhByEstorgeId(String estorge_id);
//
//    /**
//     * 通过电商库位码获取日日顺仓库List
//     *
//     * @param params
//     * @return
//     */
//    ServiceResult<List<InvRrsWarehouse>> getRrsWhByEstorgeId(Map<String, Object> params);
//
//    /**
//     * 根据wa库码查询wa地址信息
//     *
//     * @param waCode wa库码
//     * @return
//     */
//    ServiceResult<List<WAAddress>> getWAAddressInfo(String waCode);
//
//    /**
//     * 预算体取得
//     *
//     * @param params
//     * @return
//     */
//    ServiceResult<InvBudgetOrg> getBudgetOrg(Map<String, Object> params);
//
//    /**
//     * 根据销售组织编码取供应商信息
//     *
//     * @param sale_org_id 销售组织编码
//     * @return
//     */
//    ServiceResult<List<InvBaseSupplier>> getSuppliperInfo(String sale_org_id);
//
//    /**
//     * 查询基地直发销售组织信息
//     *
//     * @param params 销售组织编码
//     * @return
//     */
//    ServiceResult<List<InvBaseSupplierForBaseSend>> getSuppliperInfoForBaseSend(Map<String, Object> params);
//
//    /**
//     * 查询基地直发订单来源信息
//     *
//     * @return
//     */
//    ServiceResult<List<InvChannel2OrderSource>> getOrdeSourceName();
//
//    /**
//     * 查询基地直发SAP渠道码信息
//     *
//     * @param
//     * @return
//     */
//    ServiceResult<List<InvBaseElectBussinessChannel>> getSAPChannCode(String cbs_channel_code);
//
//    /**
//     * 通过日日顺C码获取仓库编码
//     *
//     * @param centerCode
//     * @return
//     */
//    ServiceResult<String> getWhCodeByCenterCode(String centerCode);
//
//    /**
//     * 根据退货原因查找编号
//     *
//     * @param returnReason
//     * @return
//     */
//    ServiceResult<String> getIdByReturnReason(String returnReason);
//
//    /**
//     * 查询基地库工厂编码
//     * @return
//     */
//    ServiceResult<List<InvGdFactory>> queryGdFactoryList();
//
//    /**
//     * 查询套机编码
//     * @param subSku
//     * @return
//     */
//    ServiceResult<List<InvMachineSet>> getMainSkuBySubSku1(String subSku);
//
//    /**
//     * 根据订单来源查询sap渠道code和sap客户码
//     *
//     * @param 订单来源
//     * @return
//     */
//    ServiceResult<InvChannel2OrderSource> getSapChannelCodeAndCustomerCode(String ordeSourceCode);
//
//    /**
//     * 获取 所有省份
//     * @return Map<Integer, String>  key: 省份编码， value:身份名称
//     */
//    ServiceResult<Map<Integer, String>> getAllProvince();
//
//    /**
//     * 获取 所有城市
//     * @param provinceCode 省份编码
//     * @return Map<Integer, String> key:城市编码， value:城市名称
//     */
//    ServiceResult<Map<Integer, String>> getAllCityByProvId(Integer provinceCode);
//
//    /**
//     * 获取 所有区域
//     * @param regionCode 区域编码
//     * @return Map<Integer, String> key:区域编码， value:区域名称
//     */
//    ServiceResult<Map<Integer, String>> getAllRegionByCityId(Integer regionCode);
//
//    /**
//     * 根据共享库位和渠道编码获取库位
//     * @param lesSecCode
//     * @param channelCode
//     * @return
//     */
//    ServiceResult<InvSection> getByLesSecCodeAndChannelCode(String lesSecCode, String channelCode,String itemProperty);
//}
