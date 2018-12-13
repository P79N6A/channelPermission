package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.InvBaseCityCode;

import java.util.List;
import java.util.Map;

public interface InvBaseCityCodeDao {

    List<InvBaseCityCode> getInvBaseCityCode(Map<String, Object> params);
}

