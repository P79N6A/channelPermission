package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvDbmBaseSendAddressDao;
import com.haier.stock.model.InvDbmBaseSendAddress;
import com.haier.stock.service.InvDbmBaseSendAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvDbmBaseSendAddressServiceImpl implements InvDbmBaseSendAddressService {
    @Autowired
    InvDbmBaseSendAddressDao invDbmBaseSendAddressDao;

    /**
     * @param params
     * @return
     */
    public List<InvDbmBaseSendAddress> selectInvDbmBaseSendAddressItem(Map<String, Object> params) {
        return invDbmBaseSendAddressDao.selectInvDbmBaseSendAddressItem(params);
    }

}
