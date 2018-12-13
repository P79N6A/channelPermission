package com.haier.shop.services;

import com.haier.shop.dao.shopread.PersonTradeCfgReadDao;
import com.haier.shop.model.PersonTradeCfg;
import com.haier.shop.service.ShopPersonTradeCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ShopPersonTradeCfgServiceImpl implements ShopPersonTradeCfgService {

    @Autowired
    private PersonTradeCfgReadDao personTradeCfgReadDao;

    @Override
    public List<Map<String, String>> getCommissionerByArea(String area) {
        return personTradeCfgReadDao.getCommissionerByArea(area);
    }

    @Override
    public List<Map<String, String>> getTradeBycommissioner(String commissioner) {
        return personTradeCfgReadDao.getTradeBycommissioner(commissioner);
    }

    @Override
    public List<PersonTradeCfg> getAll() {
        return personTradeCfgReadDao.getAll();
    }

    @Override
    public List<Map<String, String>> getLoginPersonInfo(String commissioner) {
        return personTradeCfgReadDao.getLoginPersonInfo(commissioner);
    }

    @Override
    public List<Map<String, String>> getTradeAllSmallChannelPeople() {
        return personTradeCfgReadDao.getTradeAllSmallChannelPeople();
    }

    @Override
    public List<Map<String, String>> getTradeAllCommissioner() {
        return personTradeCfgReadDao.getTradeAllCommissioner();
    }




}
