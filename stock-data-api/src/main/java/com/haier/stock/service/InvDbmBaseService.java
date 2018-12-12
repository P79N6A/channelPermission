package com.haier.stock.service;

import com.haier.stock.model.InvDbmBase;

import java.util.List;
import java.util.Map;


public interface InvDbmBaseService {

    /**
     * 
     * @param params
     * @return
     */
    public List<InvDbmBase> selectInvDbmBaseItem(Map<String, Object> params);

    public List<String> findAllBaseStorage(Map params);

}
