package com.haier.stock.dao.stock;

import com.haier.stock.model.InvDbmBase;

import java.util.List;
import java.util.Map;



public interface InvDbmBaseDao {

    /**
     * 
     * @param params
     * @return
     */
    public List<InvDbmBase> selectInvDbmBaseItem(Map<String, Object> params);

    public List<String> findAllBaseStorage(Map params);

}
