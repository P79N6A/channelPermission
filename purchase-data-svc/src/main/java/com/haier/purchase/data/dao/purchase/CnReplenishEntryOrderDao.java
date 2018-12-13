package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.haier.common.PagerInfo;
import com.haier.purchase.data.model.CnReplenishEntryOrder;

@Component
public interface CnReplenishEntryOrderDao {

	/**
	 * 获得待推送SAP的调拨单。条件为 state=50 
	 * 从 cn_replenish_entry_order_item 关联查询 cn_replenish_entry_order
	      查询需要推送SAP数据的条件： 从 cn_replenish_entry_order_item.type=1 and cn_replenish_entry_order_item.inSap in(2,3)
	 * @param pi	分页信息
	 * @return
	 */
	List<CnReplenishEntryOrder> getToPushSAPOrders(@Param("pager") PagerInfo pi);

	List<CnReplenishEntryOrder> getEditStatusSAPOrders(@Param("lineNumList") List<String> lineNumList);
}
