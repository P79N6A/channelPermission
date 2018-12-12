package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvParam;

public interface InvParamDao {
    List<InvParam> queryParams(String group);
}
