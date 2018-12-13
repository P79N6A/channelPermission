package com.haier.shop.service;

import com.haier.shop.model.PersonTradeCfg;

import java.util.List;
import java.util.Map;

public interface ShopPersonTradeCfgService {
    public List<Map<String, String>> getCommissionerByArea(String area);

    public List<Map<String, String>> getTradeBycommissioner(String commissioner);

    public List<PersonTradeCfg> getAll();

    List<Map<String, String>> getLoginPersonInfo(String commissioner);

    List<Map<String, String>> getTradeAllSmallChannelPeople();

    List<Map<String, String>> getTradeAllCommissioner();

}
