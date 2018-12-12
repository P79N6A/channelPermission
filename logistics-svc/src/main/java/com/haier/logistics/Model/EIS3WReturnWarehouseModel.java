package com.haier.logistics.Model;

import java.util.Date;
import java.util.List;

import com.haier.eis.model.EisInterfaceFinance3w;
import com.haier.eis.model.EisStockBusinessQueue3W;
import com.haier.eis.model.LesStockTransQueue3W;
import com.haier.eis.service.EisInterfaceFinance3WService;
import com.haier.eis.service.EisStockBusinessQueue3WService;
import com.haier.eis.service.LesStockTransQueue3WService;
import com.haier.logistics.Helper.LogisticsHandler;
import com.haier.logistics.service.StockCommonService;
import com.haier.stock.model.InventoryBusinessTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.BusinessException;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.VomInOutStoreItem;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.eis.service.EisVomInOutStoreItemService;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.eis.service.EisVomShippingStatusService;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockTransaction;

/**
 * 3W正品退仓
 * @author wangp-c
 *
 */
@Configuration
@Service
public class EIS3WReturnWarehouseModel {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(EIS3WReturnWarehouseModel.class);
	@Autowired
	private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
	@Autowired
	private EisVomInOutStoreItemService       eisVomInOutStoreItemService;
	@Autowired
	private LesStockTransQueue3WService lesStockTransQueue3WDao;
/*
	@Autowired
	private DataSourceTransactionManager transactionManagerEis;
*/
	@Autowired
	private EisVomShippingStatusService eisVomShippingStatusService;
	@Autowired
	private StockCommonService stockCommonService;
	private LogisticsHandler logisticsHandler;
	@Autowired
    private EisStockBusinessQueue3WService eisStockBusinessQueue3WDao;
	@Autowired
    private EisInterfaceFinance3WService eisInterfaceFinance3WDao;
	@Autowired
    private EisExternalSkuService        eisExternalSkuService;
	public boolean processVomInOutStore3W() {
	        List<VomInOutStoreOrder> delayOrders = eisVomInOutStoreOrderService.getByProcessStatus3W(
	            VomInOutStoreOrder.PROCESS_STATUS_NEW, VomInOutStoreOrder.DELAY_YES);
	        for (VomInOutStoreOrder delayOrder : delayOrders) {
	            try {
	                processVomInOutStore3W(delayOrder);
	            } catch (Exception e) {
	                LOGGER.error("(3W正品退仓)处理延后的VomInOutStoreOrder(id=" + delayOrder.getId() + ")失败:", e);
	                eisVomInOutStoreOrderService.setDelay(delayOrder.getId(), e.getMessage());
	            }
	        }
	        while (true) {
	            List<VomInOutStoreOrder> orders = eisVomInOutStoreOrderService.getByProcessStatus3W(
	                VomInOutStoreOrder.PROCESS_STATUS_NEW, VomInOutStoreOrder.DELAY_NO);
	            if (orders.size() <= 0) {
	                break;
	            }
	            for (VomInOutStoreOrder order : orders) {
	                try {
	                    processVomInOutStore3W(order);
	                } catch (Exception e) {
	                    LOGGER.error("(3W正品退仓)处理VomInOutStoreOrder(id=" + order.getId() + ")失败:", e);
	                    eisVomInOutStoreOrderService.setDelay(order.getId(), e.getMessage());
	                }
	            }
	        }
	        return true;
	    }
	
	 
	 
	 private String transferVomOrderType(int orderType, int busType) {
	        String inventoryBusinessType;
	        switch (orderType) {
	           //vom按采购入库处理，cbs按照调拨入库业务处理
	            case 1://3w正品退仓
	                if (busType == 2) {
	                    inventoryBusinessType = InventoryBusinessTypes.RETURN_WAREHOUSE_3W.getCode();
	                }  else {
	                    inventoryBusinessType = InventoryBusinessTypes.UNDEFINED.getCode();
	                }
	                break;
	            default:
	                inventoryBusinessType = InventoryBusinessTypes.UNDEFINED.getCode();
	        }
	        return inventoryBusinessType;
	    }
	 
	 public boolean processVomShippingStatus3W() {
	        List<VomShippingStatus> shippingStatuses = eisVomShippingStatusService.getByProcessStatus3W(0);
	        for (VomShippingStatus shippingStatus : shippingStatuses) {
	            try {
	                logisticsHandler.process(shippingStatus);
	            } catch (Exception ex) {
	                LOGGER.error("处理物流状态失败（3W正品退仓）：", ex);
	            }
	        }
	        return true;
	    }
	 
	 
	    
	    private class LessTransferTransProcess3W extends LessTransProcess3W {

	        LessTransferTransProcess3W(LesStockTransQueue3W transQueue) {
	            super(transQueue);
	        }

