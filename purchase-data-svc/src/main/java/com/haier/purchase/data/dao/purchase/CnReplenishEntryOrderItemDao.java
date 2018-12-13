package com.haier.purchase.data.dao.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.haier.purchase.data.model.CnReplenishEntryOrderItem;

@Component
public interface CnReplenishEntryOrderItemDao {

	/**
	 * 根据补货入库单id获得记录
	 * @param id
	 * @return
	 */
	List<CnReplenishEntryOrderItem> getByReplEntryOrderId(@Param("id") Long id);


	/**
	 * 推送完成后保存结果，一共更新4个字段。itemNum,inSap,inSapTime,sapMsg
	 * @param item
	 * @return
	 */
	void updateItemAfterPush(CnReplenishEntryOrderItem item);


	/**
	 * 出库推送完成后更新状态
	 * @param itemCode
	 */
	void updateStatusAfterOutPushToSAP(CnReplenishEntryOrderItem item);

}
