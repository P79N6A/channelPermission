package com.haier.eis.dao.eis;

import com.haier.eis.model.VomShippingStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 作者:张波
* 2017/12/22
* */
@Service
public interface VomShippingStatusDao {
    List<VomShippingStatus> getByProcessStatus(@Param("process_status") Integer processStatus);
    Integer updateProcessStatus(@Param("id") Integer id,
                                @Param("process_status_update_to") Integer processStatusUpdateTo,
                                @Param("process_status_update_from") Integer processStatusUpdateFrom,
                                @Param("msg") String msg);
    
    /**
     * 获取3W正品
     * @param processStatus
     * @return
     */
    List<VomShippingStatus> getByProcessStatus3W(@Param("process_status") Integer processStatus);
    Integer insert(VomShippingStatus shippingStatus);
}
