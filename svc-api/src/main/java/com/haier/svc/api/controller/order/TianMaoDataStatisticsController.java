package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.Producttypes;
import com.haier.shop.service.ProductTypesService;
import com.haier.shop.service.TianMaoDataStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 天猫系销售额，出库额，开票额,推送sap额统计
 * @Author:JinXueqian
 * @Date: 2018/8/8 15:51
 */

@Controller
@RequestMapping("order/")
public class TianMaoDataStatisticsController {

    private static final Logger logger = LogManager.getLogger(TianMaoDataStatisticsController.class);

    @Autowired
    TianMaoDataStatisticsService tianMaoDataStatisticsService;
    @Autowired
    ProductTypesService productTypesService;



    /**
     * 天猫系销售额，出库额，开票额,推送sap额统计页面跳转
     * @return
     */
    @GetMapping(value = {"loadTianMaoDataStatisticsPage.html"})
    public String loadExternalOrderListPage() {
        return "order/tianMaoDataStatistics";
    }

    /**
     * 查询商品类型
     * @param response
     */
    @PostMapping(value = {"getProductTypes.html"})
    public void getProductTypes(HttpServletResponse response){

        try {
            List<Producttypes> producttypesList = productTypesService.getProducttypes();
            response.setContentType("application/json;charset=utf-8");
            String json = JSON.toJSONString(producttypesList);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }catch (Exception e){
            logger.error("商品类型查询失败", e);
            e.printStackTrace();
        }

    }

    /**
     * 点击弹出层的确定按钮后在表格中追加商品分类
     * @param request
     * @param response
     * @param data
     * @return
     */
    @PostMapping(value = {"appendProducttypes"})
    @ResponseBody
    public JSONArray appendProducttypes(HttpServletRequest request, HttpServletResponse response, String data){
        JSONArray jsonArray = new JSONArray();
        List<String> list=new ArrayList<>();
        //把String转换为json
        net.sf.json.JSONArray jsonArray1 =  net.sf.json.JSONArray.fromObject(data);
        //这里的t是Class<T>
        if (jsonArray1.size()!=0){
            for (int i=0;i<jsonArray1.size();i++){
                String str=jsonArray1.get(i).toString();
                list.add(str);
            }
        }

        List<Producttypes> producttypesList = new ArrayList<>();
        //根据id查询producttypes
        if(list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                String id = list.get(i);
                List<Producttypes> producttypesList1  = productTypesService.selectByProducttypesSku(Integer.parseInt(id));
                producttypesList.addAll(producttypesList1);
            }
        }
        System.out.println(producttypesList.size());
        if(producttypesList != null && producttypesList.size() > 0){

            for (Producttypes producttypes : producttypesList) {
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("id",producttypes.getId());
                jsonObject.put("typeName",producttypes.getTypeName());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 点击弹出层的搜索按钮执行的函数,根据商品分类名称搜索
     * @param request
     * @param response
     * @param typeName
     * @return
     */
    @RequestMapping(value = {"getProductttypesByTypeName"})
    @ResponseBody
    public JSONArray getProductttypesByTypeName(HttpServletRequest request, HttpServletResponse response,
                                       String typeName){
        Map<String,Object> map=new HashMap<>();

        List<Map<String,Object>> list=productTypesService.getProductttypesByTypeName(typeName);
        JSONArray jsonArray = new JSONArray();
        for (Map m:list){
            JSONObject jsonObject =new JSONObject();
            String id=m.get("id").toString();
            String typeName1=m.get("typeName").toString();
            jsonObject.put("id",id);
            jsonObject.put("typeName",typeName1);
            String remove="<a >删除</a>";
            jsonObject.put("operation",remove);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    /**
     * 查询天猫系销售额，出库额，开票额,推送sap额统计
     * @param response
     * @param rows
     * @param page
     */
    @PostMapping(value = {"getTianMaoDataStatistics.html"})
    public void getTianMaoDataStatistics(HttpServletResponse response,
                                        @RequestParam(required = false) String producttypesIds,
                                        @RequestParam(required = false) String addTimeMin,
                                        @RequestParam(required = false) String addTimeMax,
                                        @RequestParam(required = false) Integer rows,
                                        @RequestParam(required = false) Integer page) {

        try {
            List<String> producttypesIdsList =null;
            if(StringUtils.isNotEmpty(producttypesIds)){
               String[] producttypesIdsArray = producttypesIds.split(",");
                producttypesIdsList = Arrays.asList(producttypesIdsArray);
            }

            Map<String, Object> params = new HashMap();

            params.put("addTimeMin", addTimeMin);
            params.put("addTimeMax", addTimeMax);
            params.put("producttypesIds", producttypesIdsList);

            List<Map<String,Object>> retMap = tianMaoDataStatisticsService.getTianMaoDataStatistics(params);

            response.setContentType("application/json;charset=utf-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("天猫系销售额，出库额，开票额,推送sap额统计查询失败", e);
        }
    }

}
