/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.shop.dao.shopread;

import com.haier.shop.model.LesFiveYardInfo;

import java.util.List;
import java.util.Map;


public interface LesFiveYardsDao {

    public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params);

    LesFiveYardInfo get(Integer id);

    LesFiveYardInfo getBySCode(String sCode);

}
