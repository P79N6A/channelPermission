package com.haier.svc.api.controller.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.svc.service.BdmOrderService;
import com.haier.svc.service.ItemService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.purchase.data.model.BdmOrder;
import com.haier.shop.model.ItemBase;

@Controller
@RequestMapping(value = "purchase")
public class BdmOrderController {
    private final static org.apache.log4j.Logger logger = LogManager
                                                            .getLogger(BdmOrderController.class);
    @Autowired
    private BdmOrderService bdmOrderService;

    @Autowired
    private ItemService itemService;

    /**
     * 查询页面跳转
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "BdmOrder.html", method = { RequestMethod.GET })
    public String InvBaseSupplierMethod(HttpServletRequest request, HttpServletResponse response) {
        return "purchase/bdmOrder";
    }

    /**
     * 查询数据
     *
     * @param request
     * @param response
     * @param itemcode
     * @param itemname
     * @param smbCcode
     * @param smbName
     * @param page
     * @param rows
     */
    @RequestMapping(value = "findBdmOrder", method = { RequestMethod.POST })
    public void findBdmOrder(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = false) String itemcode,
                             @RequestParam(required = false) String itemname,
                             @RequestParam(required = false) String smbCcode,
                             @RequestParam(required = false) String smbName, Integer page,
                             Integer rows) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            if (page == null) {
                page = 1;
            }
            if (rows == null) {
                rows = 1;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("itemcode", itemcode);
            params.put("itemname", itemname);
            params.put("smbCcode", smbCcode);
            params.put("smbName", smbName);
            params.put("index", rows * (page - 1));
            params.put("rows", rows);
            ServiceResult<List<BdmOrder>> result = bdmOrderService.findBdmOrder(params);
            if (result.getSuccess() && result.getResult() != null) {
                List<BdmOrder> resultList = result.getResult();
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("total", result.getPager().getRowsCount());
                resultMap.put("rows", resultList);
                response.getWriter().write(JsonUtil.toJson(resultMap));
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (Exception ex) {
            logger.info("查询BDM样表信息时发生未知异常", ex);
        }
    }

    /**
     * 创建BDM样表数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "insertBdmOrder", method = { RequestMethod.POST })
    public void insertBdmOrder(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(required = true) String itemcode,
                               @RequestParam(required = true) String itemname,
                               @RequestParam(required = false) String customerCode,
                               @RequestParam(required = false) String customerName,
                               @RequestParam(required = false) String tradeCode) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("omsRole", "BDM");
            params.put("roleChannel", "1020");
            params.put("fromSystem", "CBS_DSA");
            params.put("itemcode", itemcode);
            params.put("itemname", itemname);
            if (!"".equals(customerCode) && customerCode != null) {
                params.put("customerCode", customerCode);
            }
            if (!"".equals(customerName) && customerName != null) {
                params.put("customerName", customerName);
            }
            if (!"".equals(customerName) && customerName != null) {
                params.put("customerName", customerName);
            }
            if (!"".equals(tradeCode) && tradeCode != null) {
                params.put("tradeCode", tradeCode);
            }
            int status = bdmOrderService.insertBdmOrder(params);

            response.getWriter().write(JsonUtil.toJson(status));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception ex) {
            logger.info("BDM创建时发生未知异常", ex);
        }
    }

    /**
     * 删除bdm样表
     *
     * @param deleteData
     * @param request
     * @param response
     */
    @RequestMapping(value = "deleteBdmOrder", method = { RequestMethod.POST })
    public void deleteBdmOrder(@RequestParam(required = true) String deleteData,
                               HttpServletRequest request, HttpServletResponse response) {
        try {
            if (deleteData != null) {
                JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
                List<String> deleteList = new ArrayList<String>();
                for (int i = 0; i < deletejson.length(); i++) {
                    deleteList.add(deletejson.getString(i));
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("itemcode", deleteList.get(0));
                ServiceResult<List<BdmOrder>> result = bdmOrderService.findBdmOrder(params);
                List<BdmOrder> resultlist = result.getResult();
                BdmOrder bdmOrder = new BdmOrder();
                bdmOrder = resultlist.get(0);
                params.put("itemname", bdmOrder.getItemname());
                params.put("omsRole", "BDM");
                params.put("roleChannel", "1020");
                params.put("fromSystem", "CBS_DSD");
                int status = bdmOrderService.deleteBdmOrder(params);
                response.getWriter().write(JsonUtil.toJson(status));
                response.getWriter().flush();
                response.getWriter().close();
            }

        } catch (Exception ex) {
            logger.error("BDM样表删除时发生未知异常", ex);
        }
    }


    @RequestMapping(value = { "getDataByItemcode" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> getDataByItemcode(HttpServletRequest request,
                                                    @RequestParam(required = true) String itemcode) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();

        if (itemcode != null && !itemcode.equals("")) {
            // 调用service执行检索
            ServiceResult<List<ItemBase>> itemResult = itemService
                    .findItemBaseByMaterialId(itemcode);
            if (itemResult.getSuccess() && itemResult.getResult() != null
                    && itemResult.getResult().size() > 0) {
                // DB操作成功
                ItemBase item = itemResult.getResult().get(0);
                // 型号
                String itemname = item.getMaterialDescription();
                Map<String, Object> Data = new HashMap<String, Object>();
                if ("".equals(itemname)) {
                    result.setMessage("通过型号编码关联型号名称失败！请维护物料表！");
                    result.setTotalCount(0);
                } else {
                    Data.put("itemname", itemname);
                    // 数据集转化为json
                    String jsonData = JsonUtil.toJson(Data);
                    // 设置返回值
                    result.setData(jsonData);
                    result.setTotalCount(1);
                }
            } else {
                // DB操作失败
                result.setMessage("型号编码非法！不存在此型号编码！");
                result.setTotalCount(0);
            }
        }
        return result;
    }

}
