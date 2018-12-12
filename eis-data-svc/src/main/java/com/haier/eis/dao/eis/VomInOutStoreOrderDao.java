package com.haier.eis.dao.eis;


import com.haier.eis.model.VomInOutStoreOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 吴坤洋 2017-12-22
 */
public interface VomInOutStoreOrderDao {
    Integer insert(VomInOutStoreOrder vomInOutStoreOrder);

    List<VomInOutStoreOrder> getByProcessStatus(@Param("process_status") Integer processStatus,
                                                @Param("delay") Integer delay);

    VomInOutStoreOrder getByRefNo(@Param("refNo") String refNo);

    Integer updateProcessStatus(@Param("id") Integer id,
                                @Param("process_status_update_to") Integer processStatusUpdateTo,
                                @Param("process_status_update_from") Integer processStatusUpdateFrom);

    Integer setDelay(@Param("id") Integer id, @Param("msg") String msg);
    
    /**
     * 获取3W正品退仓
     * @param processStatus
     * @param delay
     * @return
     */
    List<VomInOutStoreOrder> getByProcessStatus3W(@Param("process_status") Integer processStatus,
                                                  @Param("delay") Integer delay);

	List<VomInOutStoreOrder> findInTime();
	
	int queryVomInOut(String orderNo);//根据网单号查询VOM是否返回啦出入库信息
}
