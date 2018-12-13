package com.haier.purchase.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.T2OrderDao;
import com.haier.purchase.data.service.PurchaseT2OrderService;

/**
 * Created by 黄俊 on 2014/7/8.
 */
@Service
public class PurchaseT2OrderServiceImpl implements PurchaseT2OrderService {

	@Autowired
	T2OrderDao t2OrderDao;

	/**
	 * 获取预测备料历史查询表单
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<T2OrderItem> findT2Orders(Map<String, Object> params) {
		return t2OrderDao.findT2Orders(params);
	}

	/**
	 * 渠道和品类对应的已提交数量取得
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<T2OrderItem> findT2OrdersSum(Map<String, Object> params) {
		return t2OrderDao.findT2OrdersSum(params);
	}

	/**
	 * 获得T+2条数
	 * 
	 * @return
	 */
	@Override
	public int findT2OrdersCNT(Map<String, Object> params) {
		return t2OrderDao.findT2OrdersCNT(params);
	}

	/**
	 * 获得条数
	 * 
	 * @return
	 */
	@Override
	public int getRowCnts() {
		return t2OrderDao.getRowCnts();
	}

	/**
	 * 获取PO查询信息
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<CrmOrderItem> findPOList(Map<String, Object> params) {
		return t2OrderDao.findPOList(params);
	}

	/**
	 * 获取3W查询信息
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<CrmOrderItem> find3WList(Map<String, Object> params) {
		return t2OrderDao.find3WList(params);
	}

	/**
	 * 获取PO信息条数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public int findPOListCNT(Map<String, Object> params) {
		return t2OrderDao.findPOListCNT(params);
	}

	/**
	 * 获取3W信息条数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public int find3WListCNT(Map<String, Object> params) {
		return t2OrderDao.find3WListCNT(params);
	}

	/**
	 * 订单提交
	 * 
	 * @param params
	 */
	@Override
	public void updateOrderStatus(Map<String, Object> params) {
		t2OrderDao.updateOrderStatus(params);
	}

	/**
	 * 错误信息更新
	 * 
	 * @param params
	 */
	@Override
	public void updateByQty(T2OrderItem orderItem) {
		t2OrderDao.updateByQty(orderItem);
	}

	/**
	 * 价格信息更新
	 * 
	 * @param params
	 */
	@Override
	public void updatePrice(T2OrderItem orderItem) {
		t2OrderDao.updatePrice(orderItem);
	}

	/**
	 * 创建T+2订单表单
	 * 
	 * @param t2OrderItem
	 * @return
	 */
	@Override
	public Integer insert(T2OrderItem t2OrderItem) {
		return t2OrderDao.insert(t2OrderItem);
	}

	/**
	 * 订单删除
	 * 
	 * @param params
	 */
	@Override
	public void deleteOrderStatus(Map<String, Object> params) {
		t2OrderDao.deleteOrderStatus(params);
	}

	/**
	 * 订单数量修改
	 * 
	 * @param params
	 */
	@Override
	public Integer updateCount(Map<String, Object> params) {
		return t2OrderDao.updateCount(params);
	}

	/**
	 * 撤销订单
	 * 
	 * @param params
	 */
	@Override
	public void updateRevokeFlag(Map<String, Object> params) {
		t2OrderDao.updateRevokeFlag(params);
	}

	/**
	 * 撤销失败信息更新
	 * 
	 * @param t2OrderItem
	 */
	@Override
	public void updateErrMsg(T2OrderItem t2OrderItem) {
		t2OrderDao.updateErrMsg(t2OrderItem);
	}

	/**
	 * Oms订单号取得
	 * 
	 * @param params
	 */
	@Override
	public List<T2OrderItem> findCancelT2OrderForOms(Map<String, Object> params) {
		return t2OrderDao.findCancelT2OrderForOms(params);
	}

	/**
	 * 综合查询获取T+2订单信息
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<T2OrderItem> findT2OrderMultipleList(Map<String, Object> params) {
		return t2OrderDao.findT2OrderMultipleList(params);
	}

	/**
	 * 综合查询获取T+2订单信息条数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public Integer findT2OrderMultipleListCNT(Map<String, Object> params) {
		return t2OrderDao.findT2OrderMultipleListCNT(params);
	}

	/**
	 * 手工关单
	 * 
	 * @param params
	 */
	@Override
	public void manualCloseOrder(Map<String, Object> params) {
		t2OrderDao.manualCloseOrder(params);
	}

