package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.ExternalOrders;
import com.haier.shop.model.ExternalOrdersVo;
import com.haier.shop.service.ExternalOrdersService;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.TradeOrderUtils;
import com.haier.svc.bean.ShopEnum;
import com.haier.system.service.SyncOrderConfigsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 3W订单接入人工处理
 * @Author:JinXueqian
 * @Date: 2018/8/8 15:51
 */

@Controller
@RequestMapping("order/")
public class ExternalOrdersController {

    private static final Logger logger = LogManager.getLogger(ExternalOrdersController.class);

    @Autowired
    ExternalOrdersService externalOrdersService;
    @Autowired
    private SyncOrderConfigsService syncOrderConfigsService;

    /**
     * 3W订单接入人工处理列表页面跳转
     * @return
     */
    @GetMapping(value = {"loadExternalOrderListPage.html"})
    public String loadExternalOrderListPage() {
        return "order/externalOrdersList";
    }

    /**
     * 分页查询错误来源订单
     * @param response
     * @param rows
     * @param page
     */
    @PostMapping(value = {"getExternalOrderList.html"})
    public void getStockSyncStorageList(HttpServletResponse response,
                                        @RequestParam(required = false) String taoBaoShop,
                                        @RequestParam(required = false) Integer orderState,
                                        @RequestParam(required = false) String sourceOrderSn,
                                        @RequestParam(required = false) String addTimeMin,
                                        @RequestParam(required = false) String addTimeMax,
                                        @RequestParam(required = false) Integer rows,
                                        @RequestParam(required = false) Integer page) {

        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("sourceOrderSn", sourceOrderSn);
            params.put("addTimeMin", addTimeMin);
            params.put("addTimeMax", addTimeMax);
            params.put("taoBaoShop", taoBaoShop);
            params.put("orderState", orderState);
            Integer resultCount = externalOrdersService.findExternalOrdersCNT(params);
            List<ExternalOrdersVo> externalOrdersList = externalOrdersService.getExternalOrdersList(params);
            if(externalOrdersList != null && externalOrdersList.size() > 0){
                for (ExternalOrdersVo externalOrdersVo : externalOrdersList) {
                    Integer configId = externalOrdersVo.getConfigId();
                   String shopName = syncOrderConfigsService.selectShopNameById(configId);
                   externalOrdersVo.setShopName(shopName);
                }
            }
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("total", resultCount);
            retMap.put("rows", externalOrdersList);
            response.setContentType("application/json;charset=utf-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("网点列表失败", e);
        }
    }
    /**
     * 添加3W订单接入人工处理页面跳转
     * @return
     */
    @RequestMapping(value = {"externalOrdersAdd.html"})
    public String externalOrdersAdd() {
        return "order/externalOrdersAdd";
    }

    /**
     * 添加3W订单接入人工处理
     * @param sourceOrderSn
     * @param source
     * @param type
     * @param response
     * @return
     */
    @PostMapping(value = { "/saveExternalOrdersInfo.html" })
    @ResponseBody
    public String saveExternalOrdersInfo(@RequestParam(required = false) String sourceOrderSn,
                                         @RequestParam(required = false) String source,
                                       @RequestParam(required = false) String type,
                                         HttpServletResponse response){
        JSONObject json = new JSONObject();
        try {

           //source ShopEnum里的值
           ShopEnum shopEnum = ShopEnum.valueOf(source);
           ExternalOrders extOrder = TradeOrderUtils.convertTrade2ExtOrdersBySourceOrderSn_old(sourceOrderSn, shopEnum,type);

           int result = externalOrdersService.insertExternalOrdersInfo(extOrder);
           if (result > 0 ){
               //成功
               json.put("flag",1);
               return json.toString();
           }else {
               //失败
               json.put("flag",2);
               return json.toString();
           }

       }catch (Exception e){
            e.printStackTrace();
            logger.error("添加失败");
       }
       //失败,发生异常
        json.put("flag",3);
        return json.toString();
    }

    /**
     * 修改3W订单接入人工处理
     * @param sourceOrderSn
     * @param orderState
     * @param errorLog
     * @param response
     */
    @PostMapping(value = { "/updateExternalOrdersInfo.html" })
    @ResponseBody
    public String updateExternalOrdersInfo(@RequestParam(required = false) String sourceOrderSn,
                                   @RequestParam(required = false) Integer orderState,
                                   @RequestParam(required = false) String errorLog,
                                   HttpServletResponse response){

        if(orderState == null){
            orderState = 1001;
        }
        errorLog = StringUtil.nullSafeString(errorLog);
        JSONObject json = new JSONObject();
        try {
            Integer result = externalOrdersService.updateShopOrdersInfo(sourceOrderSn,orderState,errorLog);

        if (result > 0){
            //成功
            json.put("flag",1);
            return json.toString();
        }else {
            //失败
            json.put("flag",2);
            return json.toString();
        }

    }catch (Exception e){
        e.printStackTrace();
        logger.error("修改失败");
    }
        //失败,发生异常
        json.put("flag",3);
        return json.toString();
    }
}
