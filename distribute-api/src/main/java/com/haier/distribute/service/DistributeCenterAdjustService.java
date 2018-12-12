package com.haier.distribute.service;
/**
 * Created by Administrator on 2017/11/16 0016.
 */

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.TAdjustData;

import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 16:19
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface DistributeCenterAdjustService {
    JSONObject productList(PagerInfo pager, TAdjustData condition);

    Map<String, Object> update(String id, String type);
    
    List<TAdjustData> exportAdjustList(TAdjustData tad);

}
