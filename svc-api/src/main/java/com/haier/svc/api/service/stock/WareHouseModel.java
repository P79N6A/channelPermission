package com.haier.svc.api.service.stock;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.stock.service.StockCenterInvWareHouseService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WareHouseModel {

    private Logger logger = LogManager.getLogger(WareHouseModel.class);
    @Autowired
    private StockCenterInvWareHouseService stockCenterInvWareHouseService;

    public ServiceResult<Map<String, Integer>> insertInvSections(
            List<InvSection> invSections) {
        ServiceResult<Map<String, Integer>> insResult = stockCenterInvWareHouseService.insertInvSections(invSections);
        return insResult;
    }

    public ServiceResult<Map<String, Integer>> insertInvWarehouses(
            List<PopInvWarehouse> invWarehouses) {
        ServiceResult<Map<String, Integer>> insResult = stockCenterInvWareHouseService.insertInvWarehouses(invWarehouses);
        return insResult;
    }
}
