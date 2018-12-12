package com.haier.stock.service;

import com.haier.stock.model.InvDbmBaseSendAddress;

import java.util.List;
import java.util.Map;


public interface InvDbmBaseSendAddressService {

    /**
     * 
     * @param params
     * @return
     */
    public List<InvDbmBaseSendAddress> selectInvDbmBaseSendAddressItem(Map<String, Object> params);

}
