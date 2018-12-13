package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.PurchaseOrderQueue4Daily;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseOrderQueue4DailyDao {

  Integer update(PurchaseOrderQueue4Daily queue4Daily);

  Integer updateMsg(@Param("id") Integer id, @Param("msg") String msg);

  List<PurchaseOrderQueue4Daily> getByStatus(@Param("status") Integer status);

  Integer insert(PurchaseOrderQueue4Daily purchaseOrderQueue4Daily);

  List<PurchaseOrderQueue4Daily> getQueues(@Param("refNos") List<String> refNos);

  Integer updateStatus(@Param("id") Integer id, @Param("newStatus") Integer newStatus,
      @Param("oldStatus") Integer oldStatus);

  List<PurchaseOrderQueue4Daily> getByOrderStatus();

  Integer updateOrderStatus(@Param("id") Integer id, @Param("newStatus") Integer newStatus,
      @Param("oldStatus") Integer oldStatus);

  List<PurchaseOrderQueue4Daily> getBySyncStatus();

  Integer updateSyncStatus(@Param("id") Integer id, @Param("newStatus") Integer newStatus,
      @Param("oldStatus") Integer oldStatus);

  List<PurchaseOrderQueue4Daily> getForSyncProdDateFromEDW();

  Integer updateSyncProdDateStatus(@Param("id") Integer id, @Param("newStatus") Integer newStatus,
      @Param("oldStatus") Integer oldStatus);
}
