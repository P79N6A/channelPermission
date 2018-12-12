package com.haier.eis.services;


import java.util.List;

import com.haier.eis.dao.eis.VomwwwOutstockSynchronizeLogsDao;
import com.haier.eis.model.VomwwwOutstockSynchronizeLogs;
import com.haier.eis.service.VomwwwOutstockSynchronizeLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VomwwwOutstockSynchronizeLogsServiceImpl implements VomwwwOutstockSynchronizeLogsService {

    @Autowired
    VomwwwOutstockSynchronizeLogsDao vomwwwOutstockSynchronizeLogsDao;

    @Override
    public Integer insert(VomwwwOutstockSynchronizeLogs log) {
        return vomwwwOutstockSynchronizeLogsDao.insert(log);
    }

    @Override
    public List<VomwwwOutstockSynchronizeLogs> getVomwwwOutstockSynchronizeLogsList(Integer status, Integer vomwwwLogsSize, Integer vomwwwLogsErrorcount) {
        return vomwwwOutstockSynchronizeLogsDao.getVomwwwOutstockSynchronizeLogsList(status, vomwwwLogsSize, vomwwwLogsErrorcount);
    }
}
