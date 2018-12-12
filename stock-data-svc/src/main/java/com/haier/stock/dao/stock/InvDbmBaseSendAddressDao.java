package com.haier.stock.dao.stock;

import com.haier.stock.model.InvDbmBaseSendAddress;

import java.util.List;
import java.util.Map;



public interface InvDbmBaseSendAddressDao {

    /**
     * 
     * @param params
     * @return
     */
    public List<InvDbmBaseSendAddress> selectInvDbmBaseSendAddressItem(Map<String, Object> params);

}
