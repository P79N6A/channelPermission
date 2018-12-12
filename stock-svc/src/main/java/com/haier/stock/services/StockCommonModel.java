package com.haier.stock.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.service.PurchaseInvBudgetOrgService;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.StoragesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.WAAddress;
import com.haier.purchase.data.service.PurchaseWAAddressService;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.StorageCities;
import com.haier.shop.model.Storages;
import com.haier.shop.service.StorageCitiesService;
import com.haier.stock.model.InvBaseElectBussinessChannel;
import com.haier.stock.model.InvBaseSupplier;
import com.haier.stock.model.InvBaseSupplierForBaseSend;

import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvChannel2Receiver;
import com.haier.stock.model.InvGdFactory;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvSection2Oms;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.InvBaseSupplierService;
import com.haier.stock.service.InvChannel2ReceiverService;
import com.haier.stock.service.InvGdFactoryService;
import com.haier.stock.service.InvSection2OmsService;
import com.haier.stock.service.ReturnReasonService;

import com.haier.stock.service.StockInvChannel2OrderSourceService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockInvRrsWarehouseService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockChannelService;
import com.haier.stock.service.StockInvStockLockService;
import com.haier.stock.service.StockInvWarehouseService;


/**
 * 简单库存服务Model，获取库存配置信息 Created by 钊 on 14-3-27.
 * @param <T>
 */

@Service
public class StockCommonModel<T> {
	@Autowired
    private StockInvMachineSetService         invMachineSetDao;
	@Autowired
    private StockInvWarehouseService           invWarehouseDao;
	@Autowired
    private StockInvRrsWarehouseService        invRrsWarehouseDao;
	@Autowired
    private StoragesService storagesDao;
	@Autowired
    private StockInvSectionService<T>            invSectionDao;
	@Autowired
    private InvChannel2ReceiverService    invChannel2ReceiverDao;
	@Autowired
    private StockInvChannel2OrderSourceService invChannel2OrderSourceDao;
	@Autowired
    private StockInvStockChannelService        invStockChannelDao;
	@Autowired
    private InvSection2OmsService         invSection2OmsDao;
	@Autowired
    private BigStoragesRelaService bigStoragesRelaService;
	@Autowired
    private PurchaseInvBudgetOrgService invBudgetOrgDao;
	@Autowired
    private PurchaseWAAddressService purchaseWAAddressService;
	@Autowired
    private InvBaseSupplierService        supplierDao;
	@Autowired
    private ReturnReasonService           returnReasonDao;
	@Autowired
    private InvGdFactoryService           invGdFactoryDao;
	@Autowired
    private StockInvStockLockService           invStockLockDao;
	@Autowired
    private StorageCitiesService          storageCitiesDao;

    /**
     * CBS简单查询逻辑--查询冻结记录列表
     * @param refNo
     * @param sku
     * @return
     */
    public List<InvStockLock> getStockLockList(String refNo, String sku) {
        List<InvStockLock> stockLocks = invStockLockDao.getByRefNoAndSku(refNo, sku);
        return stockLocks;
    }

    /**
     * 查询基地工厂编码
     * @return
     */
    public List<InvGdFactory> queryGdFactoryList() {
        return invGdFactoryDao.queryGdFactory();
    }

    public ReturnReasonService getReturnReasonDao() {
        return returnReasonDao;
    }

    public void setReturnReasonDao(ReturnReasonService returnReasonDao) {
        this.returnReasonDao = returnReasonDao;
    }

    /**
     * @return Returns the supplierDao
     */
    public InvBaseSupplierService getSupplierDao() {
        return supplierDao;
    }

    /**
     * @param supplierDao
     *            The supplierDao to set.
     */
    public void setSupplierDao(InvBaseSupplierService supplierDao) {
        this.supplierDao = supplierDao;
    }


    public List<InvMachineSet> getSubMachinesByMainSku(String mainSku) {
        return this.invMachineSetDao.getByMainSku(mainSku);
    }


