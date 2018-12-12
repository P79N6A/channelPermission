package com.haier.eis.service;


import com.haier.eis.model.VomInOutStoreItem;


import java.util.List;

/**
 * Created by 钊 on 2014/7/22.
 */
public interface EisVomInOutStoreItemService {
    Integer insert(VomInOutStoreItem vomInOutStoreItem);

    List<VomInOutStoreItem> getNotProcessByOrderId(Integer orderId);

    Integer updateProcessStatus(Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom);
}
