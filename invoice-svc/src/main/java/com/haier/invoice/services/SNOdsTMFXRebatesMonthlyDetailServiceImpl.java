package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.invoice.service.SNOdsTMFXRebatesMonthlyDetailService;
import com.haier.shop.model.SNOdsTMFXRebatesMonthlyDetail;
import com.haier.shop.service.SNOdsTMFXRebatesMonthlyDetailDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xupeng on 2018/5/22.
 */
@Service("SNOdsTMFXRebatesMonthlyDetailServiceImpl")
public class SNOdsTMFXRebatesMonthlyDetailServiceImpl implements SNOdsTMFXRebatesMonthlyDetailService {

    @Autowired
    SNOdsTMFXRebatesMonthlyDetailDataService sNOdsTMFXRebatesMonthlyDetailDataService;

    public JSONObject paging (SNOdsTMFXRebatesMonthlyDetail param, PagerInfo pagerInfo){
        return sNOdsTMFXRebatesMonthlyDetailDataService.paging(param,pagerInfo);
    }
    public JSONObject export (SNOdsTMFXRebatesMonthlyDetail param){
        return sNOdsTMFXRebatesMonthlyDetailDataService.export(param);
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
            JSONObject createResule = sNOdsTMFXRebatesMonthlyDetailDataService.createData(year, month, type);
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
                String st = sNOdsTMFXRebatesMonthlyDetailDataService.createPromotionCostSummary(year, month, type);
                if (StringUtils.isNotBlank(st)){
                    result.put("success", false);
                    result.put("msg", st);
                    return result;
                }
            }
            String st2 = sNOdsTMFXRebatesMonthlyDetailDataService.createShopData(year, month, type);
            if (StringUtils.isNotBlank(st2)){
                result.put("success", false);
                result.put("msg", st2);
                return result;
            }
            sNOdsTMFXRebatesMonthlyDetailDataService.createIndustrySummary(year, month, type);
            result.put("success", true);
            result.put("msg", "执行成功");
        }
        return result;
    }

}
