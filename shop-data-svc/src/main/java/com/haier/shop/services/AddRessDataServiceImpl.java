package com.haier.shop.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.shop.dao.shopread.AccountCenterReadDao;
import com.haier.shop.dao.shopread.RegionsReadDao;
import com.haier.shop.dto.RegionsDTO;
import com.haier.shop.model.O2OOrderTailendQueues;
import com.haier.shop.service.AddRessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddRessDataServiceImpl implements AddRessDataService{
    @Autowired
    private RegionsReadDao getRegions;
    @Autowired
    private AccountCenterReadDao accountCenterReadDao;
    public List<RegionsDTO> getRegionsAll() {
        return getRegions.getRegions2();
    }
    public List<Map<String,Object>> getProductCates(){
        return accountCenterReadDao.getProductCatesOrderBy();
    }

    @Override
    public List<RegionsDTO> getRegionsParentId(String parentId) {

        if(StringUtils.isEmpty(parentId)){
            return getRegions.getRegionsProvince();
        }

        return getRegions.getRegionsParentId(parentId);

    }

    /**
     * 更具网单号ID DepositOrderProductId 获取 o2o已付尾款订单队列表
     */
    @Override
    public O2OOrderTailendQueues getTailendToAccountCenterByDepositOrderProductId(Integer depositOrderProductId){
        return accountCenterReadDao.getTailendToAccountCenterByDepositOrderProductId(depositOrderProductId);
    }
}