	/**
	 * 撤消手工关单 赵雪林 2015-1-14
	 * 
	 * @param params
	 */
	@Override
	public void cancelCloseOrder(Map<String, Object> params) {
		t2OrderDao.cancelCloseOrder(params);
	}

	/**
	 * 审核订单
	 * 
	 * @param params
	 */
	@Override
	public void reviewT2Order(Map<String, Object> params) {
		t2OrderDao.reviewT2Order(params);
	}

	/**
	 * 产品部审核订单
	 * 
	 * @param params
	 */
	@Override
	public void reviewT2OrderDepart(Map<String, Object> params) {
		t2OrderDao.reviewT2OrderDepart(params);
	}

	/**
	 * 审核通过订单情报取得
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<T2OrderItem> findT2OrderForOms(Map<String, Object> params) {
		return t2OrderDao.findT2OrderForOms(params);
	}

	/**
	 * 提报OMS的状态更新
	 * 
	 * @param params
	 */
	@Override
	public void updateByOms(T2OrderItem t2OrderItem) {
		t2OrderDao.updateByOms(t2OrderItem);
	}

	@Override
	public void updateOmsFlowFlagOnly(T2OrderItem t2OrderItem) {
		t2OrderDao.updateOmsFlowFlagOnly(t2OrderItem);
	}

	/**
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	@Override
	public List<T2OrderItem> getOnwayNumByCateChan(String category_id,
			String ed_channel_id) {
		return t2OrderDao.getOnwayNumByCateChan(category_id, ed_channel_id);
	}

	/**
	 * 根据品类渠道取得本周已用
	 * 
	 * @param report_year_week
	 *            本周
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	@Override
	public List<T2OrderItem> getUsedNumByCateChan(String report_year_week,
			String category_id, String ed_channel_id) {
		return t2OrderDao.getUsedNumByCateChan(report_year_week, category_id,
				ed_channel_id);
	}

	/**
	 * 更新订单状态为已开入WA提单
	 * 
	 * @param so_id
	 *            so单号
	 * @param outTime
	 *            出日日顺库时间
	 * @return
	 */
	@Override
	public void updateStatusToOutRRS(Map<String, Object> params) {
		t2OrderDao.updateStatusToOutRRS(params);
	}

	/**
	 * 更新订单状态为已入WA库
	 * 
	 * @param so_id
	 *            so单号
	 * @param inTime
	 *            入WA库时间
	 * @return
	 */
	@Override
	public void updateStatusToInWA(Map<String, Object> params) {
		t2OrderDao.updateStatusToInWA(params);
	}

	/**
	 * 根据订单号获取CRM自动采购的DN号
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<String> get85DNFromHaierT2(Map params) {
		return t2OrderDao.get85DNFromHaierT2(params);
	}

	/**
	 * 更新入WA库状态
	 * 
	 * @param params
	 */
	@Override
	public void updateStatusToInWAByDN(Map params) {
		t2OrderDao.updateStatusToInWAByDN(params);
	}

	/**
	 * 更新haier t+2主表状态
	 * 
	 * @param params
	 */
	@Override
	public void updateStatus(Map params) {
		t2OrderDao.updateStatus(params);
	}

	/**
	 * 从超期库存闸口表中获得标准的渠道、品类数据
	 * 
	 * @return
	 */
	@Override
	public List<HaierLimitHistoryItem> getFullChannelProductList(Map params) {
		return t2OrderDao.getFullChannelProductList(params);
	}

	/**
	 * 插入或更新超期历史数据
	 * 
	 * @param item
	 */
	@Override
	public void insertOrUpdateLimitHistory(HaierLimitHistoryItem item) {
		t2OrderDao.insertOrUpdateLimitHistory(item);
	}

	/**
	 * 插入超期历史数据
	 * 
	 * @param item
	 */
	@Override
	public void insertLimitHistory(HaierLimitHistoryItem item) {
		t2OrderDao.insertLimitHistory(item);
	}

	/**
	 * 更新超期历史数据
	 * 
	 * @param item
	 */
	@Override
	public void updateLimitHistory(HaierLimitHistoryItem item) {
		t2OrderDao.updateLimitHistory(item);
	}

	/**
	 * 更新同步状态
	 * 
	 * @param item
	 */
	@Override
	public void updateSyncStatusByOms(T2OrderItem item) {
		t2OrderDao.updateSyncStatusByOms(item);
	}