	        @Override
	        boolean doWith() throws BusinessException {
	            String channel="WA";
	            setChannel(channel);
	            setBusinessProcessStatus(0);
	            return true;
	        }
	    }
	 
	 
	    /**
	     * LESS出入库记录处理抽象类，根据不同的业务类型实现确定渠道的方法，可以指定是否占用和关联业务处理状态(3W正品退仓)
	     */
	    private abstract class LessTransProcess3W {
	        private LesStockTransQueue3W transQueue3W;

	        private boolean isFrozen = false; //是否需要占用库存，默认为否

	        private boolean isRelease = false; //是否需要释放库存，默认为否

	        private int businessProcessStatus = 2; //关联业务处理状态，默认为不处理

	        private String channel; //关联渠道

	        LessTransProcess3W(LesStockTransQueue3W transQueue3W) {
	            this.transQueue3W = transQueue3W;
	        }

	        public LesStockTransQueue3W getTrans() {
	            return transQueue3W;
	        }

	        boolean getIsFrozen() {
	            return isFrozen;
	        }

	        void setIsFrozen(boolean isFrozen) {
	            this.isFrozen = isFrozen;
	        }

	        int getBusinessProcessStatus() {
	            return businessProcessStatus;
	        }

	        void setBusinessProcessStatus(int businessProcessStatus) {
	            this.businessProcessStatus = businessProcessStatus;
	        }

	        boolean getIsRelease() {
	            return isRelease;
	        }

	        void setIsRelease(boolean isRelease) {
	            this.isRelease = isRelease;
	        }

	        public String getChannel() {
	            return channel;
	        }

	        public void setChannel(String channel) {
	            this.channel = channel;
	        }

	        /**
	         * 设置交易是否占用库存、是否释放库存、关联业务处理状态、关联渠道
	         * @throws BusinessException 业务异常
	         */
	        abstract boolean doWith() throws BusinessException;

	        private boolean updateTransStatus(Integer status, String errorMessage) {
	            if (!StringUtil.isEmpty(errorMessage) && errorMessage.length() > 255) {
	                errorMessage = errorMessage.substring(0, 255);
	            }
	            return lesStockTransQueue3WDao.updateStatus(getTrans().getId(), status,
	                getTrans().getStatus(), errorMessage) > 0;
	        }

	        private boolean updateTransDone() {
	            return updateTransStatus(LesStockTransQueue3W.STATUS_DONE, "");
	        }

	        protected boolean updateTransFailed(String errorMessage) {
	            return updateTransStatus(LesStockTransQueue3W.STATUS_DELAY, errorMessage);
	        }

	        protected boolean updateTransUnidentified(String errorMessage) {
	            return updateTransStatus(LesStockTransQueue3W.STATUS_UNIDENTIFIED, errorMessage);
	        }

	        /**
	         * 添加到业务处理队列(3W正品退仓)
	         * @param
	         */
	        private void newStockBusinessQueue3W(LesStockTransQueue3W stockTransQueue3W) {
	            EisStockBusinessQueue3W stockBusinessQueue = new EisStockBusinessQueue3W();
	            stockBusinessQueue.setStockTransQueue3WId(stockTransQueue3W.getId());
	            stockBusinessQueue.setAddTime(new Date());
	            eisStockBusinessQueue3WDao.insert(stockBusinessQueue);
	        }


	        private InvStockTransaction generateInvTrans() {
	            InvStockTransaction stockTransaction = new InvStockTransaction();
	            stockTransaction.setSku(transQueue3W.getSku());
	            stockTransaction.setExternalSecCode(transQueue3W.getSecCode());
	            stockTransaction.setChannelCode(channel);
	            stockTransaction.setCorderSn(transQueue3W.getCorderSn());
	            stockTransaction.setQuantity(transQueue3W.getQuantity());
	            stockTransaction.setMark(transQueue3W.getMark());
	            stockTransaction.setItemProperty(transQueue3W.getCharg());
	            stockTransaction.setBillType(StringUtil.isEmpty(transQueue3W.getBillType())
	                ? InventoryBusinessTypes.UNDEFINED.getCode() : transQueue3W.getBillType());
	            stockTransaction.setBillTime(transQueue3W.getBillTime());
	            stockTransaction.setIsFrozen(getIsFrozen() ? 1 : 0);
	            stockTransaction.setIsRelease(getIsRelease() ? 1 : 0);
	            stockTransaction.setProcessStatus(0);
	            stockTransaction.setBusinessProcessStatus(businessProcessStatus);
	            stockTransaction.setAddTime(new Date());
	            stockTransaction.setIsDelay(0);
	            return stockTransaction;
	        }
	    }
	    

		public void setVomInOutStoreOrderDao(EisVomInOutStoreOrderService eisVomInOutStoreOrderService) {
			this.eisVomInOutStoreOrderService = eisVomInOutStoreOrderService;
		}

