/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.LesFiveYardInfo;

public interface LesFiveYardsDao {

    public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params);

    LesFiveYardInfo get(Integer id);

    LesFiveYardInfo getBySCode(String sCode);

}
