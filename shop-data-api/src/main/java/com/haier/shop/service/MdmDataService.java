package com.haier.shop.service;

import com.haier.shop.model.SysMdmVO;

import java.util.List;
import java.util.Map;

public interface MdmDataService {
    public SysMdmVO queryBySku(String sku);
    public int insert(List<Map<String,Object>> maps);
    public void delete();
}
