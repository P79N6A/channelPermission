package com.haier.svc.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.InvSection;
import com.haier.stock.service.StockInvSectionService;
import com.haier.svc.service.InvSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvSectionServiceImpl implements InvSectionService{
    @Autowired
    private StockInvSectionService stockInvSectionService;

    @Override
    public JSONObject getInvSectionList(InvSection invSection) {
        return stockInvSectionService.getInvSectionList(invSection);
    }

    @Override
    public List<InvSection> queryInvSectionExcel(InvSection invSection) {
        return stockInvSectionService.queryInvSectionExcel(invSection);
    }
}
