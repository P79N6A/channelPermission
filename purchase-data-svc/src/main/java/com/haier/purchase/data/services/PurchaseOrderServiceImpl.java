package com.haier.purchase.data.services;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.dao.purchase.PurchaseOrderDao;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseItemStatus;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import com.haier.purchase.data.model.PurchaseOrderModel;
import com.haier.purchase.data.model.PurchaseOrderQueue;
import com.haier.purchase.data.model.PurchaseOrderQueue4Daily;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import com.haier.purchase.data.model.PurchaseOrderStatus;
import com.haier.purchase.data.service.PurchaseOrderService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(PurchaseOrderServiceImpl.class);

	@Autowired
	PurchaseOrderDao purchaseOrderDao;
	@Autowired
	PurchaseOrderModel purchaseOrderModel;

	@Override
	public PurchaseOrder get(int poId) {
		return purchaseOrderDao.get(poId);
	}

	@Override
	public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params) {
		return purchaseOrderDao.getOrderInfoByDnSi(params);
	}

	@Override
	public ServiceResult<Boolean> isExistPurchaseItem(String sourceSn, String sku, String secCode) {
		Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.isExistPurchaseItem(sourceSn, sku, secCode));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("根据订单来源号，产品Sku，库位编码查询验证是否存在采购订单明细记录失败！，" + e.getMessage());
			log.error(
					"[purchase_order_service][createPurchaseOrder]:根据订单来源号，产品Sku，库位编码查询验证是否存在采购订单明细记录失败",
					e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> createPurchaseOrder(PurchaseOrder purchaseOrder,
			List<PurchaseItem> purchaseItemList) {
		Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel
					.createPurchaseOrder(purchaseOrder, purchaseItemList));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("采购订单创建失败，" + e.getMessage());
			log.error("[purchase_order_service][createPurchaseOrder]:采购订单创建失败", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueueWrapper>> findPurchaseOrderQueueByCondition(Date startTime,
			Date endTime, String poItemNo, int status_, PagerInfo pagerInfo) {
		ServiceResult<List<PurchaseOrderQueueWrapper>> result = new ServiceResult<List<PurchaseOrderQueueWrapper>>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
			List<PurchaseOrderQueueWrapper> list = purchaseOrderModel.findPurchaseOrderQueueByCondition(startTime, endTime, poItemNo, status_, pagerInfo);
			result.setResult(list);
			if (pagerInfo != null) {
				result.setPager(pagerInfo);
			}
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][findPurchaseOrderQueueByCondition]:根据条件查询加载采购订单队列列表信息出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrder>> findPurchaseOrder(Date startTime, Date endTime,
			String poNo, int status, PagerInfo pager) {
		ServiceResult<List<PurchaseOrder>> result = new ServiceResult<List<PurchaseOrder>>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
			List<PurchaseOrder> list = purchaseOrderModel.findPurchaseOrder(startTime, endTime, poNo, status, pager);
			result.setResult(list);
			if (pager != null) {
				result.setPager(pager);
			}
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][findPurchaseOrder]根据条件查询加载采购订单信息出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> confirmPurchaseOrder(int poId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			PurchaseOrder purchaseOrder = purchaseOrderModel.getPurchaseOrder(poId);
			//必须是新建状态才能确认
			if (purchaseOrder == null) {
				result.setResult(false);
				result.setMessage("采购订单【" + poId + "】已不存在");
				return result;
			}

			if (purchaseOrder.getStatus() == PurchaseOrderStatus.CONFIRM.getValue()) {
				result.setResult(false);
				result.setMessage("采购订单【" + poId + "】已经确认");
				return result;
			}

			if (purchaseOrder.getStatus().intValue() != PurchaseOrderStatus.PO_NEW.getValue()) {
				result.setResult(false);
				result.setMessage("采购订单【" + purchaseOrder.getPoNo() + "】的状态不是新建无法确认");
				return result;
			}
			result.setResult(purchaseOrderModel.confirmPurchaseOrder(poId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][confirmPurchaseOrder]:确认采购订单出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> cancelPurchaseOrder(int poId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			PurchaseOrder purchaseOrder = purchaseOrderModel.getPurchaseOrder(poId);
			//必须是新建状态才能取消
			if (purchaseOrder == null) {
				result.setResult(false);
				result.setMessage("采购订单【" + poId + "】已不存在");
				return result;
			}
			if (purchaseOrder.getStatus().intValue() != PurchaseOrderStatus.PO_NEW.getValue()) {
				result.setResult(false);
				result.setMessage("采购订单【" + purchaseOrder.getPoNo() + "】的状态不是新建无法取消");
				return result;
			}
			result.setResult(purchaseOrderModel.cancelPurchaseOrder(poId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][confirmPurchaseItem]:取消采购订单出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseItem>> findPurchaseItemByPoId(int poId) {
		ServiceResult<List<PurchaseItem>> result = new ServiceResult<List<PurchaseItem>>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
			List<PurchaseItem> list = purchaseOrderModel.findPurchaseItemByPoId(poId);
			result.setResult(list);
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][findPurchaseItem]:根据条件查询加载采购订单明细列表信息出错", e);
		}
		return result;
	}


	@Override
	public ServiceResult<Boolean> updatePurchaseProduceDailyOrderAfterInput(String sku,
																			String corderSn,
																			Integer inputQty,
																			Date inputTime) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updatePurchaseProduceDailyOrderAfterInput(sku,
					corderSn, inputQty, inputTime));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新采购订单明细表记录状态及采购订单表记录状态出错," + e.getMessage());
			log.error(
					"[purchase_order_service][updatePurchaseOrderAfterInput]:更新采购订单明细表记录状态及采购订单表记录状态出错",
					e);
		}

		return result;
	}

	/**
	 * 根据订单明细编号查询采购订单明细信息
	 * @param poItemNo
	 * @return
	 */
	@Override
	public ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo) {
		ServiceResult<PurchaseItem> result = new ServiceResult<PurchaseItem>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
			result.setResult(purchaseOrderModel.getPurchaseItemByPoItemNo(poItemNo));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error(
					"[purchase_order_service][getPurchaseItemByPoItemNo]:根据订单明细编号poItemNo查询采购订单明细信息出错",
					e);
		}
		return result;
	}

	/**
	 * 当采购订单数据完成入库操作后更新采购订单及采购订单明细状态<br/>
	 * poItemId,poId,status不能为null
	 * @param purchaseItem
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> updatePurchaseOrderAfterInput(PurchaseItem purchaseItem) {
		Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updatePurchaseOrderAfterInput(purchaseItem));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新采购订单明细表记录状态及采购订单表记录状态出错," + e.getMessage());
			log.error(
					"[purchase_order_service][updatePurchaseOrderAfterInput]:更新采购订单明细表记录状态及采购订单表记录状态出错",
					e);
		}

		return result;
	}

	@Override
	public ServiceResult<PurchaseOrder> getPurchaseOrder(int poId) {
		ServiceResult<PurchaseOrder> result = new ServiceResult<PurchaseOrder>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'systemModel' is required.");
			result.setResult(purchaseOrderModel.getPurchaseOrder(poId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][getPurchaseOrder]加载采购订单信息出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> confirmPurchaseItem(int poItemId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			PurchaseItem purchaseItem = purchaseOrderModel.getPuchaseItem(poItemId);

			if (purchaseItem == null) {
				result.setResult(false);
				result.setMessage("采购单【" + poItemId + "】已不存在");
				return result;
			} else if (purchaseItem.getStatus().intValue() != PurchaseItemStatus.PI_NEW.getValue()) {
				result.setResult(false);
				result.setMessage("采购单【" + purchaseItem.getPoItemNo() + "】的状态为"
						+ PurchaseItemStatus.fromValue(purchaseItem.getStatus()).getText()
						+ "，无法确认");
				return result;
			}
			result.setResult(purchaseOrderModel.confirmPurchaseItem(poItemId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][confirmPurchaseItem]:确认采购网单出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> cancelPurchaseItem(int poItemId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			PurchaseItem purchaseItem = purchaseOrderModel.getPuchaseItem(poItemId);
			//必须是新建状态才能取消
			if (null == purchaseItem) {
				result.setResult(false);
				result.setMessage("采购单【" + poItemId + "】已不存在");
				return result;
			} else if (purchaseItem.getStatus().intValue() != PurchaseItemStatus.PI_NEW.getValue()) {
				result.setResult(false);
				result.setMessage("采购单【" + purchaseItem.getPoItemNo() + "】的状态为"
						+ PurchaseItemStatus.fromValue(purchaseItem.getStatus()).getText()
						+ ",不能取消");
				return result;
			}

			result.setResult(purchaseOrderModel.cancelPurchaseItem(poItemId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][cancelPurchaseItem]:取消采购网单出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueueWrapper>> findPurchaseOrderQueue() {
		ServiceResult<List<PurchaseOrderQueueWrapper>> result = new ServiceResult<List<PurchaseOrderQueueWrapper>>();
		try {
			Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
			List<PurchaseOrderQueueWrapper> list = purchaseOrderModel.findPurchaseOrderQueue();
			result.setResult(list);
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[purchase_order_service][findPurchaseOrderQueue]:根据条件查询采购订单队列信息出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updatePurchaseOrderQueueAfterSynced(
			PurchaseOrderQueue purchaseOrderQueue) {
		Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel
					.updatePurchaseOrderQueueAfterSynced(purchaseOrderQueue));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新采购队列状态并记录Les响应信息出错，" + e.getMessage());
			log.error(
					"[purchase_order_service][updatePurchaseOrderQueueAfterSynced]:更新采购队列状态并记录Les响应信息出错",
					e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updatePurchaseProduceDailyFromOms(PurchaseOrderQueue4Daily queue,
			Map<String, String> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updatePurchaseProduceDailyFromOms(queue, params));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			if (e instanceof BusinessException) {
				log.error("更新日日单采购单状态失败：" + e.getMessage());
			} else {
				log.error("更新日日单采购单状态失败：", e);
			}
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updatePurchaseOrderQueue4DailyMessage(Integer queueId, String msg) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result
					.setResult(purchaseOrderModel.updatePurchaseOrderQueue4DailyMessage(queueId, msg));
		} catch (Exception e) {
			result.setResult(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("更新日日单采购单队列MSG失败：", e);
		}

		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4Daily(Integer queueStatus) {
		ServiceResult<List<PurchaseOrderQueue4Daily>> result = new ServiceResult<List<PurchaseOrderQueue4Daily>>();
		try {
			result.setResult(purchaseOrderModel.getPurchaseOrderQueue4Daily(queueStatus));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取日日单采购单队列失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> addToPurchaseOrderQueue4Daily(PurchaseOrderQueue4Daily queue4Daily) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.addToPurchaseOrderQueue4Daily(queue4Daily));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage("添加日日单采购单队列失败：" + e.getMessage());
			log.error("添加日日单采购单队列失败:", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Map<String, Boolean>> checkProduceDailyQueueIsExist(List<String> refNos) {
		ServiceResult<Map<String, Boolean>> result = new ServiceResult<Map<String, Boolean>>();
		try {
			result.setResult(purchaseOrderModel.checkProduceDailyQueueIsExist(refNos));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("校验日日单采购单队列是否存在出现未知错误：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> createPurchaseProduceDaily(PurchaseOrderQueue4Daily queue,
			PurchaseOrder purchaseOrder,
			List<PurchaseItem> purchaseItems) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.createPurchaseProduceDaily(queue, purchaseOrder,
					purchaseItems));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("新建日日单采购单失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> isPurchaseProduceDailyHasEntryWAWareHouse(String corderSn) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result
					.setResult(purchaseOrderModel.isPurchaseProduceDailyHasEntryWAWareHouse(corderSn));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("判断日日单采购单是否已完成入WA库出现未知错误：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForUpdateOrders() {
		ServiceResult<List<PurchaseOrderQueue4Daily>> result = new ServiceResult<List<PurchaseOrderQueue4Daily>>();
		try {
			result.setResult(purchaseOrderModel.getPurchaseOrderQueue4DailyForUpdateOrders());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取日日单采购单队列失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateProduceDailyQueueOrderStatus(Integer queueId,
			Integer newStatus,
			Integer oldStatus) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updateProduceDailyQueueOrderStatus(queueId,
					newStatus, oldStatus));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("更新日日单采购单队列的网单更新状态失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForSyncDetailInfo() {
		ServiceResult<List<PurchaseOrderQueue4Daily>> result = new ServiceResult<List<PurchaseOrderQueue4Daily>>();
		try {
			result.setResult(purchaseOrderModel.getPurchaseOrderQueue4DailyForSyncDetailInfo());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取日日单采购单队列失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<PurchaseItem> getPurchaseProduceDailyItem(String corderSn) {
		ServiceResult<PurchaseItem> result = new ServiceResult<PurchaseItem>();
		try {
			result.setResult(purchaseOrderModel.getPurchaseProduceDailyItem(corderSn));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取日日单采购单失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateProduceDailyQueueSyncStatus(Integer queueId,
			Integer newStatus,
			Integer oldStatus) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updateProduceDailyQueueSyncStatus(queueId,
					newStatus, oldStatus));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("更新日日单采购单队列的同步生产详细信息状态失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updatePurchaseProduceDailyFromLess(PurchaseOrderQueue4Daily queue4Daily,
			String lesPing,
			Date createOrderToLessDate) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updatePurchaseProduceDailyFromLess(queue4Daily,
					lesPing, createOrderToLessDate));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			if (e instanceof BusinessException) {
				log.error("更新日日单采购单状态失败：" + e.getMessage());
			} else {
				log.error("更新日日单采购单状态失败：", e);
			}
		}
		return result;
	}

	@Override
	public ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForSyncProdDateFromEDW() {
		ServiceResult<List<PurchaseOrderQueue4Daily>> result = new ServiceResult<List<PurchaseOrderQueue4Daily>>();
		try {
			result
					.setResult(purchaseOrderModel.getPurchaseOrderQueue4DailyForSyncProdDateFromEDW());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取日日单采购单队列失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updatePurchaseProduceDailyPrdDateFromEDW(PurchaseOrderQueue4Daily queue,
			Date prodDate) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseOrderModel.updatePurchaseProduceDailyProdDateFromOms(queue,
					prodDate));
		} catch (Exception e) {
			result.setResult(false);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			if (e instanceof BusinessException) {
				log.error("更新日日单采购单状态失败：" + e.getMessage());
			} else {
				log.error("更新日日单采购单状态失败：", e);
			}
		}
		return result;
	}
}
