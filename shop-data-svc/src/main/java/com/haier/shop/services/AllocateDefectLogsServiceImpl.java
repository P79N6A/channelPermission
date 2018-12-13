package com.haier.shop.services;

import com.google.gson.Gson;
import com.haier.shop.dao.shopwrite.AllocateDefectLogsWriteDao;
import com.haier.shop.dao.shopwrite.OmsInStoreRecordWriteDao;
import com.haier.shop.model.*;
import com.haier.shop.service.AllocateDefectLogsService;
import com.haier.shop.service.OmsInStoreRecordService;
import com.haier.stock.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllocateDefectLogsServiceImpl implements AllocateDefectLogsService {
    @Autowired
    private AllocateDefectLogsWriteDao allocateDefectLogsWriteDao;

    @Override
    public void insertLog(Integer omsId, String operate) {
        allocateDefectLogsWriteDao.insertLog(omsId,operate);
    }

    @Override
    public Map<String, Object> operateLog(String id) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AllocateDefectLogs> list = allocateDefectLogsWriteDao.operateLog(Integer.parseInt(id));// 查询调拨残次操作日志
        map.put("list", gson.toJson(list));
        return map;
    }

}
