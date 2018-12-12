package com.haier.order.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.order.model.StockCommonModel;
import com.haier.order.model.StockModel;
import com.haier.order.model.StockSyncToRegionModel;
import com.haier.order.model.StockSyncToShopModel;
import com.haier.stock.model.SgFlagShipStoreAuthorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.BigStoragesRela;
//import com.haier.shop.model.SgFlagShipStoreAuthorization;
import com.haier.shop.model.StockChangeQueue;
import com.haier.shop.model.StockHolder;
import com.haier.shop.model.StorageProducts;
import com.haier.shop.model.Storages;
import com.haier.stock.model.InvDbmBase;
import com.haier.stock.model.InvDbmBaseSendAddress;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStock2Channel;
import com.haier.stock.model.InvStockBatch;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.model.InventoryBusinessTypes;

@Service
public class OrderCenterStockServiceImpl {
    private static Logger log = LoggerFactory.getLogger(OrderCenterStockServiceImpl.class);
    @Autowired
    private StockModel stockModel;
    @Autowired
    private StockCommonModel stockCommonModel;
    @Autowired
    private StockSyncToShopModel stockSyncToShopModel;
    @Autowired
    private StockSyncToRegionModel stockSyncToRegionModel;

    
    public void hello() {
        log.info(">>> hello job scheduler");
    }

    
    public ServiceResult<Boolean> updateCbsStockByInventoryBusiness(String sku, String lesSecCode,
                                                                    String channelCode,
                                                                    String itemProperty,
                                                                    Integer qty,
                                                                    InventoryBusinessTypes bizType,
                                                                    String mark, String refNo,
                                                                    boolean isFrozeStock,
                                                                    String mainSku) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        result.setResult(false);
        result.setSuccess(false);
        result.setMessage(
                "com.haier.cbs.stock.service.OrderThirdCenterStockService#updateCbsStockByInventoryBusiness Has been abandoned");
        return result;

    }

    
    public ServiceResult<Boolean> updateCbsStockByStockTransaction(InvStockTransaction stockTransaction) {
        ServiceResult<Boolean> result;
        try {
            result = stockModel.updateCbsStockByStockTransaction(stockTransaction);
        } catch (Exception e) {
            result = new ServiceResult<Boolean>();
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("根据出入库记录更新库存时出现未知异常：" + e.getMessage());
            log.error("updateCbsStockByStockTransaction：根据出入库记录更新库存时出现未知异常：", e);
        }
        return result;
    }

    
    public ServiceResult<Boolean> updateLesStock(String sku, String secCode, String channelCode,
                                                 Integer newQty) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockModel.updateLesStock(sku, secCode, channelCode, newQty);
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("更新 Les 库存时出现未知异常：" + e.getMessage());
            log.error("updateLesStock:同步日日顺库存发生异常，sku=" + sku + ", secCode=" + secCode
                    + ", channelCode=" + channelCode + ", newQty=" + newQty);
        }
        return result;
    }

    
    public ServiceResult<Boolean> updateJiDiStock(String sku, String secCode, Integer qty) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockModel.updateJiDiStock(sku, secCode, InvSection.CHANNEL_CODE_GD, qty);
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("更新 基地库存时出现未知异常：" + e.getMessage());
            log.error("updateJiDiStock:同步基地顺库存发生异常，sku=" + sku + ", secCode=" + secCode
                    + ", channelCode=" + InvSection.CHANNEL_CODE_GD + ", qty=" + qty);
        }
        return result;
    }

    
    public ServiceResult<Boolean> frozeStockQty(String sku, String secCode, String refNo,
                                                Integer frozenQty, InventoryBusinessTypes billType,
                                                String optUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result = stockModel.frozeStockQty(sku, secCode, refNo, frozenQty, billType, optUser);
            result.setMessage(result.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("冻结虚拟库位，冻结库存失败：" + e.getMessage());
            log.error("frozeStockQty：冻结库存时失败，refNo:" + refNo + ",sku:" + sku + ",secCode" + secCode
                            + ",frozenQty:" + frozenQty,
                    e);
        }
        return result;
    }

    
    public ServiceResult<Boolean> outFrozenStockQty(String sku, String refNo, Integer outQty,
                                                    InventoryBusinessTypes billType,
                                                    String optUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (outQty == null || outQty < 1) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("出库数量不能小于1");
                return result;
            }

            if (StringUtil.isEmpty(refNo)) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("要出口的单据号不能为空");
                return result;
            }

            List<StockHolder> stockHolders = new ArrayList<StockHolder>();
            List<InvMachineSet> machineSets = stockCommonModel.getSubMachinesByMainSku(sku);
            if (machineSets.size() <= 0) {
                stockHolders.add(StockHolder.newInstance(sku, null, outQty));
            } else {
                for (InvMachineSet machineSet : machineSets) {
                    Integer num = machineSet.getMenge().intValue();
                    stockHolders
                            .add(StockHolder.newInstance(machineSet.getSubSku(), null, outQty * num));
                }
            }
            List<StockHolder> stockHoldersForOut = new ArrayList<StockHolder>();
            for (StockHolder stockHolder : stockHolders) {
                List<InvStockLock> stockLocks = stockCommonModel.getStockLockList(refNo,
                        stockHolder.getSku());

                Integer qty = stockHolder.getChangeQty();
                for (InvStockLock stockLock : stockLocks) {
                    if (qty <= 0)
                        break;
                    Integer _releaseQty;
                    if (stockLock.getLockQty() <= stockLock.getRealeaseQty())
                        continue;
                    else
                        _releaseQty = stockLock.getLockQty() - stockLock.getRealeaseQty();
                    if (_releaseQty > qty)
                        _releaseQty = qty;
                    qty -= _releaseQty;
                    stockHoldersForOut.add(StockHolder.newInstance(stockLock.getSku(),
                            stockLock.getSecCode(), _releaseQty));
                }
                if (stockLocks.size() <= 0) {
                    result.setError("no_out_record",
                            "不存在此单的占用记录(refno=" + refNo + ")，不能出库(出库数：" + qty + ")");
                    result.setSuccess(true);
                    result.setResult(true);
                    return result;
                }
                if (qty.equals(outQty)) {
                    result.setError("repeated_out",
                            "此单据已经出库(refno=" + refNo + ")，不能重复出库(出库数：" + qty + ")");
                    result.setSuccess(true);
                    result.setResult(true);
                    return result;
                }
                if (qty > 0) {
                    result.setSuccess(false);
                    result.setResult(false);
                    result.setMessage("要出库的数量大于原冻结数量, " + qty);
                    return result;
                }
            }
            boolean isOutSuc = stockModel.outFrozenStockQty(refNo, billType, stockHoldersForOut,
                    optUser);
            result.setSuccess(isOutSuc);
            result.setResult(isOutSuc);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("出库失败，" + e.getMessage());
            log.error("outFrozenStockQty:出库失败，sku=" + sku + ",refNo=" + refNo + ", releaseQty="
                    + outQty + ", billType=" + billType.getCode() + ",optUser=" + optUser + ","
                    + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo,
                                                        Integer releaseQty,
                                                        InventoryBusinessTypes billType) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            return stockModel.releaseFrozenStockQty(sku, refNo, releaseQty, billType);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("释放库存失败，" + e.getMessage());
            log.error(
                    "releaseFrozenStockQty:释放库存失败，sku=" + sku + ",refNo=" + refNo + ", releaseQty="
                            + releaseQty + ", billType=" + billType.getCode() + "," + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo,
                                                        Integer releaseQty,
                                                        InventoryBusinessTypes billType,
                                                        String optUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            return stockModel.releaseFrozenStockQty(sku, refNo, releaseQty, billType, optUser);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("失败冻结库存失败：" + e.getMessage());
            log.error("releaseFrozenStockQty:释放库存失败，sku=" + sku + ",refNo=" + refNo
                    + ", releaseQty=" + releaseQty + ", billType=" + billType.getCode()
                    + ",optUser=" + optUser + "," + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<String> reassignSecCodeForCreateOrderToLes(String sku, String secCode,
                                                                    String refNo, String source,
                                                                    Integer num, boolean usRRS) {
        ServiceResult<String> result = new ServiceResult<String>();
        result.setResult(null);
        result.setSuccess(false);
        result.setMessage(
                "com.haier.cbs.stock.service.OrderThirdCenterStockService#reassignSecCodeForCreateOrderToLes has been abandoned ");
        return result;
    }

    public ServiceResult<Boolean> saleSubMachine(String sku) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (StringUtil.isEmpty(sku, true)) {
            log.info("saleSubMachine:销售子件，物料编码为空");
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("销售子件，物料编码为空");
            return result;
        }

        try {
            boolean isSuc = stockModel.saleSubMachine(sku);
            result.setResult(isSuc);
            result.setSuccess(isSuc);
            if (!isSuc) {
                result.setMessage("没有同步到子件数据");
            }
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("添加销售子件发生异常，子件物料sku=" + sku + "," + e.getMessage());
            log.error("saleSubMachine:添加子件销售失败，子件物料sku=" + sku, e);
        }
        return result;
    }

    public ServiceResult<Boolean> deleteSaleSubMachine(String sku) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (StringUtil.isEmpty(sku, true)) {
            log.info("deleteSaleSubMachine:取消销售子件，物料编码为空");
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("取消销售子件，物料编码为空");
            return result;
        }

        try {
            boolean isSuc = stockModel.deleteSaleSubMachine(sku);
            result.setResult(isSuc);
            result.setSuccess(isSuc);
            if (!isSuc) {
                result.setMessage("取消子件销售失败");
            }
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("取消销售子件失败， 子件物料sku=" + sku + "," + e.getMessage());
            log.error("deleteSaleSubMachine:取消销售子件失败，子件物料sku=" + sku, e);
        }
        return result;
    }

    
    public ServiceResult<Storages> getStoragesBySCode(String sCode) {
        ServiceResult<Storages> result = new ServiceResult<Storages>();
        try {
            result.setResult(stockModel.getStorages(sCode));
        } catch (Exception e) {
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    
    public ServiceResult<InvStock> getInvStock(String sku, String secCode) {
        ServiceResult<InvStock> result = new ServiceResult<InvStock>();
        try {
            result.setResult(stockModel.getInvStock(sku, secCode));
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据sku(" + sku + ")和secCode(" + secCode + ")获取库存时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    
    public ServiceResult<List<StockChangeQueue>> getStockChangeQueue(Integer topX) {
        ServiceResult<List<StockChangeQueue>> result = new ServiceResult<List<StockChangeQueue>>();
        try {
            result.setResult(stockModel.getStockChangeQueue(topX));
        } catch (Exception e) {
            log.error("获取前X条库存变化队列时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    
    public ServiceResult<Integer> deleteStockChangeQueue(Integer id) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(stockModel.deleteStockChangeQueue(id));
        } catch (Exception e) {
            log.error("根据id(" + id + ")删除库存变化队列时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    
    public ServiceResult<Boolean> machineSetSync(InvMachineSet machineSet) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(stockModel.machineSetSync(machineSet));
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("同步套机数据，出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<List<StorageProducts>> getAllEffectiveLocks() {
        ServiceResult<List<StorageProducts>> result = new ServiceResult<List<StorageProducts>>();
        try {
            result.setResult(stockModel.getAllEffectiveLocks());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取渠道锁定数据时出现未知异常");
            log.error("获取渠道锁定数据时出现未知异常", e);
        }
        return result;
    }

    
    public ServiceResult<StorageProducts> getStockBySkuAndSecCode(String sku, String secCode) {
        ServiceResult<StorageProducts> result = new ServiceResult<StorageProducts>();
        try {
            result.setResult(stockModel.getStorageProducts(secCode, sku));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
            log.error("根据sku和secCode获取库存时(sku:" + sku + ",secCode:" + secCode + ")，出现未知异常:"
                            + e.getMessage(),
                    e);
        }
        return result;
    }

    
    public ServiceResult<List<InvStock2Channel>> getInvStockChannelLst() {
        ServiceResult<List<InvStock2Channel>> result = new ServiceResult<List<InvStock2Channel>>();
        try {
            result.setResult(stockModel.getInvStockChannelLst());
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("获取所有所有库位中（分渠道）物料库存信息出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<List<InvStockBatch>> queryInvStockBatchList(Integer lastBatchId,
                                                                     int startIndex, int pageSize) {
        ServiceResult<List<InvStockBatch>> result = new ServiceResult<List<InvStockBatch>>();
        result.setResult(this.stockModel.queryInvStockBatchList(lastBatchId, startIndex, pageSize));
        return result;
    }

    
    public ServiceResult<Boolean> compareStockQtyDifLog() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        String msg = this.stockModel.compareStockQtyDifLog();
        result.setMessage(msg + ", 比较差异数据成功");
        return result;
    }

    
    public ServiceResult<List<BigStoragesRela>> getBigStoragesRelaList() {
        ServiceResult<List<BigStoragesRela>> result = new ServiceResult<List<BigStoragesRela>>();
        try {
            result.setResult(stockModel.getBigStoragesRelaList());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询大家电多级库位关系列表：" + e.getMessage());
            log.error("查询大家电多级库位关系列表：", e);
        }
        return result;
    }

    
    public ServiceResult<List<String>> getSubSkuListByMainSku(String sku) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(stockModel.getSubSkuListByMainSku(sku));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询大家电多级库位关系列表：" + e.getMessage());
            log.error("查询大家电多级库位关系列表：", e);
        }
        return result;
    }

    
    public ServiceResult<List<InvDbmBase>> getInvDbmBaseList(Map<String, Object> params) {
        ServiceResult<List<InvDbmBase>> result = new ServiceResult<List<InvDbmBase>>();
        try {
            result.setResult(stockModel.selectInvDbmBase(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("InvDbmBase表检错错误：" + e.getMessage());
            log.error("InvDbmBase表检错错误：", e);
        }
        return result;
    }

    
    public ServiceResult<List<String>> findAllBaseStorage(Map params) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(stockModel.findAllBaseStorage(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("InvDbmBase表检错错误：" + e.getMessage());
            log.error("InvDbmBase表检错错误：", e);
        }
        return result;
    }

    
    public ServiceResult<List<InvDbmBaseSendAddress>> getInvDbmBaseSendAddressList(Map<String, Object> params) {
        ServiceResult<List<InvDbmBaseSendAddress>> result = new ServiceResult<List<InvDbmBaseSendAddress>>();
        try {
            result.setResult(stockModel.selecInvDbmBaseSendAddress(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("InvDbmBaseSendAddress表检错错误：" + e.getMessage());
            log.error("InvDbmBaseSendAddress表检错错误：", e);
        }
        return result;
    }

    
    public ServiceResult<Date> syncStockToShop(Date lastSyncDate) {
        ServiceResult<Date> serviceResult = new ServiceResult<Date>();
        try {
            serviceResult.setResult(stockSyncToShopModel.syncToShop(lastSyncDate));
        } catch (Exception e) {
            serviceResult.setResult(lastSyncDate);
            serviceResult.setSuccess(false);
            serviceResult.setMessage("同步库存到商城失败");
            log.error("同步库存到商城失败:", e);
        }

        return serviceResult;
    }

    
    public ServiceResult<Date> syncStockToRegion(Date lastSyncDate) {

        ServiceResult<Date> serviceResult = new ServiceResult<Date>();
        try {
            serviceResult.setResult(stockSyncToRegionModel.syncToRegion(lastSyncDate));
        } catch (Exception e) {
            serviceResult.setResult(lastSyncDate);
            serviceResult.setSuccess(false);
            serviceResult.setMessage("同步区县库存到顺逛失败");
            log.error("同步区县库存到顺逛失败:", e);
        }

        return serviceResult;

    }

    
    public ServiceResult<Date> syncSgStockFromEisToShop(Date lastSyncDate) {
        ServiceResult<Date> serviceResult = new ServiceResult<Date>();
        try {
            serviceResult.setResult(stockModel.syncSgStockFromEisToShop(lastSyncDate));
        } catch (Exception e) {
            serviceResult.setResult(lastSyncDate);
            serviceResult.setSuccess(false);
            serviceResult.setMessage("同步顺逛库存从Eis到Shop失败");
            log.error("同步顺逛库存从Eis到Shop失败:", e);
        }

        return serviceResult;
    }

    
    public ServiceResult<Integer> synLesStockToInvBaseStockDiff(Integer maxId, Integer maxNum) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(stockModel.autoSynchLesStock(maxId, maxNum));
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("同步LES库存到inv_base_stock_diff表失败");
            log.error("同步LES库存到inv_base_stock_diff表失败:", e);
        }
        return serviceResult;
    }

    
    public ServiceResult<Boolean> sysAutoDeleteInvBaseStockDiffLog() {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(stockModel.autoDeleteInvBaseStockDiffLog());
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("定时删除InvBaseStockDiffLog日志失败");
            log.error("定时删除InvBaseStockDiffLog日志失败:", e);
        }
        return serviceResult;
    }

    
    public ServiceResult<List<SgFlagShipStoreAuthorization>> syncStockToRegionEc(
            Integer storeId, Integer brandId, String department) {
        ServiceResult<List<SgFlagShipStoreAuthorization>> serviceResult = new ServiceResult<List<SgFlagShipStoreAuthorization>>();
        try {
            serviceResult.setResult(stockModel.syncToRegionEc(storeId, brandId, department));
        } catch (Exception e) {
            serviceResult.setResult(new ArrayList<SgFlagShipStoreAuthorization>());
            serviceResult.setSuccess(false);
            serviceResult.setMessage("同步EC库存到区县失败");
            log.error("同步EC库存到区县失败:", e);
        }
        return serviceResult;
    }

    public void setStockSyncToShopModel(StockSyncToShopModel stockSyncToShopModel) {
        this.stockSyncToShopModel = stockSyncToShopModel;
    }

    public void setStockModel(StockModel stockModel) {
        this.stockModel = stockModel;
    }

    public void setStockCommonModel(StockCommonModel stockCommonModel) {
        this.stockCommonModel = stockCommonModel;
    }

    public StockSyncToRegionModel getStockSyncToRegionModel() {
        return stockSyncToRegionModel;
    }

    public void setStockSyncToRegionModel(StockSyncToRegionModel stockSyncToRegionModel) {
        this.stockSyncToRegionModel = stockSyncToRegionModel;
    }

}
