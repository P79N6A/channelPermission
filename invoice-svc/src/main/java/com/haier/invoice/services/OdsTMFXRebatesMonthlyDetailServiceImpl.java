package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.invoice.service.OdsTMFXRebatesMonthlyDetailService;
import com.haier.shop.model.OdsTMFXRebatesMonthlyDetail;
import com.haier.shop.service.OdsTMFXRebatesMonthlyDetailDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xupeng on 2018/5/22.
 */
@Service
public class OdsTMFXRebatesMonthlyDetailServiceImpl implements OdsTMFXRebatesMonthlyDetailService {

    @Autowired
    OdsTMFXRebatesMonthlyDetailDataService odsTMFXRebatesMonthlyDetailDataService;

    public JSONObject paging (OdsTMFXRebatesMonthlyDetail param, PagerInfo pagerInfo){
        return odsTMFXRebatesMonthlyDetailDataService.paging(param,pagerInfo);
    }
    public JSONObject export (OdsTMFXRebatesMonthlyDetail param){
        return odsTMFXRebatesMonthlyDetailDataService.export(param);
    }

    /**
     * 手动执行计算
     *
     * @param year
     * @param month
     * @param type
     * @param flag
     * @return
     */
    @Override
    public JSONObject actionToCreateData(String year, String month, String type, String flag) {
        JSONObject result = new JSONObject();
        if ("1".equals(flag)){
            //修改点位数据跟目标数据全跑一遍
            JSONObject createResule = odsTMFXRebatesMonthlyDetailDataService.createData(year, month, type);
            Boolean success = (Boolean) createResule.get("success");
            String msg = String.valueOf(createResule.get("msg"));
            String settlementLogInfo = String.valueOf(createResule.get("settlementLogInfo"));
            if (success){
                result.put("success", true);
                result.put("msg", msg);
                result.put("settlementLogInfo", settlementLogInfo);
            }else{
                result.put("success", false);
                result.put("msg", msg);
                result.put("settlementLogInfo", settlementLogInfo);
            }

        }else{
            //修改营销费用数据的时候只需要将返利汇总(产业)的营销费用计算部分,生态点汇总跟产业汇总 重新跑一遍
            if ("m".equals(type)) {
                String st = odsTMFXRebatesMonthlyDetailDataService.createPromotionCostSummary(year, month, type);
                if (StringUtils.isNotBlank(st)){
                    result.put("success", false);
                    result.put("msg", st);
                    return result;
                }
            }
            String st2 = odsTMFXRebatesMonthlyDetailDataService.createShopData(year, month, type);
            if (StringUtils.isNotBlank(st2)){
                result.put("success", false);
                result.put("msg", st2);
                return result;
            }
            odsTMFXRebatesMonthlyDetailDataService.createIndustrySummary(year, month, type);
            result.put("success", true);
            result.put("msg", "执行成功");
        }
        return result;
    }

}
