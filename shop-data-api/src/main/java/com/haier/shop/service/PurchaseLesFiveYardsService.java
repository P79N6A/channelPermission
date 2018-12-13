/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.shop.service;

import com.haier.shop.model.LesFiveYardInfo;

import java.util.List;
import java.util.Map;


public interface PurchaseLesFiveYardsService {

    public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params);

    LesFiveYardInfo get(Integer id);

    LesFiveYardInfo getBySCode(String sCode);

}
