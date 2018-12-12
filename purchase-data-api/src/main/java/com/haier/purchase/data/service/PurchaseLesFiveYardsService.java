/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.LesFiveYardInfo;

public interface PurchaseLesFiveYardsService {

    public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params);

    LesFiveYardInfo get(Integer id);

    LesFiveYardInfo getBySCode(String sCode);

}
