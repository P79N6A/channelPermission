package com.haier.stock.services;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.model.WAAddress;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.StorageCities;
import com.haier.shop.model.Storages;
import com.haier.stock.model.InvBaseElectBussinessChannel;
import com.haier.stock.model.InvBaseSupplier;
import com.haier.stock.model.InvBaseSupplierForBaseSend;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvGdFactory;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvSection2Oms;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockCommonService;


/**
 * 基础库存服务实现
 * Created by 钊 on 14-3-27.
 */

@Service
public class StockCommonServiceImpl implements StockCommonService {
    private static Logger    log = LoggerFactory.getLogger(StockCommonServiceImpl.class);
    @Autowired
    private StockCommonModel stockCommonModel;

    public void setStockCommonModel(StockCommonModel stockCommonModel) {
        this.stockCommonModel = stockCommonModel;
    }

    /**
     *--------------------------------------------------------------------------------------------
     * @return
     */

    @Override
    public ServiceResult<Storages> getStoragesBySCode(String sCode) {
        ServiceResult<Storages> result = new ServiceResult<Storages>();
        try {
            result.setResult(stockCommonModel.getStorages(sCode));
        } catch (Exception e) {
            log.error("根据sCode(" + sCode + ")获取库位信息时发生异常:", e);
            result.setSuccess(false);
            result.setMessage("服务器发生未知异常:" + e.getMessage());
        }

        return result;
    }