		public void setVomInOutStoreItemDao(EisVomInOutStoreItemService eisVomInOutStoreItemService) {
			this.eisVomInOutStoreItemService = eisVomInOutStoreItemService;
		}
		 private void processVomInOutStore3W(VomInOutStoreOrder vomInOutStoreOrder) {
		        List<VomInOutStoreItem> items = eisVomInOutStoreItemService
		            .getNotProcessByOrderId(vomInOutStoreOrder.getId());
		        String secCode = getSecCode(vomInOutStoreOrder.getStoreCode());
		        String remark = "";
		        boolean isAllItemsProcessSuccess = true;
		        for (VomInOutStoreItem item : items) {
		        	LesStockTransQueue3W stockTransQueue = new LesStockTransQueue3W();
		            stockTransQueue.setLesBatchId(item.getId());
		            stockTransQueue.setSku(item.getHrCode());
		            stockTransQueue.setCorderSn(vomInOutStoreOrder.getOrderNo());
		            stockTransQueue.setSecCode(secCode);
		            stockTransQueue.setOutping(vomInOutStoreOrder.getCertification());
		            stockTransQueue.setBillTime(vomInOutStoreOrder.getOutInDate());
		            stockTransQueue.setBillType(transferVomOrderType(vomInOutStoreOrder.getOrderType(),
		                vomInOutStoreOrder.getBusType()));
		            stockTransQueue.setQuantity(item.getNumber());
		            stockTransQueue.setLesBillNo(vomInOutStoreOrder.getExpNo() + item.getItemNo());
		            stockTransQueue.setMark(vomInOutStoreOrder.getBusType() == 1 ? "H" : "S");
		            stockTransQueue.setKunnrSaleTo("");
		            stockTransQueue.setKunnrSendTo("");
		            stockTransQueue.setBwart("");
		            stockTransQueue.setCharg(item.getStorageType());
		            stockTransQueue.setData("");
		            stockTransQueue.setStatus(LesStockTransQueue.STATUS_UNDONE);
		            stockTransQueue.setFinanceStatus(0);
		            stockTransQueue.setAddTime(new Date());
		            stockTransQueue.setChannelCode("WA");
		            stockTransQueue.setVersionCode("");
		            stockTransQueue.setZeile("000" + item.getItemNo());

		            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		            /*TransactionStatus status = transactionManagerEis.getTransaction(def);*/
		            try {
		                int n = eisVomInOutStoreItemService.updateProcessStatus(item.getId(),
		                    VomInOutStoreOrder.PROCESS_STATUS_STOCK_TRANSACTION_HAS_GENERATED,
		                    VomInOutStoreOrder.PROCESS_STATUS_NEW);
		                if (n <= 0) {
		                    if (LOGGER.isWarnEnabled()) {
		                        LOGGER.warn("处理VomInOutStoreItem(id=" + item.getId() + ")出现并发情况（3W正品退仓）");
		                    }
		                    remark += "并发情况，itemId=" + item.getId();
		                    isAllItemsProcessSuccess = false;
		                } else {
		                    LesStockTransQueue3W transQueue = lesStockTransQueue3WDao
		                        .getByOrderSn(stockTransQueue.getCorderSn(), stockTransQueue.getSku());
		                    if (transQueue != null) {
		                        LOGGER.info("[VOM][ProcessVomInOutStore]:存在重复的记录-"
		                                    + stockTransQueue.getCorderSn() + ","
		                                    + stockTransQueue.getSku());
		                    } else {
		                    	lesStockTransQueue3WDao.insert(stockTransQueue);
		                    }
		                }
		                /*transactionManagerEis.commit(status);*/
		            } catch (Exception e) {
		                isAllItemsProcessSuccess = false;
		                /*transactionManagerEis.rollback(status);*/
		                LOGGER.error("(3W正品退仓)处理VomInOutStoreItem(id=" + item.getId() + ")出现错误：", e);
		                remark += e.getMessage();
		                break;
		            }
		        }

		        if (isAllItemsProcessSuccess) {
		            eisVomInOutStoreOrderService.updateProcessStatus(vomInOutStoreOrder.getId(),
		                VomInOutStoreOrder.PROCESS_STATUS_STOCK_TRANSACTION_HAS_GENERATED,
		                VomInOutStoreOrder.PROCESS_STATUS_NEW);
		        } else {
		            if (remark.length() > 255) {
		                remark = remark.substring(0, 255);
		            }
		            eisVomInOutStoreOrderService.setDelay(vomInOutStoreOrder.getId(), remark);
		        }

		    }
		 private String getSecCode(String rrsSecCode) {
		        ServiceResult<String> result = stockCommonService.getWhCodeByCenterCode(rrsSecCode);
		        if (!result.getSuccess())
		            throw new BusinessException("通过日日顺C码获取仓库编码失败：" + result.getMessage());
		        if (StringUtil.isEmpty(result.getResult())) {
		            throw new BusinessException("通过日日顺C码获取仓库编码失败：不可识别的C码：" + rrsSecCode);
		        }

		        return result.getResult() + "WA";
		    }
}
