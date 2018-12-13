package com.haier.stock.model;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.StockHolder;
import com.haier.shop.service.StockChangeQueueService;
import com.haier.stock.service.BaseStockService;
import com.haier.stock.service.InvBaseStockLogService;
import com.haier.stock.service.InvBaseStockService;
import com.haier.stock.service.InvStockChangeService;
import com.haier.stock.service.InvStockChannelService;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockChannelService;
import com.haier.stock.services.Helper.Handler;
import com.haier.stock.services.Helper.StockBizHelper;
import com.haier.stock.services.Helper.TransHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class HopStockSyncModel {

  private static Logger LOGGER = LogManager.getLogger(HopStockSyncModel.class);

  @Autowired
  private StockInvMachineSetService invMachineSetDao;
  @Autowired
  private StockInvSectionService invSectionDao;
  @Autowired
  private InvBaseStockService invBaseStockDao;
  @Autowired
  private BaseStockService baseStockDao;
  @Autowired
  private InvBaseStockLogService invBaseStockLogDao;
  @Autowired
  private StockInvStockChannelService invStockChannelDao;
  @Autowired
  private StockChangeQueueService stockChangeQueueDao;
  @Autowired
  private InvStockChangeService invStockChangeDao;
  @Autowired
  private InvStockService invStockDao;
  @Autowired
  private StockCenterItemService itemService;

  private DataSourceTransactionManager transactionManager;

  public String stockSync(final String sku, final String secCode, final Integer newQty,
      final String sourceCode) throws Exception {
    //物料检查
    ServiceResult<ItemBase> itemBaseService = itemService.getItemBaseBySku(sku);
    if (!itemBaseService.getSuccess()) {
      String message = "查询物料发生异常";
      return message;
    } else {
      ItemBase itemBase = itemBaseService.getResult();
      String message = "";
      if (itemBase == null || StringUtil.isEmpty(itemBase.getMaterialCode())) {
        message = "系统不存在此物料";
        return message;
      }

    }

    //查找库位
    InvSection section = invSectionDao.getBySecCode(secCode);
    //不存在添加
    if (section == null) {
      return secCode + "库位验证不存在，请及时维护！";
    }

    StringBuilder messageBuilder = new StringBuilder();
    TransHandler proxyHandler = new TransHandler(transactionManager);
    try {
      proxyHandler.handler(new Handler() {
        List<StockHolder> stockHolderList = new ArrayList<StockHolder>();

        @Override
        public boolean beforeProcess() {
          List<InvMachineSet> machineSets = invMachineSetDao.getByMainSku(sku);
          StockHolder stockHolder = null;
          if (machineSets.size() <= 0) {
            stockHolder = new StockHolder(sku, secCode, newQty);
            stockHolderList.add(stockHolder);
          } else {
            for (InvMachineSet machineSet : machineSets) {
              String _sku = machineSet.getSubSku();
              Integer _newQty = machineSet.getMenge().intValue() * newQty;
              stockHolder = new StockHolder(_sku, secCode, _newQty);
              stockHolderList.add(stockHolder);
            }
          }
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("同步到EHAIER库存，检查套机结束， sku=" + sku + ", qty=" + newQty
                + ",secCode=" + secCode + ",sourceCode=" + sourceCode);
          }
          return true;
        }

        @Override
        public boolean process() {
          for (StockHolder holder : stockHolderList) {
            Integer newStockQty = holder.getChangeQty();
            if (newStockQty < 0) {
              newStockQty = 0;
            }
            //                        //查找库位
            //                        InvSection section = invSectionDao.getBySecCode(holder.secCode);
            //                        //不存在添加
            //                        if (section == null) {
            //                            section = StockBizHelper.newSection(secCode,
            //                                InvSection.CHANNEL_CODE_HAIP, InvSection.W10);
            //
            //                            invSectionDao.insert(section);
            //                        }
            //查找库存
            InvBaseStock baseStock = invBaseStockDao.getForUpdate(sku, holder.getSecCode());
            //不存在添加
            Date dateTime = new Date();
            boolean isInsert = false;
            if (baseStock == null) {
              isInsert = true;
              baseStock = new InvBaseStock();
              baseStock.setSku(holder.getSku());
              baseStock.setLesSecCode(holder.getSecCode());
              baseStock.setSecCode(holder.getSecCode());
              baseStock.setStockQty(newStockQty);
              baseStock.setFrozenQty(0);
              baseStock.setCreateTime(dateTime);
              StockBizHelper.assignmentValue2InvBaseStock(itemService, invSectionDao,
                  baseStock);
            } else {
              //计算实际库存：实际库存至少为已经冻结的
              newStockQty = holder.getChangeQty() >= baseStock.getFrozenQty()
                  ? holder.getChangeQty() : baseStock.getFrozenQty();
            }
            //记录同步日志
            InvBaseStockLog invBaseStockLog = StockBizHelper.newInvBaseStockLog(
                baseStock, newStockQty, baseStock.getFrozenQty(), "", "",
                InventoryBusinessTypes.SYNC_STOCK, dateTime);
            StockBizHelper.assignmentValue2InvBaseStockLog(itemService, invSectionDao,
                invBaseStockLog);
            invBaseStockLogDao.insert(invBaseStockLog);
            //设置实际库存
            baseStock.setStockQty(newStockQty);
            baseStock.setUpdateTime(dateTime);
            //更新InvBaseStock
            if (isInsert) {
              invBaseStockDao.insert(baseStock);
            } else {
              invBaseStockDao.updateStockQty(baseStock.getStoId(), newStockQty,
                  dateTime);
            }
            BaseStock tempStock = new BaseStock();
            tempStock.setSku(sku);
            tempStock.setCode(secCode);
            Integer sellQty = newStockQty - baseStock.getFrozenQty();
            tempStock.setStockQty(sellQty > 0 ? sellQty : 0);
            tempStock.setStockType(BaseStock.STOCKTYPE_WA);
            //计算销售库存数,包括涉及到的套机计算
            List<BaseStock> stockList = StockBizHelper
                .regroupStockQtyBySku(baseStockDao, invMachineSetDao, tempStock);
            InvSection stockSection;
            for (BaseStock stock : stockList) {
              InvStock invStock = new InvStock();
              invStock.setSku(stock.getSku());
              invStock.setSecCode(stock.getCode());
              invStock.setStockQty(stock.getStockQty());
              stockSection = invSectionDao.getBySecCode(stock.getCode());
              //联动更新销售库存信息
              StockBizHelper.linkageUpdateStockForSales(itemService, invSectionDao,
                  invStockDao, invStockChannelDao, stockChangeQueueDao,
                  invStockChangeDao, invStock, stockSection, true);
            }
          }
          return true;
        }

        @Override
        public boolean afterProcess() {
          return true;
        }
      });
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    return messageBuilder.toString();
  }

}
