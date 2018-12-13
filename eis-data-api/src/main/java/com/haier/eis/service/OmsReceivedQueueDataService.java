package com.haier.eis.service;

import java.util.List;
import java.util.Map;

public interface OmsReceivedQueueDataService {
    public int insert(Map<String,Object> map);
    public List<Map<String,Object>> select();
    public void update(List<Map<String,Object>> map);
}
