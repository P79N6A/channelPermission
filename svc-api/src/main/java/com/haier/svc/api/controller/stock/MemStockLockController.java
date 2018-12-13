package com.haier.svc.api.controller.stock;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.model.Ustring;
import com.haier.stock.model.InvStockLockEx;
import com.haier.svc.api.controller.stock.mode.MemStockLockModel;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("memStockLock")
public class MemStockLockController {
    private Logger            logger = LogManager.getLogger(MemStockLockController.class);
    @Autowired
    private MemStockLockModel memStockLockModel;

    @RequestMapping("/loadStockLockPage")
    String loadStockLockPage() {
        return "stock/memStockLock";
    }

    @RequestMapping(value = { "/loadStockLockListPage" }, method = { RequestMethod.GET })
    String loadStockLockList() {
        return "stock/stockLockList";
    }

    @RequestMapping(value = { "/lockStock" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<String> lockStock(Map<String, Object> modelMap, HttpServletRequest request,
                                     HttpServletResponse response) {
        logger.info("手动锁定库存");
        String sku = request.getParameter("sku").trim().toUpperCase();
        String secCode = request.getParameter("secCode").trim().toUpperCase();
        String frozenQty = request.getParameter("frozenQty");
        String checkrode = request.getParameter("refNos");
        String lockUser = request.getParameter("lockUser");
        String wdNo;
        if(checkrode == null || "".equals(checkrode)){
        	wdNo = memStockLockModel.generateRefNo();
        }else{
        	wdNo = checkrode;
        }

        if(lockUser == null || "".equals(lockUser)){
        	lockUser = WebUtil.currentUserName(request);
        }
        ServiceResult<Boolean> result = memStockLockModel.lockStock(sku, secCode,
            Integer.parseInt(frozenQty), wdNo, null, lockUser);

        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        jsonResult.setData(wdNo);
        jsonResult.setMessage(result.getMessage());
        return jsonResult;
    }
    /**
     * 释放库存列表
     * @param request
     * @param modelMap
     * @param itemProperty
     * @param pageIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value= {"/queryStockLockListPage"}, method = { RequestMethod.GET })
    JSONObject  queryMemStockLockList(HttpServletRequest request, Map<String, Object> modelMap,
                                 @RequestParam(required = false) String itemProperty,
                                 @RequestParam(required = false) Integer pageIndex) {
    	JSONObject json = new JSONObject();
        InvStockLockEx stockLock = new InvStockLockEx();
        String sku = request.getParameter("sku");
        String secCode = request.getParameter("secCode");
        String optUser = request.getParameter("optUser");
        String refNo = request.getParameter("refNo");
        String startLockTime = request.getParameter("startTime");
        String endLockTime = request.getParameter("endTime");
        String lockQty =request.getParameter("lockQty");
        int size = Integer.parseInt(Ustring.getString0(request.getParameter("size")));
        if(!"".equals(lockQty)&&null!=lockQty){
        	stockLock.setLockQty(Integer.parseInt(lockQty));	
        }
        stockLock.setStartLockTime(startLockTime);
        stockLock.setEndLockTime(endLockTime);
        	
        if (size <= 0) {
        	size=20;
        }



        stockLock.setSku(sku == null ? "" : sku.trim());
        stockLock.setSecCode(secCode == null ? "" : secCode.trim().toUpperCase());
        stockLock.setOptUser(optUser == null ? "" : optUser.trim());
        stockLock.setRefno(refNo == null ? "" : refNo.trim());
        PagerInfo pager = new PagerInfo(size, pageIndex);

        List<InvStockLockEx> stockLockList = memStockLockModel.queryMemStockLockList(stockLock,pager);

        Gson gson=new Gson();
        json.put("total", pager.getRowsCount());
        json.put("rows", gson.toJson(stockLockList));
        return json;
    }
    /**
     * 跳转至网单库存释放列表
     * @return
     */
    @RequestMapping(value = { "/loadStockLockWDListPage.html" }, method = { RequestMethod.GET })
    String loadStockLockWDList(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) String operate) {
        return "/stock/stockLockWDList";
    }
    
    /**
     * 释放WD库存列表
     * @param request
     * @param modelMap
     * @param itemProperty
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = { "/queryStockLockWDListPage" }, method = { RequestMethod.POST })
    @ResponseBody
    JSONObject queryMemStockLockWDList(HttpServletRequest request, Map<String, Object> modelMap,
                                 @RequestParam(required = false) String itemProperty,
                                 @RequestParam(required = false) Integer pageIndex) {
    	JSONObject json = new JSONObject();
        InvStockLockEx stockLock = new InvStockLockEx();
        String sku = request.getParameter("sku");
        String secCode = request.getParameter("secCode");
        String optUser = request.getParameter("optUser");
        String refNo = request.getParameter("refNo");
        String operate = request.getParameter("operate");
        String startLockTime = request.getParameter("startTime");
        String endLockTime = request.getParameter("endTime");
        stockLock.setStartLockTime(startLockTime);
        stockLock.setEndLockTime(endLockTime);
        String lockQty =request.getParameter("lockQty");
        int size = Integer.parseInt(Ustring.getString0(request.getParameter("size")));
        int start =Integer.parseInt(Ustring.getString0(request.getParameter("start")));
        if(!"".equals(lockQty)&&null!=lockQty){
        	stockLock.setLockQty(Integer.parseInt(lockQty));	
        }
        stockLock.setStartLockTime(startLockTime);
        stockLock.setEndLockTime(endLockTime);
        	
        if (size <= 0) {
        	size=20;
		}
        if(start>0){
        	start=((start-1)  * size);
        	if(start==0){
        		start =1;
        	}
		}else {
			start =1;
		}
        
        
        stockLock.setSku(sku == null ? "" : sku.trim());
        stockLock.setSecCode(secCode == null ? "" : secCode.trim().toUpperCase());
        stockLock.setOptUser(optUser == null ? "" : optUser.trim());
        stockLock.setRefno(refNo == null ? "" : refNo.trim());
        PagerInfo pager = new PagerInfo(size, start);
        List<InvStockLockEx> stockLockList = memStockLockModel.queryMemStockWDLockList(stockLock, pager);
        modelMap.put("rowList", stockLockList);
        modelMap.put("pager", pager);
        modelMap.put("operate", operate);
        Gson gson=new Gson();
        json.put("total", pager.getRowsCount());
        json.put("rows", gson.toJson(stockLockList));
		return json;
    }

    @RequestMapping(value = { "/cancelLockStock" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<String> cancelLockStock(Map<String, Object> modelMap,
                                           HttpServletRequest request, HttpServletResponse response) {
        logger.info("手动取消锁定库存");
        String sku = request.getParameter("sku");
        String secCode = request.getParameter("secCode");
        String refNo = request.getParameter("refno");
        String releaseCnt = request.getParameter("releaseQty");
        String id = request.getParameter("id");
        ServiceResult<Boolean> result = memStockLockModel.cancelLockStock(
            sku == null ? "" : sku.trim(), secCode == null ? "" : secCode.trim(),
            refNo == null ? "" : refNo.trim(),
            StringUtil.isEmpty(releaseCnt) ? 0 : Integer.parseInt(releaseCnt), id, request.getSession().getAttribute("userName").toString());
        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        jsonResult.setData(result.getSuccess() ? "释放成功！" : "释放失败！");
        if (!result.getSuccess()) {
            jsonResult.setMessage(result.getMessage());
        }

        return jsonResult;
    }

    /**
     * 将未释放的锁定记录分出渠道判断是谁占用的
     * @return
     */
    @RequestMapping(value = { "/updateStockLockChannel" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<Boolean> lockStockInChannel() {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        boolean isSyncOver = memStockLockModel.lockStockInChannel();
        jsonResult.setData(isSyncOver);
        return jsonResult;
    }

    public void setMemStockLockModel(MemStockLockModel memStockLockModel) {
        this.memStockLockModel = memStockLockModel;
    }

}
