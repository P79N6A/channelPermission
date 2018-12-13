package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.OdsTMFXPeculiarCategoryService;
import com.haier.shop.model.OdsTMFXPeculiarCategory;
import com.haier.shop.service.OdsTMFXPeculiarCategoryDataService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jtbshan on 2018/5/22.
 */
@Component
public  class OdsTMFXPeculiarCategoryServiceImpl implements OdsTMFXPeculiarCategoryService {
    private static final Logger log = LogManager.getLogger(OdsTMFXPeculiarCategoryServiceImpl.class);

    @Autowired
    private OdsTMFXPeculiarCategoryDataService odsTMFXPeculiarCategoryDataService;

    @Override
    public JSONObject paging (OdsTMFXPeculiarCategory param, PagerInfo pagerInfo){
        return odsTMFXPeculiarCategoryDataService.paging(param,pagerInfo);
    }
    @Override
    public JSONObject export (OdsTMFXPeculiarCategory param){
        return odsTMFXPeculiarCategoryDataService.export(param);
    }
    @Override
    public ServiceResult<JSONObject> insert (OdsTMFXPeculiarCategory param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            OdsTMFXPeculiarCategory odsTMFXPeculiarCategoryDb = odsTMFXPeculiarCategoryDataService.queryBySkuCategory(param);
            if(odsTMFXPeculiarCategoryDb !=  null){
                serviceResult.setError("error","保存失败，sku对应品类已存在");
                return serviceResult;
            }
            odsTMFXPeculiarCategoryDataService.insertSelective(param);

            serviceResult.setSuccess(true);
            serviceResult.setMessage("创建成功");

        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("创建异常",e);
        }
        return serviceResult;
    }
    @Override
    public ServiceResult<JSONObject> update (OdsTMFXPeculiarCategory param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            odsTMFXPeculiarCategoryDataService.updateByPrimaryKeySelective(param);
            serviceResult.setSuccess(true);
            serviceResult.setMessage("更新成功");

        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("更新异常",e);
        }
        return serviceResult;
    }
    @Override
    public ServiceResult<JSONObject>  delBatch(List<String> ids){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = odsTMFXPeculiarCategoryDataService.delBatch( ids);
            if(count > 0){
                serviceResult.setSuccess(true);
                serviceResult.setMessage("删除成功");
            }else {
                serviceResult.setSuccess(false);
                serviceResult.setMessage("删除失败");
            }
        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("删除异常",e);
        }
        return serviceResult;
    }




}
