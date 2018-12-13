package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.InvBaseCityCodeDao;
import com.haier.purchase.data.model.InvBaseCityCode;
import com.haier.purchase.data.service.InvBaseCityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvBaseCityCodeServiceImpl implements InvBaseCityCodeService{

    @Autowired
    private InvBaseCityCodeDao invBaseCityCodeDao;
    /**
     * 获得GVS直发水家电城市编码对照信息
     *
     * @param params
     * @return
     */

    public List<InvBaseCityCode> getInvBaseCityCode(Map<String, Object> params) {
        return invBaseCityCodeDao.getInvBaseCityCode(params);
    }
}