    @Override
    public ServiceResult<String> getChannelCodeByReceiverCode(String receiverCode) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            result.setResult(stockCommonModel.getChannelCodeByRecieverCode(receiverCode));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("通过送达方编码获取渠道编码发生未知异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<String> getChannelCodeByOrderSource(String source) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            result.setResult(stockCommonModel.getChannelCodeByOrderSource(source));
        } catch (Exception e) {
            log.error("通过订单来源获取渠道编码出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("通过订单来源获取渠道编码出现未知异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvStockChannel>> getChannels() {
        ServiceResult<List<InvStockChannel>> result = new ServiceResult<List<InvStockChannel>>();
        try {
            result.setResult(stockCommonModel.getChannels());
        } catch (Exception e) {
            log.error("获取渠道出现未知异常：", e);
            result.setSuccess(false);
            result.setError("获取渠道出现未知异常:", e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<InvSection> getSectionByCode(String secCode) {
        ServiceResult<InvSection> result = new ServiceResult<InvSection>();
        try {
            result.setResult(stockCommonModel.getSectionByCode(secCode));
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("根据库位编码(secCode:" + secCode + ")获取库位时，出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvSection>> getSectionByWhCodeAndChannelCode(String whCode,
                                                                            String channelCode) {
        ServiceResult<List<InvSection>> result = new ServiceResult<List<InvSection>>();
        try {
            result
                .setResult(stockCommonModel.getSectionByWhCodeAndChannelCode(whCode, channelCode));
        } catch (Exception e) {
            log.error("根据渠道获取库位信息失败：", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvSection>> getSectionByStatus(Integer status) {
        ServiceResult<List<InvSection>> result = new ServiceResult<List<InvSection>>();
        try {
            result.setResult(stockCommonModel.getSectionByStatus(status));
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("根据状态获取库位列表时，出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<InvWarehouse> getWarehouse(String whCode) {
        ServiceResult<InvWarehouse> result = new ServiceResult<InvWarehouse>();
        try {
            result.setResult(stockCommonModel.getWarehouse(whCode));
        } catch (Exception e) {
            log.error("根据wh_code(" + whCode + ")获取仓库信息时发生异常:", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public ServiceResult<List<InvSection2Oms>> getAllOMSSectionInfo() {
        ServiceResult<List<InvSection2Oms>> result = new ServiceResult<List<InvSection2Oms>>();
        try {
            result.setResult(stockCommonModel.getAllOMSSectionInfo());
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("获取所有OMS库位匹配WA库位码时出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvChannel2OrderSource>> getAllOrder2ChannelSource() {
        ServiceResult<List<InvChannel2OrderSource>> result = new ServiceResult<List<InvChannel2OrderSource>>();
        try {
            result.setResult(stockCommonModel.getAllOrder2ChannelSource());
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("获取所有订单渠道数据时出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }


    /**
     * -----------------------------------------------------------------------------------------
     * @param machineSet
     * @return
     */
    @Override
    public ServiceResult<List<BigStoragesRela>> getBigStoragesRelaList() {
        ServiceResult<List<BigStoragesRela>> result = new ServiceResult<List<BigStoragesRela>>();
        try {
            result.setResult(stockCommonModel.getBigStoragesRelaList());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询大家电多级库位关系列表：" + e.getMessage());
            log.error("查询大家电多级库位关系列表：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvMachineSet>> getSubMachinesByMainSku(InvMachineSet machineSet) {
        ServiceResult<List<InvMachineSet>> result = new ServiceResult<List<InvMachineSet>>();
        try {
            result.setResult(stockCommonModel.getSubMachinesByMainSku(machineSet.getMainSku()));
        } catch (Exception e) {
            log.error("根据mainSku(" + machineSet.getMainSku() + ")获取仓库信息时发生异常:", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public ServiceResult<List<String>> getSubSkuListByMainSku(String sku) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(stockCommonModel.getSubSkuListByMainSku(sku));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询大家电多级库位关系列表：" + e.getMessage());
            log.error("查询大家电多级库位关系列表：", e);
        }
        return result;
    }

    /**
     * 通过电商库位码获取仓库
     * 
     * @param estorge_id
     * @return
     */
    @Override
    public ServiceResult<InvWarehouse> getWhByEstorgeId(String estorge_id) {
        ServiceResult<InvWarehouse> result = new ServiceResult<InvWarehouse>();
        try {
            result.setResult(stockCommonModel.getWhByEstorgeId(estorge_id));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过电商库位码获取仓库：" + e.getMessage());
            log.error("通过电商库位码获取仓库：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<String> getWhCodeByCenterCode(String centerCode) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            result.setResult(stockCommonModel.getWhCodeByCenterCode(centerCode));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 通过电商库位码获取日日顺仓库List
     * 
     * @param params
     * @return
     */
    @Override
    public ServiceResult<List<InvRrsWarehouse>> getRrsWhByEstorgeId(Map<String, Object> params) {
        ServiceResult<List<InvRrsWarehouse>> result = new ServiceResult<List<InvRrsWarehouse>>();
        try {
            result.setResult(stockCommonModel.getRrsWhByEstorgeId(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
            log.error("通过电商库位码获取日日顺仓库List：", e);
        }
        return result;
    }

    /**
     * 预算体取得
     * 
     * @param params
     * @return
     */
    @Override
    public ServiceResult<InvBudgetOrg> getBudgetOrg(Map<String, Object> params) {
        ServiceResult<InvBudgetOrg> result = new ServiceResult<InvBudgetOrg>();
        try {
            result.setResult(stockCommonModel.getBudgetOrg(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("预算体取得：" + e.getMessage());
            log.error("预算体取得：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<WAAddress>> getWAAddressInfo(String waCode) {
        ServiceResult<List<WAAddress>> result = new ServiceResult<List<WAAddress>>();
        try {
            result.setResult(stockCommonModel.getWAAddressInfo(waCode));
        } catch (Exception e) {
            log.error("获取WA库信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @param sale_org_id
     * @return
     * @see com.haier.cbs.stock.service.StockCommonService#getSuppliperInfo(java.lang.String)
     */
    @Override
    public ServiceResult<List<InvBaseSupplier>> getSuppliperInfo(String sale_org_id) {
        ServiceResult<List<InvBaseSupplier>> result = new ServiceResult<List<InvBaseSupplier>>();
        try {
            result.setResult(stockCommonModel.getSuppliperInfo(sale_org_id));
        } catch (Exception e) {
            log.error("获取供应商信息发生异常：", e);
            result.setMessage("获取供应商信息发生异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @param params
     * @return
     * @see com.haier.cbs.stock.service.StockCommonService#getSuppliperInfoForBaseSend(java.util.Map)
     */
    @Override
    public ServiceResult<List<InvBaseSupplierForBaseSend>> getSuppliperInfoForBaseSend(Map<String, Object> params) {
        ServiceResult<List<InvBaseSupplierForBaseSend>> result = new ServiceResult<List<InvBaseSupplierForBaseSend>>();
        try {
            result.setResult(stockCommonModel.getSuppliperInfoForBaseSend(params));
        } catch (Exception e) {
            log.error("获取其地址发供应商信息发生异常：", e);
            result.setMessage("获取其地址发供应商信息发生异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvBaseElectBussinessChannel>> getSAPChannCode(String cbs_channel_code) {
        ServiceResult<List<InvBaseElectBussinessChannel>> result = new ServiceResult<List<InvBaseElectBussinessChannel>>();
        try {
            result.setResult(stockCommonModel.getSAPChannCode(cbs_channel_code));
        } catch (Exception e) {
            log.error("获取SAP渠道码异常：", e);
            result.setMessage("获取SAP渠道码异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvChannel2OrderSource>> getOrdeSourceName() {
        ServiceResult<List<InvChannel2OrderSource>> result = new ServiceResult<List<InvChannel2OrderSource>>();
        try {
            result.setResult(stockCommonModel.getOrdeSourceName());
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("获取所有订单来源数据时出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<String> getIdByReturnReason(String returnReason) {
        ServiceResult<String> result = new ServiceResult<String>();
        result.setResult(stockCommonModel.getIdByReturnReason(returnReason));
        return result;
    }

    @Override
    public ServiceResult<List<InvGdFactory>> queryGdFactoryList() {
        ServiceResult<List<InvGdFactory>> result = new ServiceResult<List<InvGdFactory>>();
        try {
            List<InvGdFactory> factoryList = stockCommonModel.queryGdFactoryList();
            /* List<String> factoryCodeList = new ArrayList<String>();
             for (InvGdFactory code : factoryList) {
                 factoryCodeList.add(code.getFactory());
             }*/
            result.setResult(factoryList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询基地库编码失败");
            log.error("queryGdFactoryList:查询基地库工厂编码出现异常，" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvMachineSet>> getMainSkuBySubSku1(String subSku) {
        ServiceResult<List<InvMachineSet>> result = new ServiceResult<List<InvMachineSet>>();
        try {
            result.setResult(stockCommonModel.getMainSkuBySubSku(subSku));
        } catch (Exception e) {
            log.error("根据subSku(" + subSku + ")获取子件列表发生异常:", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 根据订单来源查询sap渠道code和sap客户码
     * 
     * @param 订单来源
     * @return
     */
    @Override
    public ServiceResult<InvChannel2OrderSource> getSapChannelCodeAndCustomerCode(String ordeSourceCode) {
        ServiceResult<InvChannel2OrderSource> result = new ServiceResult<InvChannel2OrderSource>();
        try {
            result.setResult(stockCommonModel.getSapChannelCodeAndCustomerCode(ordeSourceCode));
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("获取sap渠道code和sap客户码数据时出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<StorageCities> getStorageCityByIds(Integer provinceId, Integer cityId,
                                                            Integer regionId) {
        ServiceResult<StorageCities> result = new ServiceResult<StorageCities>();
        try {
            result.setResult(stockCommonModel.getStorageCityByIds(provinceId, cityId, regionId));
        } catch (Exception e) {
            log.error("根据ids查询出现异常，provinceId=" + provinceId + ",cityId=" + cityId + ", regionId="
                      + regionId);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public ServiceResult<Map<Integer, String>> getAllProvince() {

        ServiceResult<Map<Integer, String>> result = new ServiceResult<Map<Integer, String>>();
        try {
            result.setResult(stockCommonModel.getAllProvince());
        } catch (Exception e) {
            log.error("查询所有省份");
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Map<Integer, String>> getAllCityByProvId(Integer provinceCode) {
        ServiceResult<Map<Integer, String>> result = new ServiceResult<Map<Integer, String>>();
        try {
            result.setResult(stockCommonModel.getAllCityByProvId(provinceCode));
        } catch (Exception e) {
            log.error("根据provinceCode查询所有城市, provinceCode=" + provinceCode);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Map<Integer, String>> getAllRegionByCityId(Integer regionCode) {
        ServiceResult<Map<Integer, String>> result = new ServiceResult<Map<Integer, String>>();
        try {
            result.setResult(stockCommonModel.getAllRegionByCityId(regionCode));
        } catch (Exception e) {
            log.error("根据regionCode查询所有区域，regionCode=" + regionCode);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

	@Override
	public ServiceResult<InvSection> getByLesSecCodeAndChannelCode(
			String lesSecCode, String channelCode,String itemProperty) {
		ServiceResult<InvSection> result = new ServiceResult<InvSection>();
        try {
            result.setResult(stockCommonModel.getByLesSecCodeAndChannelCode(lesSecCode, channelCode, itemProperty));
        } catch (Exception e) { 
            log.error("根据共享库位lesSecCode" + lesSecCode+", channelCode="+channelCode+"查询失败");
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
	}
}
