package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.ProduceDailyPurchaseItem;
import com.haier.purchase.data.model.PurchaseItem;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseItemDao {

	/**
	 * 根据采购订单明细编号查询记录信息
	 * @param poItemNo
	 * @return
	 */
	PurchaseItem getPurchaseItemByPoItemNo(String poItemNo);

	/**
	 * 根据订单来源号，产品Sku，库位编码查询验证是否存在采购订单明细记录
	 * @param sourceSn
	 * @param sku
	 * @param secCode
	 * @return
	 */
	Integer isExistPurchaseItem(@Param("sourceSn") String sourceSn, @Param("sku") String sku, @Param("secCode") String secCode);

	Integer insert(PurchaseItem purchaseItem);

	Integer updatePoItemNo(@Param("poItemId") int poItemId, @Param("poItemNo") String poItemNo);

	PurchaseItem get(int poItemId);

	List<PurchaseItem> getItemByPOID(int poId);

	Integer updatePurchaseItemStatusbyID(PurchaseItem purchaseItem);

	List<PurchaseItem> findPurchaseItemByPoIdAndStatus(@Param("poId") int poId, @Param("status") int status);


	List<PurchaseItem> getPurchaseItemForPD(@Param("poItemNo")String poItemNo);

	int updatePurchaseItemStatus(PurchaseItem targetItem);

	List<ProduceDailyPurchaseItem> findPurchaseItems(Map<String, Object> params);

	int getRowCnt();

	PurchaseItem getConfirmItem(int poItemId);

	Integer updateFromOMS(PurchaseItem item);

	Integer updateLessPing(@Param("id") Integer id, @Param("lesPing") String lessPing, @Param("createOrderToLessDate") Date createOrderToLessDate);

	Integer updateProdDate(PurchaseItem item);

	Integer getByPoItemNo(String poItemNo);
}
