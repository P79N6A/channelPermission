package com.haier.shop.service;

import java.util.List;
import java.util.Map;

public interface MdmDataService {
    public int insert(List<Map<String,Object>> maps);
    public void delete();
}
