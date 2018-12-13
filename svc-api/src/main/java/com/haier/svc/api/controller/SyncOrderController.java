package com.haier.svc.api.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.svc.bean.PHPSerializer;
import com.haier.system.model.SyncOrderConfigs;
import com.haier.system.service.SyncOrderConfigsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("syncOrder")
public class SyncOrderController {

    private final static Logger logger = LoggerFactory.getLogger(SyncOrderController.class);

    @Autowired
    private SyncOrderConfigsService syncOrderConfigsService;

    /**
     * 跳转到订单同步配置列表
     * @param request
     * @return
     */
        @RequestMapping(value = "toSyncOrder" ,method = {RequestMethod.GET})
    public String toSyncOrder (HttpServletRequest request){ return "order/syncOrder/syncOrderList"; }

    /**
     * 查询订单同步配置列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchSyncOrder")
    @ResponseBody
    public void search(@RequestParam(required = false) String orderSn,
                       @RequestParam(required = false) Integer rows,
                       @RequestParam(required = false) Integer page,
                       HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try{
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("orderSn",orderSn);

            Map<String,Object> retMap = syncOrderConfigsService.findSyncOrderConfigByPage(params);

            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (IOException e) {
            logger.error("", e);
            logger.error("[SyncOrderController][searchSyncOrder]异常，e:"+e.getMessage());
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 跳转到新增订单同步配置页面
     * @param request
     * @return
     */
    @RequestMapping(value = "toAddSyncOrder" ,method = {RequestMethod.GET})
    public String toAddSyncOrder (HttpServletRequest request){ return "order/syncOrder/syncOrderAdd"; }

