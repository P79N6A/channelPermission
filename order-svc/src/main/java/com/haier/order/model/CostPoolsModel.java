package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.shop.model.CostPools;
import com.haier.shop.service.CostPoolsService;
import com.haier.shop.service.CostPoolsUsedLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CostPoolsModel")
public class CostPoolsModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(CostPoolsModel.class);

    @Autowired
    private CostPoolsService costPoolsService;
    @Autowired
    private CostPoolsUsedLogsService costPoolsUsedLogsService;

    public Map<String,Object> findCostPoolsByPage(Map<String, Object> params) {
        //获得分页数据列表
        List<CostPools> result = costPoolsService.findCostPoolsByPage(params);
        //获得总数据数
        int resultcount = costPoolsService.getTotal();
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);

        return retMap;
    }

    public Map<String,Object> findCostPoolsUsedLogsByPage(Map<String, Object> params) {
        //获得分页数据列表
        List<CostPools> result = costPoolsUsedLogsService.findCostPoolsUsedLogsByPage(params);
        //获得总数据数
        int resultcount = costPoolsUsedLogsService.getTotal();
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    public List<Map<String,Object>> getExportCostPoolsList(Map<String, Object> paramMap) {

        return costPoolsService.getExportCostPoolsList(paramMap);
    }

    public List<Map<String,Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap) {

        return costPoolsUsedLogsService.getExportCostPoolsUsedLogsList(paramMap);
    }

    public int addCostPool(CostPools costPools) {
        return costPoolsService.addCostPool(costPools);
    }

    public ServiceResult<CostPools> findCostPoolsById(String id) {
        ServiceResult<CostPools> serviceResult = new ServiceResult<CostPools>();
        try {
            if(id==null || id==""){
                serviceResult.setSuccess(false);
                return serviceResult;
            }
            CostPools product = costPoolsService.findCostPoolsById(id);
            if(product!=null){
                serviceResult.setSuccess(true);
                serviceResult.setResult(product);
            }else{
                serviceResult.setSuccess(false);
            }
            return serviceResult;
        } catch (Exception e) {
            log.error("[CostPoolsModel][findCostPoolsById]通过id查询费用渠道发生异常，e:"+e.getMessage());
            serviceResult.setSuccess(false);
            serviceResult.setMessage("[CostPoolsModel][findCostPoolsById]通过id查询费用渠道发生异常，e:"+e.getMessage());
            return serviceResult;
        }
    }

    public Boolean updateCostPools(CostPools costPools) {
        try {
            if(costPools!=null){
                Integer row = costPoolsService.updateCostPools(costPools);
                if(row==1){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("[CostPoolsModel][updateCostPools]编辑渠道费用发生异常，e:"+e.getMessage());
            return false;
        }
    }

    public Boolean deleteCostPools(String id) {
        try {
            if(id!=null&&id.trim()!=""){
                Integer row = costPoolsService.deleteCostPools(id);
                if(row==1){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("[CostPoolsModel][updateCostPools]编辑渠道费用发生异常，e:"+e.getMessage());
            return false;
        }
    }

    public CostPools findcostPoolsByTYBC(CostPools costPools) {
        return costPoolsService.findcostPoolsByTYBC(costPools);
    }
}
