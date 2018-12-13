package com.haier.eis.dao.eis;

import java.util.List;
import java.util.Map;

public interface OmsReceivedQueueDataDao {
    public int insert(Map<String,Object> map);
    public List<Map<String,Object>> select();
    public void update(List<Map<String,Object>> map);
}
