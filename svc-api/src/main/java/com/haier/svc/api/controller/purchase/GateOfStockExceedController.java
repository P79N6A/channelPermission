package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.BusinessException;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.GateOfStockExceedItem;
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
import java.io.IOException;
import java.util.*;

@RequestMapping(value = "/stockexceed")
@Controller
public class GateOfStockExceedController {
    private final static org.apache.log4j.Logger logger = LogManager.getLogger(GateOfStockExceedController.class);
    private final static String                  IS_ENABLED            = "is_enabled";


    @Resource(name = "stockCommonService")
    private StockCommonService stockCommonService;
    @Resource(name = "dataDictionaryService")
    private DataDictionaryService dataDictionaryService;
    @Resource(name = "gateService")
    private GateService gateService;

    @RequestMapping(value = { "gotoModifyGateOfStockExceed" }, method = { RequestMethod.GET })
    String gotoModifyGateOfStockDelay(HttpServletRequest request, Map<String, Object> modelMap) {
        logger.debug("GateController:gotoModifyGateOfStockDelay");
        modelMap.put("channelsList", JsonUtil.toJson(getChannelsList(request)));
        modelMap.put("isEnabledList", JsonUtil.toJson(getIsEnabledList()));
        return "purchase/gateOfStockExceed";
    }


