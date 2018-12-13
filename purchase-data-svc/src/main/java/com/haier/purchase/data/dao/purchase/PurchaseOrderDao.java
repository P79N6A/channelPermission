package com.haier.purchase.data.dao.purchase;

import com.haier.common.PagerInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseOrderDao {
	 PurchaseOrder get(int poId);
	 public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params);

	Integer insert(PurchaseOrder purchaseOrder);

	Integer updatePoNo(@Param("poId") int poId, @Param("poNo") String poNo);

	List<PurchaseOrder> findPurchaseOrder(@Param("startTime") Date startTime,
			@Param("endTime") Date endTime,
			@Param("poNo") String poNo, @Param("status") int status,
			@Param("pager") PagerInfo pager);

	public int getRowCnts();

	Integer updatePurchaseOrderStatus(PurchaseOrder purchaseOrder);

	PurchaseOrder getConfirmOrder(int poId);

	Integer update(PurchaseOrder purchaseOrder);

	/**
	 * 获取订单网单编号数字
	 */
	Integer getNum(String type, String date);

	/**
	 * 插入获取订单网单编号数字表
	 */
	Integer insertNum(String type, String date);

	/**
	 * 更新获取订单网单编号数字表
	 */
	Integer updateNum(String type, String date);
}
