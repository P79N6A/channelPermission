package com.haier.svc.api.controller.purchase;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateItemForTransfer;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.DataDictionaryService;
import com.haier.svc.service.GateService;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "gatetime")
public class GateOfTimeController {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(GateOfTimeController.class);
    private final static String                  IS_ENABLED            = "is_enabled";
    @Resource(name = "dataDictionaryService")
    private DataDictionaryService dataDictionaryService;
    @Resource(name = "gateService")
    private GateService gateService;

    @RequestMapping(value = { "/getGateLoad" }, method = { RequestMethod.GET })
    String getPersonManageReportLoad(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> modelMap) {
        return "purchase/gateload";
    }

    /**
     * 跳转至时间闸口维护页面
     *
     * @param request
     */
    @RequestMapping(value = { "gotoModifyGate" }, method = { RequestMethod.GET })
    String gotoModifyGate(HttpServletRequest request, Map<String, Object> modelMap) {
        logger.debug("GateController:gotoModifyGate");
        Map<String, Object> params = new HashMap<String, Object>();
        ServiceResult<List<GateItem>> result = gateService.findGateItem(params);
        List<GateItem> timeGateData = new ArrayList<GateItem>();
        if (result.getSuccess() && result.getResult() != null && result.getResult().size() > 0) {
            timeGateData = result.getResult();
            GateItemForTransfer entity = new GateItemForTransfer();
            entity.setGateItemData((GateItem[]) timeGateData.toArray(new GateItem[0]));
            List<DataDictionary> isEnabledList = getIsEnabledList();
            modelMap.put("gateItemData", timeGateData);
            modelMap.put("isEnabledList", JsonUtil.toJson(isEnabledList));
        } else {
            logger.debug("时间闸口检索失败！");
        }
        return "purchase/gateOfTime";
    }

    /**
     * 获取是否启用list
     *
     * @return DataDictionary List<>
     */
    List<DataDictionary> getIsEnabledList() {
        List<DataDictionary> result = CommPurchase.getByValueSetId(dataDictionaryService,
                IS_ENABLED);
        return result;
    }


    /**
     * 时间闸口编辑
     *
     * @param request
     */
    @RequestMapping(value = { "modifyTimeGate" }, method = { RequestMethod.POST })
    @ResponseBody
    HttpJsonResult<String> modifyTimeGate(@ModelAttribute("filterForm") GateItemForTransfer gateItemData,
                                          @RequestParam(required = false) String updateFlag,
                                          HttpServletRequest request, HttpServletResponse response) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        ServiceResult<Boolean> updateResult = new ServiceResult<Boolean>();
        logger.debug("GateController: modifyTimeGate");

        String successMessage = "保存成功！";
        String errorMessage = "保存失败！";
        // 取得提交者
        String user = getUserName();
        gateItemData.setUser(user);

        if ("default".equals(updateFlag)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("modify_user", user);
            // 执行插入service
            updateResult = gateService.modifyDefaultTime(params);
            successMessage = "恢复默认成功！";
            errorMessage = "恢复默认失败！";
        } else {
            String is_enabled = "";
            GateItem[] timeGateArray = gateItemData.getGateItemData();
            // 循环处理启用禁用
            for (int i = 0; i < timeGateArray.length; i++) {
                if ((i & 1) == 0) {
                    is_enabled = timeGateArray[i].getIs_enabled();
                } else {
                    timeGateArray[i].setIs_enabled(is_enabled);
                    is_enabled = "";
                }
            }

            // 日志操作录入 start
            Map<String, Object> params = new HashMap<String, Object>();
            ServiceResult<List<GateItem>> resultLog = gateService.findGateItem(params);
            List<GateItem> timeGateData = new ArrayList<GateItem>();
            timeGateData = resultLog.getResult();
            GateItem[] timeGateOld = new GateItem[timeGateData.size()];
            timeGateData.toArray(timeGateOld);
            gateService.unionLog("haier_time_gate_t", timeGateOld,
                    gateItemData.getGateItemData(), "1", user, "");
            // end

            // 执行插入service
            updateResult = gateService.modifyGateItem(gateItemData);
        }
        if (updateResult.getSuccess() && updateResult.getResult() != null
                && updateResult.getResult() == true) {
            // DB操作成功
            logger.debug("时间闸口保存成功！");
            result.setTotalCount(1);
            result.setMessage(successMessage);
            // ServiceResult<List<GateItem>> gateResult =
            // gateService.findGateItem(null);
            // if (gateResult.getSuccess() && gateResult.getResult() != null
            // && gateResult.getResult().size() > 0) {
            // result = saveJobByName(gateResult.getResult(), request,
            // response);
            // }
        } else {
            // DB操作失败
            result.setTotalCount(0);
            result.setMessage(errorMessage);
        }
        return result;
    }
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }
}
