package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.DistributionDetailsService;
import com.haier.invoice.service.OdsTMFXPointMaintainService;
import com.haier.shop.model.DistributionDetails;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.shop.service.DistributionDetailsDataService;
import com.haier.shop.service.OdsTMFXPointMaintainDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtbshan on 2018/5/22.
 */
@Component
public  class DistributionDetailsServiceImpl implements DistributionDetailsService {
    private static final Logger log = LogManager.getLogger(DistributionDetailsServiceImpl.class);

    @Autowired
    private DistributionDetailsDataService distributionDetailsDataService;


    public JSONObject paging (DistributionDetails param, PagerInfo pagerInfo){
        return distributionDetailsDataService.paging(param,pagerInfo);
    }
    public JSONObject export (DistributionDetails param){
        return distributionDetailsDataService.export(param);
    }



}