    /**
     * 取得所有订单渠道数据
     * ---------------------------
     *

    public InvWarehouse getWarehouse(String whCode) {
        return invWarehouseDao.getByWhCode(whCode);
    }

    /**
     * 获取库位信息
     * 
     * @param sCode
     * @return
     */
    public Storages getStorages(String sCode) {
        return storagesDao.getByCode(sCode);
    }


    public StorageCities getStorageCityByIds(Integer provinceId, Integer cityId, Integer regionId) {
        return storageCitiesDao.getStorageCityByIds(provinceId, cityId, regionId);
    }

    /**
     * 根据库位编码获取库位对象
     * 
     * @param secCode
     * @return
     */
    public InvSection getSectionByCode(String secCode) {
        if (StringUtil.isEmpty(secCode)) {
            throw new BusinessException("库位编码不能为空。");
        }
        return invSectionDao.getBySecCode(secCode);
    }

    public List<InvSection> getSectionByWhCodeAndChannelCode(String whCode, String channelCode) {
        return invSectionDao.getByChannelCode(whCode, channelCode);
    }

    /**
     * 根据状态，获取库位列表
     * 
     * @param status
     *            状态，可为空
     * @return
     */
    public List<InvSection> getSectionByStatus(Integer status) {
        return invSectionDao.getByStatus(status);
    }

    public String getChannelCodeByRecieverCode(String receiverCode) {
        InvChannel2Receiver invChannel2Receiver = invChannel2ReceiverDao
            .getByReceiverCode(receiverCode);
        return invChannel2Receiver == null ? null : invChannel2Receiver.getChannelCode();
    }

    public String getChannelCodeByOrderSource(String source) {
        InvChannel2OrderSource invChannel2OrderSource = invChannel2OrderSourceDao.getBySource(source);
        return invChannel2OrderSource == null ? null : invChannel2OrderSource.getChannelCode();
    }

    public List<InvStockChannel> getChannels() {
        return invStockChannelDao.getAll();
    }

    /**
     * 获取所有OMS库位匹配WA库位码
     * 
     * @return
     */
    public List<InvSection2Oms> getAllOMSSectionInfo() {
        return invSection2OmsDao.getAllOMSSectionInfo();
    }

    /**
     * 取得所有订单渠道数据
     * 
     * @return
     */
    public List<InvChannel2OrderSource> getAllOrder2ChannelSource() {
        return invChannel2OrderSourceDao.getAllOrder2ChannelSource();
    }

    public List<BigStoragesRela> getBigStoragesRelaList() {
        return bigStoragesRelaService.getList();
    }

    public List<String> getSubSkuListByMainSku(String sku) {
        List<InvMachineSet> machineSets = invMachineSetDao.getByMainSku(sku);
        List<String> skuList = new ArrayList<String>();
        if (null != machineSets && !machineSets.isEmpty()) {
            for (InvMachineSet invMachineSet : machineSets) {
                skuList.add(invMachineSet.getSubSku());
            }
        }
        return skuList;
    }

    public List<InvMachineSet> getMainSkuBySubSku(String subSku) {
        List<InvMachineSet> machineSets = invMachineSetDao.getBySubSku(subSku);
        return machineSets;
    }

    /**
     * 通过电商库位码获取仓库
     * 
     * @param estorge_id
     * @return
     */
    public InvWarehouse getWhByEstorgeId(String estorge_id) {
        return invWarehouseDao.getWhByEstorgeId(estorge_id);
    }

    /**
     * 通过电商库位码获取日日顺仓库List
     * 
     * @param params
     * @return
     */
    public List<InvRrsWarehouse> getRrsWhByEstorgeId(Map<String, Object> params) {
        return invRrsWarehouseDao.getRrsWhByEstorgeId(params);
    }

    /**
     * 取得所有订单渠道数据
     * 
     * @return
     */
    public List<InvChannel2OrderSource> getOrdeSourceName() {
        return invChannel2OrderSourceDao.getAllOrder2ChannelSource();
    }

