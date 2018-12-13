package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.AllocateDefectLogs;
import com.haier.shop.model.OmsInStoreRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllocateDefectLogsWriteDao {


    void insertLog(@Param("omsId")Integer omsId, @Param("operate") String operate);

    List<AllocateDefectLogs> operateLog(Integer omsId);
}
