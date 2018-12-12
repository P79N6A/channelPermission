/*
package com.haier.svc.api.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.order.model.ShopEnum;
import com.haier.orderthird.service.OrderThirdCenterSkuStockRelationService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.SkuStockRelation;

*/
/**
 * Created by hanhaoyang@ehaier.com on 2017/11/24 0024.
 *//*

@Controller
@RequestMapping(value = "skustockrelation/")
public class SkuStockRelationController {
    private final static org.apache.log4j.Logger log = LogManager
            .getLogger(SkuStockRelationController.class);

    @Autowired
    private OrderThirdCenterSkuStockRelationService orderThirdCenterSkuStockRelationService;

    */
/**
     * 历史查询
     *
     * @param sku
     * @param addTimeStart
     * @param request
     * @param response
     * @throws BusinessException
     * @throws IOException
     *//*

    @RequestMapping(value = {"/sku_stock_relation_list.html"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void stock_sync_log_list(
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) String addTimeStart,
            @RequestParam(required = false) String scode,
            HttpServletRequest request,
            HttpServletResponse response) throws BusinessException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        List<String> skuList = null;
        List<String> scodeList = null;
        if (StringUtil.isEmpty(sku)) {
            skuList = null;
        } else {
            skuList = getListByString(sku);
        }
        if (StringUtil.isEmpty(scode)) {
            scodeList = null;
        } else {
            scodeList = getListByString(scode);
        }
        if (StringUtil.isEmpty(addTimeStart)) {
            Date dNow = new Date();
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            Date dbefore = calendar.getTime();
            addTimeStart = sdf.format(dbefore); //格式化当前时间
        }else{
            addTimeStart = addTimeStart.replaceAll("-","");
        }
        List<SkuStockRelation> skuStockRelationList = null;
        try {
            skuStockRelationList = this.orderThirdCenterSkuStockRelationService.qryStockSyncLog(skuList, addTimeStart.trim(), scodeList);
        } catch (Exception e) {
            log.info("3w商品库存信息（历史）查询异常，异常信息：" + e.getMessage());
        }
        Map returnMap = new HashMap();
        returnMap = this.getStockReturnData(skuStockRelationList);
        Gson gson=new Gson();
        String data = gson.toJson(returnMap);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(data);
        response.getWriter().flush();
    }

    */
/**
     * 实时查询
     * @param sku
     * @param scode
     * @param rows
     * @param page
     * @param request
     * @param response
     * @throws BusinessException
     * @throws IOException
     *//*

    @ResponseBody
    @RequestMapping(value = {"sku_stock_relation_list_daily.html"}, method = {RequestMethod.POST})
    public void sku_stock_relation_list_daily(
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) String scode,
            @RequestParam(required = false) Integer rows,
            @RequestParam(required = false) Integer page,
            HttpServletRequest request,
            HttpServletResponse response) throws BusinessException, IOException {
        if (rows == null){
            rows = 20;
        }
        if (page == null)
        {
            page = 1;
        }
        List<String> skuList = new ArrayList<>();
        List<String> scodeList = new ArrayList<>();
        List<Map> showList = new ArrayList<Map>();
        try {
            if(page >0){
                page =(page - 1) * rows;
            }else {
                page = 0;
            }
            if (StringUtil.isEmpty(sku)) {
                skuList = null;
            } else {
                skuList = getListByString(sku);
            }
            if (StringUtil.isEmpty(scode)) {
                scodeList = null;
            } else {
                scodeList = getListByString(scode);
            }
            List<SkuStockRelation> skuStockRelationList = this.orderThirdCenterSkuStockRelationService.qryStockSyncLogDaily(ShopEnum.GQGYS, skuList,scodeList);
            Map returnMap = new HashMap();
            returnMap = this.getStockReturnData(skuStockRelationList);
            Gson gson=new Gson();
            System.out.println(gson.toJson(returnMap).toString());
            response.addHeader("Content-type","text/html;charset=utf-8");
            response.getWriter().write(gson.toJson(returnMap));
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e){
            log.error("错误", e);
        }
    }

    */
/**
     * 列:库位
     * 行:sku
     * Map.put(库位+sku,number)
     * 循环查找，用库位和sku确定number确定对应的数值最后组装数据
     *
     * @param list
     * @return
     *//*

    private Map getStockReturnData(List<SkuStockRelation> list) {
        Map returnMap = new HashMap();
        Set<String> scodeSet = new HashSet<String>();
        Set<String> skuSet = new HashSet<String>();
        Map skuNumberMap = new HashMap();//skuScode,number
        List<Map> stockShowList = new ArrayList<>();
        for (SkuStockRelation ssr : list) {
            SkuStockRelation s = new SkuStockRelation();
            s.setStockCode(ssr.getStockCode());
            s.setSku(ssr.getSku());
            s.setShowNum(ssr.getQuantity() + " | " + ssr.getLockQuantity());
            scodeSet.add(ssr.getStockCode());
            skuSet.add(ssr.getSku());
            skuNumberMap.put(ssr.getSku() + ssr.getStockCode(), ssr.getQuantity() + " | " + ssr.getLockQuantity());
        }
        Iterator<String> it_skuSet = skuSet.iterator();

        while (it_skuSet.hasNext()) {
            String sku = it_skuSet.next();
            Map showTypeMap = new HashMap<>();
            showTypeMap.put("sku", sku);
            for (String str : scodeSet) {
                if (null == skuNumberMap.get(sku + str)) {
                    showTypeMap.put(str,"无");
                } else {
                    showTypeMap.put(str,String.valueOf(skuNumberMap.get(sku + str)));
                }
            }
            stockShowList.add(showTypeMap);
        }

        returnMap.put("total", stockShowList.size());
        returnMap.put("scodeSet", scodeSet);
        returnMap.put("showList", stockShowList);
        return returnMap;
    }

    public List getListByString(String str) {
        List list = new ArrayList();
        if (!StringUtil.isEmpty(str,true)) {
            String temp = str.replaceAll("，", ",");
            String[] tempStr = temp.split(",");
            for (int i = 0; i < tempStr.length; i++) {
                if (!StringUtils.isEmpty(tempStr[i]))
                    list.add(tempStr[i].trim());
            }
        }
        return list;
    }
}
*/