    /**
     * 预算体取得
     * 
     * @param params
     * @return
     */
    public InvBudgetOrg getBudgetOrg(Map<String, Object> params) {
        return invBudgetOrgDao.getBudgetOrg(params);
    }

    public String getWhCodeByCenterCode(String centerCode) {
        return invWarehouseDao.getWhCodeByCenterCode(centerCode);
    }

    /**
     * 查询所有省份
     * @return
     */
    public Map<Integer, String> getAllProvince() {
        Map<Integer, String> provinceMap = new HashMap<Integer, String>();
        List<StorageCities> provList = storageCitiesDao.getAllProvince();
        for (StorageCities entity : provList) {
            provinceMap.put(entity.getProvinceId(), entity.getProvinceName());
        }
        return provinceMap;
    }

    /**
     * 查询对应的城市
     * @param provinceId
     * @return
     */
    public Map<Integer, String> getAllCityByProvId(Integer provinceId) {
        Map<Integer, String> cityMap = new HashMap<Integer, String>();
        List<StorageCities> cityList = storageCitiesDao.getAllCityByProvId(provinceId);
        for (StorageCities entity : cityList) {
            cityMap.put(entity.getCityId(), entity.getCityName());
        }
        return cityMap;
    }

    /**
     * 查询对应的区域
     * @param regionId
     * @return
     */
    public Map<Integer, String> getAllRegionByCityId(Integer regionId) {
        Map<Integer, String> regionMap = new HashMap<Integer, String>();
        List<StorageCities> regionList = storageCitiesDao.getAllRegionByCityId(regionId);
        for (StorageCities entity : regionList) {
            regionMap.put(entity.getRegionId(), entity.getRegionName());
        }
        return regionMap;
    }
     
    /**
     * 根据共享库位 和 渠道查询 库位编码
     * @param lesSecCode
     * @param channelCode
     * @return
     */
    public InvSection getByLesSecCodeAndChannelCode(String lesSecCode, String channelCode, String itemProperty ){
    	return invSectionDao.getInvSection(lesSecCode, channelCode,itemProperty);
    }
    public List<InvBaseSupplier> getSuppliperInfo(String sale_org_id) {
        return supplierDao.getSuppliperInfo(sale_org_id);
    }
    public List<WAAddress> getWAAddressInfo(String waCode) {
        return purchaseWAAddressService.getWAAddressInfo(waCode);
    }
    /**
     * @param params
     * @return
     */
    public List<InvBaseSupplierForBaseSend> getSuppliperInfoForBaseSend(Map<String, Object> params) {
        return supplierDao.getSuppliperInfoForBaseSend(params);
    }

    /**
     * @param cbs_channel_code
     * @return
     */
    public List<InvBaseElectBussinessChannel> getSAPChannCode(String cbs_channel_code) {
        return supplierDao.getSAPChannCode(cbs_channel_code);
    }

    public String getIdByReturnReason(String returnReason) {
        return returnReasonDao.getIdByReturnReason(returnReason);
    }

    public void setInvGdFactoryDao(InvGdFactoryService invGdFactoryDao) {
        this.invGdFactoryDao = invGdFactoryDao;
    }

    public void setInvStockLockDao(StockInvStockLockService invStockLockDao) {
        this.invStockLockDao = invStockLockDao;
    }

    public void setStorageCitiesDao(StorageCitiesService storageCitiesDao) {
        this.storageCitiesDao = storageCitiesDao;
    }

    /**
     * 根据订单来源查询sap渠道code和sap客户码
     * 
     * @param 订单来源
     * @return
     */
    public InvChannel2OrderSource getSapChannelCodeAndCustomerCode(String ordeSourceCode) {
        return invChannel2OrderSourceDao.getSapChannelCodeAndCustomerCode(ordeSourceCode);
    }
    

    public InvWarehouse getWarehouse(String whCode) {
        return invWarehouseDao.getByWhCode(whCode);
    }

}
