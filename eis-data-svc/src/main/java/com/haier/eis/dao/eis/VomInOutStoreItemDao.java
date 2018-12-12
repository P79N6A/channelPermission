package com.haier.eis.dao.eis;


import com.haier.eis.model.VomInOutStoreItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 钊 on 2014/7/22.
 */
public interface VomInOutStoreItemDao {
    Integer insert(VomInOutStoreItem vomInOutStoreItem);

    List<VomInOutStoreItem> getNotProcessByOrderId(@Param("orderId") Integer orderId);

    Integer updateProcessStatus(@Param("id") Integer id,
                                @Param("process_status_update_to") Integer processStatusUpdateTo,
                                @Param("process_status_update_from") Integer processStatusUpdateFrom);
}
