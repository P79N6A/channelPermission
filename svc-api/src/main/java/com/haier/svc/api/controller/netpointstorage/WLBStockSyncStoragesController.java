package com.haier.svc.api.controller.netpointstorage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.Storages;
import com.haier.shop.model.Wlbstocksyncstorages;
import com.haier.shop.model.WlbstocksyncstoragesVo;
import com.haier.shop.service.StoragesService;
import com.haier.shop.service.WLBStockSyncStoragesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author:JinXueqian
 * @Date: 2018/7/16 16:01
 */
@Controller
@RequestMapping("stock/")
public class WLBStockSyncStoragesController {

    private static final Logger logger = LogManager.getLogger(WLBStockSyncStoragesController.class);



    @Autowired
    private WLBStockSyncStoragesService wlbStockSyncStoragesService;


    @Autowired
    private StoragesService storagesService;
    /**
     * 库位列表页面跳转
     *
     * @return
     */
    @RequestMapping(value = {"loadWLBStockSyncStorageListPage.html"}, method = {RequestMethod.GET})
    public String loadNetPointListPage() {
        return "netpointstorage/wlbStockSyncStorageList";
    }

    /**
     * 分页查询
     * @param response
     * @param rows
     * @param page
     */
    @RequestMapping(value = {"getStockSyncStorageList.html"}, method = {RequestMethod.POST})
    public void getStockSyncStorageList(HttpServletResponse response,
                                 @RequestParam(required = false) String storageName,
                                 @RequestParam(required = false) String storageCode,
                                 @RequestParam(required = false) String taoBaoStorageCode,
                                 @RequestParam(required = false) String taoBaoShop,
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
            params.put("storageName", storageName);
            params.put("storageCode", storageCode);
            params.put("taoBaoStorageCode", taoBaoStorageCode);
            params.put("taoBaoShop", taoBaoShop);

            Map<String, Object> retMap = wlbStockSyncStoragesService.getStockSyncStorageList(params);
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("网点列表失败", e);
        }
    }

    /**
     * 添加物流宝库存同步库位页面跳转
     * @return
     */
    @RequestMapping(value = {"wlbStockSyncStorageAdd"})
    public String wlbStockSyncStorageAdd() {
        return "netpointstorage/wlbStockSynStorageAdd";
    }

    /**
     * 保存物流宝库存同步库位
     * @param request
     * @return
     */
    @RequestMapping(value = {"saveWlbStockSyncStorageInfo"},method = {RequestMethod.POST})
    @ResponseBody
    public String saveWlbStockSyncStorageInfo(HttpServletRequest request, HttpServletResponse response, WlbstocksyncstoragesVo wlbstocksyncstoragesVo){

        JSONObject json = new JSONObject();

        try {
            Wlbstocksyncstorages wlbstocksyncstorages = new Wlbstocksyncstorages();
            wlbstocksyncstorages.setAddTime(new Date());
            wlbstocksyncstorages.setsCode(wlbstocksyncstoragesVo.getsCode());
            wlbstocksyncstorages.setSource(wlbstocksyncstoragesVo.getSource());
            wlbstocksyncstorages.setTaobaoStoreCode(wlbstocksyncstoragesVo.getTaobaoStoreCode());

            //根据库位编码查询id
            Storages storages = storagesService.getByCode(wlbstocksyncstoragesVo.getsCode());
            if(storages != null){
                wlbstocksyncstorages.setStorageId(storages.getId());
            }else {
                wlbstocksyncstorages.setStorageId(0);
            }
            Integer  result = wlbStockSyncStoragesService.insert(wlbstocksyncstorages);
            if (result > 0 ){
                json.put("flag",1);
                return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加失败");
        }
        json.put("flag",3);
        return json.toString();
    }

    /**
     * 删除物流宝库存同步库位
     * @param
     * @return
     */
    @RequestMapping(value = "deleteWlbStockSyncStorage" ,method = {RequestMethod.GET})
    @ResponseBody
    public String deleteWlbStockSyncStorage (@RequestParam(required = false)Integer id){
        JSONObject json = new JSONObject();
        try {
            int rows = wlbStockSyncStoragesService.deleteByPrimaryKey(id);
            if(rows > 0){
                json.put("flag",1);
                return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }

        } catch (Exception e) {
            logger.error("删除失败:物流宝库存同步库位id"+id+e.getMessage());
        }
        json.put("flag",3);
        return json.toString();
    }
}