	/**
	 * OMS已冻结推送
	 * 
	 * @param params
	 */
	@Override
	public int commitAgainOrderMultiple(Map<String, Object> params) {
		return t2OrderDao.commitAgainOrderMultiple(params);
	}

	/**
	 * 来源单号查询
	 * 
	 * @param source_order_id
	 * @return
	 */
	@Override
	public T2OrderItem getDataBySourceOrderId(String source_order_id) {
		return t2OrderDao.getDataBySourceOrderId(source_order_id);
	}

	/**
	 * 创建3w信息
	 * 
	 * @param t2OrderItem
	 * @return
	 */
	@Override
	public Integer w3insert(CrmOrderItem crmOrderItem) {
		return t2OrderDao.w3insert(crmOrderItem);
	}

	/**
	 * 更新3w修改
	 * 
	 * @param params
	 */
	@Override
	public void update3wByDnCode(CrmOrderItem crmOrderItem) {
		t2OrderDao.update3wByDnCode(crmOrderItem);
	}

	/**
	 * 根据85码获取wp单号
	 * 
	 * @param params
	 */
	@Override
	public List<CrmOrderItem> getDataBySourceByWPid(CrmOrderItem crmOrderItem) {
		return t2OrderDao.getDataBySourceByWPid(crmOrderItem);
	}

	/**
	 * 根据J单号获取wp单号
	 * 
	 * @param params
	 */
	@Override
	public List<CrmOrderItem> getDataByJCode(CrmOrderItem crmOrderItem) {
		return t2OrderDao.getDataByJCode(crmOrderItem);
	}

	/**
	 * 预约导出列表查询
	 * 
	 * @return
	 */
	@Override
	public List<CrmOrderItem> get3wSubscribeList(Map<String, Object> params) {
		return t2OrderDao.get3wSubscribeList(params);
	}

	/**
	 * 自动任务查询需要从crm获取订单的信息
	 * 
	 * @return
	 */
	@Override
	public List<CrmOrderItem> getT2ListToCrm() {
		return t2OrderDao.getT2ListToCrm();
	}

	/**
	 * 根据预约单号和85码从3w表中查询数据
	 * 
	 * @return
	 */
	@Override
	public List<CrmOrderItem> getIs3wList(CrmOrderItem crmOrderItem) {
		return t2OrderDao.getIs3wList(crmOrderItem);
	}

	/**
	 * 根据wp单号从3w表中查询已签收的货物数
	 * 
	 * @return
	 */
	@Override
	public List<CrmOrderItem> getSumByWPNo(CrmOrderItem crmOrderItem) {
		return t2OrderDao.getSumByWPNo(crmOrderItem);
	}

	/**
	 * 订单状态更新为已入3w库
	 * 
	 * @param crmOrderItem
	 */
	@Override
	public void update3Wyiru(CrmOrderItem crmOrderItem) {
		t2OrderDao.update3Wyiru(crmOrderItem);
	}

	/**
	 * 款先签收，需要自动推送cbs的订单信息
	 * 
	 * @return
	 */
	@Override
	public List<CrmOrderItem> getListByNoToSAP(CrmOrderItem crmOrderItem) {
		return t2OrderDao.getListByNoToSAP(crmOrderItem);
	}

	/**
	 * 更新3w推送sap结果信息
	 * 
	 * @param crmOrderItem
	 */
	@Override
	public void updateToSAPresoult(CrmOrderItem crmOrderItem) {
		t2OrderDao.updateToSAPresoult(crmOrderItem);
	}

	@Override
	public List<T2OrderItem> findT2OrdersToSap() {
		return t2OrderDao.findT2OrdersToSap();
	}

	@Override
	public void insertT2OrderInterfaceLog(Map<String, Object> params) {
		t2OrderDao.insertT2OrderInterfaceLog(params);
	}

	@Override
	public List<T2OrderInterfaceLog> findPurchaseLog(Map<String, Object> map) {
		List<T2OrderInterfaceLog> result = new ArrayList<T2OrderInterfaceLog>();
		try {
			result = t2OrderDao.findPurchaseLog(map);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public int getPurchaseLogRow(Map<String, Object> map) {
		int result = 0;
		try {
			result = t2OrderDao.getPurchaseLogRow(map);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateStatus80FromLES() {
		t2OrderDao.updateHaierT2OrderStatus80FromLES();
//		t2OrderDao.updateHaierT2OrderStatus80FromLES();
	}

	@Override
	public List<T2OrderItem> getT2WdOrderId(Map<String, Object> params) {
		return t2OrderDao.getT2WdOrderId(params);
	}

}
