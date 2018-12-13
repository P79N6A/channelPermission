package com.haier.shop.service;

import com.haier.shop.dto.RegionsDTO;

import com.haier.shop.model.O2OOrderTailendQueues;
import java.util.List;
import java.util.Map;

public interface AddRessDataService {
    public List<RegionsDTO> getRegionsAll();
    public List<Map<String,Object>> getProductCates();

    List<RegionsDTO> getRegionsParentId(String parentId);

    O2OOrderTailendQueues getTailendToAccountCenterByDepositOrderProductId(Integer depositOrderProductId);
}