    /**
     * 获取渠道list
     *
     * @return InvStockChannel List<>
     */
    List<InvStockChannel> getChannelsList(HttpServletRequest request) {
        List<InvStockChannel> result = new ArrayList<InvStockChannel>();
        ServiceResult<List<InvStockChannel>> queryResult = stockCommonService.getChannels();
        result = queryResult.getResult();

        return result;
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
     * 库存超期闸口维护页面中为datagrid加载值
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping(value = { "findGateOfStockExceedList" }, method = { RequestMethod.POST })
    @ResponseBody
    public void findGateOfStockExceedList(HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> modelMap) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            Map<String, Object> params = new HashMap<String, Object>();

            ServiceResult<List<GateOfStockExceedItem>> result = new ServiceResult<List<GateOfStockExceedItem>>();

            // 取得渠道map
            Map<String, String> invStockChannelMap = new HashMap<String, String>();
            this.getChannelMap(invStockChannelMap, stockCommonService);

            // 取得数据字典map
            Map<String, String> valueMeaningMap = CommPurchase.getValueMeaningMap(
                    dataDictionaryService, IS_ENABLED);
            // 在DB中检索详细信息
            result = gateService.findGateOfStockExceed(params);
            // 重新组织DB中检索的数据
            if (result.getSuccess() && result.getResult() != null) {
                for (GateOfStockExceedItem gateOfStockExceedItem : result.getResult()) {
                    // 为是否启用的名称赋值
                    gateOfStockExceedItem.setIs_enabled_name(valueMeaningMap
                            .get(gateOfStockExceedItem.getIs_enabled()));
                    // 为判断方式的渠道名称赋值
                    gateOfStockExceedItem.setJudge_ed_channel_name(invStockChannelMap
                            .get(gateOfStockExceedItem.getJudge_ed_channel_id()));
                    // 为关闸方式的渠道名称赋值
                    gateOfStockExceedItem.setLimit_ed_channel_name(invStockChannelMap
                            .get(gateOfStockExceedItem.getLimit_ed_channel_id()));
                }
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("rows", result.getResult());
                response.getWriter().write(JsonUtil.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                logger.error("库存超期闸口数据检索失败！");
            }

        } catch (IOException e) {
            logger.error("错误代码：", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    private void getChannelMap(Map<String, String> invstockchannelmap,
                               StockCommonService stockCommonService) {
        //调用stockCommonService，取得渠道数据
        ServiceResult<List<InvStockChannel>> resultChannel = stockCommonService.getChannels();
        if (resultChannel.getSuccess() && resultChannel.getResult() != null) {
            List<InvStockChannel> invStockChannel = resultChannel.getResult();
            for (InvStockChannel invstockchannel : invStockChannel) {
                invstockchannelmap.put(invstockchannel.getChannelCode(), invstockchannel.getName());//将channelcode作为key，name作为value存入map中
            }
        }
    }

    /**
     * 库存超期闸口维护页面保存
     *
     * @param request
     */
    @RequestMapping(value = { "saveGateOfStockExceedList" }, method = { RequestMethod.POST })
    @ResponseBody
    HttpJsonResult<String> saveGateOfStockExceedList(@RequestParam(required = false) String allData,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        logger.debug("GateController: saveGateOfStockExceedList");

        // 取得提交者
        String user = this.getUserName();
        List<GateOfStockExceedItem> gateOfStockExceedList = new ArrayList<GateOfStockExceedItem>();
        try {
            GateOfStockExceedItem[] gateOfStockExceedArray = JSON.parse(allData,
                    GateOfStockExceedItem[].class);
            gateOfStockExceedList = Arrays.asList(gateOfStockExceedArray);
        } catch (ParseException e) {
            logger.error("saveGateOfStockExceedList中json转换为List<GateOfStockExceedItem>失败");
            logger.error("错误代码:" + e.getMessage());
            result.setMessage("json解析失败！");
            result.setTotalCount(0);
            return result;
        }
        // 修改entity数据
        int id = 0;
        for (GateOfStockExceedItem gateOfStockExceedItem : gateOfStockExceedList) {
            id++;
            gateOfStockExceedItem.setId(String.valueOf(id));
            gateOfStockExceedItem.setModify_user(user);
        }

        // 操作日志记录 start xuelin.zhao
        Map<String, Object> params = new HashMap<String, Object>();
        ServiceResult<List<GateOfStockExceedItem>> resultLog = new ServiceResult<List<GateOfStockExceedItem>>();
        resultLog = gateService.findGateOfStockExceed(params);
        // 取得渠道map
        Map<String, String> invStockChannelMap = new HashMap<String, String>();
        this.getChannelMap(invStockChannelMap, stockCommonService);
        // 取得数据字典map
        Map<String, String> valueMeaningMap = CommPurchase.getValueMeaningMap(
                dataDictionaryService, IS_ENABLED);
        for (GateOfStockExceedItem gateOfStockExceedItem : resultLog.getResult()) {
            // 为是否启用的名称赋值
            gateOfStockExceedItem.setIs_enabled_name(valueMeaningMap.get(gateOfStockExceedItem
                    .getIs_enabled()));
            // 为判断方式的渠道名称赋值
            gateOfStockExceedItem.setJudge_ed_channel_name(invStockChannelMap
                    .get(gateOfStockExceedItem.getJudge_ed_channel_id()));
            // 为关闸方式的渠道名称赋值
            gateOfStockExceedItem.setLimit_ed_channel_name(invStockChannelMap
                    .get(gateOfStockExceedItem.getLimit_ed_channel_id()));
        }

        int sizeOld = resultLog.getResult().size();// 库中数据条数
        int sizeNew = gateOfStockExceedList.size();// 页面数据条数
        String operate = "";
        // 判断页面数据条数，如果大于库中数据条数，则为新增操作
        if (sizeNew != sizeOld) {
            operate = "2";
        } else {
            operate = "1";
        }

        gateService.unionLog("haier_stock_exceed_gate_t", resultLog.getResult(),
                gateOfStockExceedList, operate, user, "");
        // end

        // 执行插入service
        ServiceResult<Boolean> insertResult = gateService
                .saveGateOfStockExceed(gateOfStockExceedList);
        if (insertResult.getSuccess() && insertResult.getResult() != null
                && insertResult.getResult() == true) {
            // DB操作成功
            result.setTotalCount(1);
            result.setMessage("保存成功！");
        } else {
            // DB操作失败
            result.setTotalCount(0);
            result.setMessage("保存失败！");
        }
        return result;
    }


    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }
    /**
     * 库存超期闸口维护页面删除数据
     *
     * @param removeIds
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "removeGateOfStockExceed" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> removeGateOfStockExceed(@RequestParam(required = false) String removeIds,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();

        List<String> removeIdList = new ArrayList<String>();
        try {
            String[] removeIdArray = JSON.parse(removeIds, String[].class);
            // 转化为list
            removeIdList = Arrays.asList(removeIdArray);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 处理需要传递删除数据
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_list", removeIdList);
        ServiceResult<Boolean> removeResult = gateService.removeGateOfStockExceed(params);
        if (removeResult.getSuccess() && removeResult.getResult() != null
                && removeResult.getResult() == true) {
            // DB操作成功
            result.setTotalCount(1);
            result.setMessage("删除成功！");
        } else {
            // DB操作失败
            result.setTotalCount(0);
            result.setMessage("删除失败！");
        }
        return result;
    }
}
