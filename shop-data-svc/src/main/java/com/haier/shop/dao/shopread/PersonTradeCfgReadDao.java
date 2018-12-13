package com.haier.shop.dao.shopread;

import com.haier.shop.model.PersonTradeCfg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface PersonTradeCfgReadDao {
    public List<Map<String, String>> getCommissionerByArea(String area);

    public List<Map<String, String>> getTradeBycommissioner(String commissioner);

    public List<PersonTradeCfg> getAll();

    List<Map<String, String>> getLoginPersonInfo(@Param("commissioner") String commissioner);

    List<Map<String, String>> getTradeAllSmallChannelPeople();

    List<Map<String, String>> getTradeAllCommissioner();


}
