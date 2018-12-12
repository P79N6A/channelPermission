package com.haier.purchase.data.service;

import com.haier.purchase.data.model.*;

import java.util.List;
import java.util.Map;


/**
 * Created by 黄俊 on 2014/8/4.
 */
public interface CgoNewService {

	/**
	 * 获取CGOT+2查询表单
	 * @param Map<String, Object> params
	 * @return
	 */
	public List<CgoOrderItemNew> findCgoOrderList(Map<String, Object> params);

	/**
	 * 获取CGOT+2查询条数
	 * @param Map<String, Object> params
	 * @return
	 */
	public Integer findCgoOrderListCNT(Map<String, Object> params);

	/**
	           * 获得条数
	           * @return
	           */

	public int getRowCnts();

	/**订单提交
	 * @param params
	 * @return 
	 */
	public Object updateCgoOrderStatus(Map<String, Object> params);

	/**
	 * 错误信息更新
	 * 
	 * @param params
	 * @return 
	 */
	public Object updateByQty(CgoOrderItemNew orderItem);

	/**
	 * 价格信息更新
	 * 
	 * @param params
	 * @return 
	 */
	public Object updatePrice(CgoOrderItemNew orderItem);

	/**
	 * 订单删除
	 * @param params
	 * @return 
	 */
	public Object deleteCgoOrderStatus(Map<String, Object> params);

	/**
	 * 综合查询获取T+2订单信息
	 * @param Map<String, Object> params
	 * @return
	 */
	public List<CgoOrderItemNew> findCgoOrderMultipleList(Map<String, Object> params);

	/**
	 * 综合查询获取T+2订单信息条数
	 * @param Map<String, Object> params
	 * @return
	 */
	public Integer findCgoOrderMultipleListCNT(Map<String, Object> params);

	/**
	 * 手工关单
	 * @param params
	 * @return 
	 */
	public Object manualCloseCgoOrder(Map<String, Object> params);

	/**
	 * 撤消手工关单
	 * 赵雪林 2015-1-14
	 * @param params
	 * @return 
	 */
	public Object cancelCloseCgoOrder(Map<String, Object> params);

	/**
	 * 获取PO查询信息
	 * @param Map<String, Object> params
	 * @return
	 */
	public List<SIOUInfoItem> findCgoPOList(Map<String, Object> params);

	/**
	 * 获取PO查询信息条数
	 * @param Map<String, Object> params
	 * @return
	 */
	public Integer findCgoPOListCNT(Map<String, Object> params);

	/**创建T+2单
	 * @param params
	 * @return 
	 */
	public Object insertCgoOrderList(Map<String, Object> params);

	/**审核订单
	 * @param params
	 * @return 
	 */
	public Object reviewCgoOrderList(Map<String, Object> params);

	/**
	 * 产品部审核订单
	 * @param params
	 * @return 
	 */

	public Object reviewCgoOrderDepart(Map<String, Object> params);

	/**
	 * 订单数量修改
	 * @param params
	 */
	public Integer updateCgoOrderCount(Map<String, Object> params);

	/**
	 * 撤销订单
	 * @param params
	 * @return 
	 */
	public Object updateCgoOrderRevokeFlag(Map<String, Object> params);

	/**
	 * CGO正品退货检索
	 * @param params
	 * @return
	 */
	public List<CgoGenuineRejectItemNew> findCgoGenuineRejectList(Map<String, Object> params);

	/**
	 * CGO正品退货条数
	 * @param params
	 * @return
	 */
	public Integer findCgoGenuineRejectListCNT(Map<String, Object> params);

	/**创建退货单
	 * @param params
	 * @return 
	 */
	public Object insertCgoGenuineRejectList(Map<String, Object> params);

	/**
	 * 正品退货订单删除
	 * @param params
	 * @return 
	 */
	public Object deleteCgoGenuineRejectStatus(Map<String, Object> params);

	/**正品退货订单提交
	 * @param params
	 * @return 
	 */
	public Object updateCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 正品退货撤销订单
	 * @param params
	 * @return 
	 */
	public Object revokeCgoGenuineRejectStatus(CgoGenuineRejectItemNew cgoGenuineRejectItem);

	/**
	 * 正品退货取消订单
	 * @param params
	 * @return 
	 */
	public Object cancelCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 正品退货订单出库
	 * @param params
	 * @return 
	 */
	public Object exwarehouseCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 负PO单List取得
	 * @param params
	 * @return
	 */
	public List<SIOUInfoItem> findCgoGenuineRejectDetailList(Map<String, Object> params);

	/**
	 * 负PO单List取得条数
	 * @param params
	 * @return
	 */
	public Integer findCgoGenuineRejectDetailListCNT(Map<String, Object> params);

	/**
	 * 更新订单状态，从“内部审核通过”到“待CGO评审”
	 * @param params
	 * @return 
	 */
	public Object updateCgoOrderStatusFrom20(Map<String, Object> params);

	/**
	 * @param orderID
	 * @param status
	 * @return 
	 */
	public Object updateCgoOrderStatusFrom30(Map<String, Object> params);

	/**
	 * @param params
	 * @return 
	 */
	public Object updateOrderStatusByOrderID(Map<String, Object> params);

	/**
	 * @param params
	 */
	public Object updateCgoTuiOrderStatusFrom30(Map<String, Object> params);

	/**
	 * @param params
	 */
	public Object updateRejectOrderStatusByOrderID(Map<String, Object> params);

	/**
	 * @param params
	 */
	public Object updateOrderErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	public Object updateRejectOrderErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	public Object updateSiOuInfoErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	public String findCgoStorgeId(Map<String, Object> params);

	/**
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	public List<CgoOrderItemNew> getOnwayNumByCateChan( String category_id,
													 String ed_channel_id);

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
	public List<CgoOrderItemNew> getUsedNumByCateChan(String report_year_week,
												   String category_id,
												    String ed_channel_id);

	public Integer getMaxFlowFlagFromCGO(Map param);

	public List<SIOUInfoItem> findCgoOrderListBySiou(Map param);

	/**
	 * 获取订单信息
	 * @param Map<String, Object> params
	 * @return
	 */
	public List<CgoOrderRejectItemNew> findCgoOrderRejectList(Map<String, Object> params);

	public List<SIOUInfoItem> findCgoGenuineRejectListByWporderid(Map<String, Object> params);

	/**
	 * 更新提交时间，如果已经有提交时间，那么不更新
	 * @param params
	 */
	public Object updateCgoGenuineRejectCommitTime(Map<String, Object> params);

	/**
	 * 获得统帅总单信息
	 * @param params
	 * @return
	 */
	public List<CgoOrderItemNew> getTSOrderInfo(Map params);

	/**
	 * 更新发运工厂信息和最晚离基地日期
	 * @param params
	 */
	public Object updateStatusInfo(PurchaseT2AuditResultFromCGO vo);

	/**
	 * 查询总单要求到货时间
	 * @param params
	 * @return
	 */
	public List<String> findRequestArrivalTime(Map params);

	public Object insertOrUpdateSiOrderInfo(CreateSiOuInfoVO csiv);
}