    /**
     * 新增订单同步配置逻辑
     * @param request
     * @return
     */
    @RequestMapping(value = "addSyncOrder" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addSyncOrder (
            String shopName, String configType, String autoSync, String useReceipt,
            String receiptTitle, String appKey, String secret, String session,
            HttpServletRequest request){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try {
            if(StringUtils.isEmpty(shopName)){
                json.put("text","外部网店名称为空");
                return json.toString();
            }
            SyncOrderConfigs fsy = syncOrderConfigsService.findOrderConfigsByShopName(shopName);
            if(fsy!=null){
                json.put("text","外部网店名称已存在");
                return json.toString();
            }
            if(StringUtils.isEmpty(configType)){
                json.put("text","没有选择外部网店类型");
                return json.toString();
            }
            if(StringUtils.isEmpty(autoSync)){
                json.put("text","没有选择是否自动处理");
                return json.toString();
            }
            if(StringUtils.isEmpty(useReceipt)){
                json.put("text","没有选择是否使用发票");
                return json.toString();
            }
            if(StringUtils.isEmpty(appKey)||StringUtils.isEmpty(secret)||StringUtils.isEmpty(session)){
                json.put("text","appKey secret session 不能为空");
                return json.toString();
            }

            receiptTitle = StringUtil.nullSafeString(receiptTitle);
            Map map = new LinkedHashMap<String,Object>();
            map.put("appKey",appKey);
            map.put("secret",secret);
            map.put("session",session);
            map.put("autoToWms",false);
            if("1".equals(useReceipt)){
                map.put("useReceipt",true);
            }else{
                map.put("useReceipt",false);
            }
            map.put("receiptTitle",receiptTitle);

            byte[] a = PHPSerializer.serialize(map,"UTF-8");
            String configValue = new String(a);
            SyncOrderConfigs syncOrderConfigs = new SyncOrderConfigs();
            syncOrderConfigs.setShopName(shopName);
            syncOrderConfigs.setSiteId(1);
            syncOrderConfigs.setConfigType(configType);
            syncOrderConfigs.setAutoSync(Integer.valueOf(autoSync));
            syncOrderConfigs.setConfigValue(configValue);
            syncOrderConfigs.setSyncCount(1);
            syncOrderConfigs.setLimitcount(60);
            Integer row = syncOrderConfigsService.addSyncOrderConfigs(syncOrderConfigs);
            if(row==null||row<1){
                json.put("flag",0);
                json.put("text","添加订单同步配置信息失败");
                return json.toString();
            }

            json.put("flag",1);
            logger.info("新增订单同步配置信息");

        }catch (Exception e){
            logger.error("[SyncOrderController][addSyncOrder]异常，e:"+e.getMessage());
            json.put("flag",0);
            json.put("text","添加异常请联系后台管理员");
            return json.toString();
        }

        return json.toString();
    }

    /**
     * 跳转到修改订单同步配置页面
     *
     * @return
     */
    @RequestMapping(value = "toUpdateSyncOrder" ,method = {RequestMethod.GET})
    public String toUpdateSyncOrder (@RequestParam(required = false)String id, Model modal){
        try{
            ServiceResult<SyncOrderConfigs> OrderConfigs = syncOrderConfigsService.findOrderConfigsById(id);

            if(OrderConfigs.getSuccess()){
                SyncOrderConfigs result = OrderConfigs.getResult();
                modal.addAttribute("SyncOrderConfigs", result);
                Map map = (Map)PHPSerializer.unserialize(result.getConfigValue().getBytes("UTF-8"));
                System.out.println(map.get("receiptTitle"));

                modal.addAttribute("ConfigValue", map);
            }
            return "order/syncOrder/SyncOrderEdit";
        }catch (Exception e){
            return "order/syncOrder/SyncOrderEdit";
        }

    }

    /**
     * 修改订单同步配置逻辑
     * @param request
     * @return
     */
    @RequestMapping(value = "updateSyncOrder" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String updateSyncOrder (String id ,String shopName, String configType, String autoSync, String useReceipt,
                                    String receiptTitle, String appKey, String secret, String session,
                                    HttpServletRequest request){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try {
            request.setCharacterEncoding("UTF-8");
            if(StringUtils.isEmpty(id)){
                json.put("text","数据ID异常");
                return json.toString();
            }
            if(StringUtils.isEmpty(shopName)){
                json.put("text","外部网店名称为空");
                return json.toString();
            }
            SyncOrderConfigs fsy = syncOrderConfigsService.findOrderConfigsByShopName(shopName);
            if(fsy!=null&&!(fsy.getId().equals(Integer.valueOf(id)))){
                json.put("text","外部网店名称已存在");
                return json.toString();
            }
            if(StringUtils.isEmpty(configType)){
                json.put("text","没有选择外部网店类型");
                return json.toString();
            }
            if(StringUtils.isEmpty(autoSync)){
                json.put("text","没有选择是否自动处理");
                return json.toString();
            }
            if(StringUtils.isEmpty(useReceipt)){
                json.put("text","没有选择是否使用发票");
                return json.toString();
            }
            if(StringUtils.isEmpty(appKey)||StringUtils.isEmpty(secret)||StringUtils.isEmpty(session)){
                json.put("text","appKey secret session 不能为空");
                return json.toString();
            }
            System.out.println(receiptTitle+"+1");
            receiptTitle = StringUtil.nullSafeString(receiptTitle);
            Map map = new LinkedHashMap<String,Object>();
            map.put("appKey",appKey);
            map.put("secret",secret);
            map.put("session",session);
            map.put("autoToWms",false);
            if("1".equals(useReceipt)){
                map.put("useReceipt",true);
            }else{
                map.put("useReceipt",false);
            }
            map.put("receiptTitle",receiptTitle);

            System.out.println(shopName+"+2");
            System.out.println(receiptTitle+"+3");
            System.out.println(map.get("receiptTitle")+"+4");
            byte[] a = PHPSerializer.serialize(map,"UTF-8");
            String configValue = new String(a);
            SyncOrderConfigs syncOrderConfigs = new SyncOrderConfigs();
            syncOrderConfigs.setId(Integer.valueOf(id));
            syncOrderConfigs.setShopName(shopName);

            syncOrderConfigs.setConfigType(configType);
            syncOrderConfigs.setAutoSync(Integer.valueOf(autoSync));
            syncOrderConfigs.setConfigValue(configValue);

            Integer row = syncOrderConfigsService.updateSyncOrderConfigsById(syncOrderConfigs);
            if(row==null||row<1){
                json.put("flag",0);
                json.put("text","更新订单同步配置信息失败");
                return json.toString();
            }

            json.put("flag",1);
            logger.info("更新订单同步配置信息");
            return json.toString();
        } catch (Exception e) {
            json.put("flag",0);
            json.put("text","添加异常请联系后台管理员");
            return json.toString();
        }
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteSyncOrder" ,method = {RequestMethod.GET})
    @ResponseBody
    public Integer deleteSyncOrder (@RequestParam(required = false)String id,HttpServletRequest request){
        try {
            if(id!=null||id.trim()!=""){
                syncOrderConfigsService.deleteSyncOrderConfigsById(id);
                logger.info("删除订单同步配置信息");
                return 1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("[SyncOrderController][deleteSyncOrder]异常，e:"+e.getMessage());
            return 2;
        }
    }


}
