package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;
import com.haier.purchase.data.model.*;
import org.apache.ibatis.annotations.Param;



/**
 * Created by 黄俊 on 2014/8/4.
 */
public interface CgoNewDao {

	/**
	 * 获取CGOT+2查询表单
	 * @param Map<String, Object> params
	 * @return
	 */
	 List<CgoOrderItemNew> findCgoOrderList(Map<String, Object> params);

	/**
	 * 获取CGOT+2查询条数
	 * @param Map<String, Object> params
	 * @return
	 */
	 Integer findCgoOrderListCNT(Map<String, Object> params);

	/**
	           * 获得条数
	           * @return
	           */

	 public int getRowCnts();

	/**订单提交
	 * @param params
	 * @return 
	 */
	 Object updateCgoOrderStatus(Map<String, Object> params);

	/**
	 * 错误信息更新
	 * 
	 * @param params
	 * @return 
	 */
	 Object updateByQty(CgoOrderItemNew orderItem);

	/**
	 * 价格信息更新
	 * 
	 * @param params
	 * @return 
	 */
	 Object updatePrice(CgoOrderItemNew orderItem);

	/**
	 * 订单删除
	 * @param params
	 * @return 
	 */
	 Object deleteCgoOrderStatus(Map<String, Object> params);

	/**
	 * 综合查询获取T+2订单信息
	 * @param Map<String, Object> params
	 * @return
	 */
	 List<CgoOrderItemNew> findCgoOrderMultipleList(Map<String, Object> params);

	/**
	 * 综合查询获取T+2订单信息条数
	 * @param Map<String, Object> params
	 * @return
	 */
	 Integer findCgoOrderMultipleListCNT(Map<String, Object> params);

	/**
	 * 手工关单
	 * @param params
	 * @return 
	 */
	 Object manualCloseCgoOrder(Map<String, Object> params);

	/**
	 * 撤消手工关单
	 * 赵雪林 2015-1-14
	 * @param params
	 * @return 
	 */
	 Object cancelCloseCgoOrder(Map<String, Object> params);

	/**
	 * 获取PO查询信息
	 * @param Map<String, Object> params
	 * @return
	 */
	 List<SIOUInfoItem> findCgoPOList(Map<String, Object> params);

	/**
	 * 获取PO查询信息条数
	 * @param Map<String, Object> params
	 * @return
	 */
	 Integer findCgoPOListCNT(Map<String, Object> params);

	/**创建T+2单
	 * @param params
	 * @return 
	 */
	 Object insertCgoOrderList(Map<String, Object> params);

	/**审核订单
	 * @param params
	 * @return 
	 */
	 Object reviewCgoOrderList(Map<String, Object> params);

	/**
	 * 产品部审核订单
	 * @param params
	 * @return 
	 */

	 Object reviewCgoOrderDepart(Map<String, Object> params);

	/**
	 * 订单数量修改
	 * @param params
	 */
	 Integer updateCgoOrderCount(Map<String, Object> params);

	/**
	 * 撤销订单
	 * @param params
	 * @return 
	 */
	 Object updateCgoOrderRevokeFlag(Map<String, Object> params);

	/**
	 * CGO正品退货检索
	 * @param params
	 * @return
	 */
	 List<CgoGenuineRejectItemNew> findCgoGenuineRejectList(Map<String, Object> params);

	/**
	 * CGO正品退货条数
	 * @param params
	 * @return
	 */
	 Integer findCgoGenuineRejectListCNT(Map<String, Object> params);

	/**创建退货单
	 * @param params
	 * @return 
	 */
	 Object insertCgoGenuineRejectList(Map<String, Object> params);

	/**
	 * 正品退货订单删除
	 * @param params
	 * @return 
	 */
	 Object deleteCgoGenuineRejectStatus(Map<String, Object> params);

	/**正品退货订单提交
	 * @param params
	 * @return 
	 */
	 Object updateCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 正品退货撤销订单
	 * @param params
	 * @return 
	 */
	 Object revokeCgoGenuineRejectStatus(CgoGenuineRejectItemNew cgoGenuineRejectItem);

	/**
	 * 正品退货取消订单
	 * @param params
	 * @return 
	 */
	 Object cancelCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 正品退货订单出库
	 * @param params
	 * @return 
	 */
	 Object exwarehouseCgoGenuineRejectStatus(Map<String, Object> params);

	/**
	 * 负PO单List取得
	 * @param params
	 * @return
	 */
	 List<SIOUInfoItem> findCgoGenuineRejectDetailList(Map<String, Object> params);

	/**
	 * 负PO单List取得条数
	 * @param params
	 * @return
	 */
	 Integer findCgoGenuineRejectDetailListCNT(Map<String, Object> params);

	/**
	 * 更新订单状态，从“内部审核通过”到“待CGO评审”
	 * @param params
	 * @return 
	 */
	 Object updateCgoOrderStatusFrom20(Map<String, Object> params);

	/**
	 * @param orderID
	 * @param status
	 * @return 
	 */
	 Object updateCgoOrderStatusFrom30(Map<String, Object> params);

	/**
	 * @param params
	 * @return 
	 */
	 Object updateOrderStatusByOrderID(Map<String, Object> params);

	/**
	 * @param params
	 * @return 
	 */
	 Object updateCgoTuiOrderStatusFrom30(Map<String, Object> params);

	/**
	 * @param params
	 */
	 Object updateRejectOrderStatusByOrderID(Map<String, Object> params);

	/**
	 * @param params
	 */
	 Object updateOrderErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	 Object updateRejectOrderErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	 Object updateSiOuInfoErrorMessage(Map<String, Object> params);

	/**
	 * @param params
	 */
	 String findCgoStorgeId(Map<String, Object> params);

	/**
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	 List<CgoOrderItemNew> getOnwayNumByCateChan(@Param("category_id") String category_id,
													@Param("ed_channel_id") String ed_channel_id);

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
	 List<CgoOrderItemNew> getUsedNumByCateChan(@Param("report_year_week") String report_year_week,
												   @Param("category_id") String category_id,
												   @Param("ed_channel_id") String ed_channel_id);

	 Integer getMaxFlowFlagFromCGO(Map param);

	 List<SIOUInfoItem> findCgoOrderListBySiou(Map param);

	/**
	 * 获取订单信息
	 * @param Map<String, Object> params
	 * @return
	 */
	 List<CgoOrderRejectItemNew> findCgoOrderRejectList(Map<String, Object> params);

	 List<SIOUInfoItem> findCgoGenuineRejectListByWporderid(Map<String, Object> params);

	/**
	 * 更新提交时间，如果已经有提交时间，那么不更新
	 * @param params
	 */
	 Object updateCgoGenuineRejectCommitTime(Map<String, Object> params);

	/**
	 * 获得统帅总单信息
	 * @param params
	 * @return
	 */
	 List<CgoOrderItemNew> getTSOrderInfo(Map params);

	/**
	 * 更新发运工厂信息和最晚离基地日期
	 * @param params
	 */
	 Object updateStatusInfo(PurchaseT2AuditResultFromCGO vo);

	/**
	 * 查询总单要求到货时间
	 * @param params
	 * @return
	 */
	 List<String> findRequestArrivalTime(Map params);

	 Object insertOrUpdateSiOrderInfo(CreateSiOuInfoVO csiv);
}
