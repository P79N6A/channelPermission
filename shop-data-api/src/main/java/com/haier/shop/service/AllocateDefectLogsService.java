package com.haier.shop.service;

import com.haier.shop.model.OmsInStoreRecords;

import java.util.List;
import java.util.Map;

public interface AllocateDefectLogsService {

    void insertLog(Integer omsId, String operate);

    Map<String,Object> operateLog(String id);
}
