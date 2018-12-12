package com.haier.svc.api.controller.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvReservedConfig;
import com.haier.svc.api.controller.stock.mode.StockReservedModel;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "/configure" })
public class StockReservedConfigController {
    @Autowired
    private StockReservedModel stockReservedModel;
    private static Logger logger = LogManager.getLogger(StockReservedConfigController.class);
    //点击导航条访问的方法
    @RequestMapping(value = { "/getStockReservedPage" })
    String loadStockReservedPage() {
        return "stock/stockReservedConfig";
    }
    //点击查询放问的方法
    @ResponseBody
    @RequestMapping(value = { "/getStockReservedList" })
    JSONObject queryStockReservedList(String schannelCode,String sref,String sstatus,Integer page, Integer rows)throws Exception{
        Map map=new HashMap();
        map.put("schannelCode",schannelCode);
        map.put("sref",sref);
        map.put("sstatus",sstatus);
        if (Ustring.isEmpty(map.get("schannelCode").toString())){
            map.put("schannelCode",null);
        }
        if (Ustring.isEmpty(map.get("sref").toString())){
            map.put("sref",null);
        }
        if (map.get("sstatus").toString().equals("-1")||map.get("sstatus").toString().equals("")||map.get("sstatus").toString().equals("null")){
            map.put("sstatus",null);
        }
		/*
		* 设置分页参数
		* */
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject= stockReservedModel.queryTotalData(pager,map);
        return jsonObject;
    }
    @RequestMapping(value = { "/getStockReservedRow" }, method = { RequestMethod.GET })
    @ResponseBody
    InvReservedConfig getStockReservedRow(HttpServletRequest request, Map<String, Object> modelMap) {
        ModelAndView modelAndView=new ModelAndView();
        String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            InvReservedConfig config = new InvReservedConfig();
            config.setId(Integer.parseInt(id));
            ServiceResult<List<InvReservedConfig>> result = this.stockReservedModel
                    .queryReservedConfigs(config);
            if (result.getResult() != null && result.getResult().size() > 0) {
                modelMap.put("row", result.getResult().get(0));
                request.setAttribute("row",result.getResult().get(0));
            }
            return result.getResult().get(0);
        }
        return new InvReservedConfig();
    }

    @RequestMapping(value = { "/saveReservedConfig" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<String> saveReservedConfig(HttpServletRequest request) {
        String id = request.getParameter("id");
        InvReservedConfig config = new InvReservedConfig();
        config.setChannelCode(request.getParameter("channelCode1"));
        config.setLockHours(Integer.parseInt(request.getParameter("lockHours")));
        config.setStatus(Integer.parseInt(request.getParameter("status1")));
        config.setRef(request.getParameter("ref1"));
        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        String ref = request.getParameter("ref1");
        //优先检查单号
        //1.单号单独检查，不与渠道冲突
        String refMsg = checkRef(ref, StringUtil.isEmpty(id, true) ? null : Integer.parseInt(id));
        if (refMsg != null) {
            jsonResult.setMessage(refMsg);
            return jsonResult;
        }
        //2.检查渠道时， 单号为空， 否则单号已经检查过
        String channelMsg = StringUtil.isEmpty(ref) ? checkChannel(
                request.getParameter("channelCode1"),
                StringUtil.isEmpty(id, true) ? null : Integer.parseInt(id)) : null;
        if (channelMsg != null) {
            jsonResult.setMessage(channelMsg);
            return jsonResult;
        }

        ServiceResult<Boolean> result;
        config.setChannelCode(request.getParameter("channelCode1").trim().toUpperCase());
        if (id == null || id.equals("")) {
            config.setCreateUser(WebUtil.currentUserName(request));
            config.setUpdateUser(WebUtil.currentUserName(request));
            result = stockReservedModel.insertReservedConfig(config);
            logger.info("新增配置信息成功, id=" + config.getId() + ", channelCode="
                    + config.getChannelCode() + ", lockHours=" + config.getLockHours());
        } else {
            config.setId(Integer.parseInt(id));
            config.setUpdateUser(WebUtil.currentUserName(request));
            result = stockReservedModel.updateReservedConfig(config);
            logger.info("修改配置信息成功, id=" + config.getId() + ", channelCode="
                    + config.getChannelCode() + ", lockHours=" + config.getLockHours());
        }
        jsonResult.setMessage(result.getSuccess() ? "操作成功" : "操作失败");
        return jsonResult;
    }

    /**
     * 检查单号
     * @param ref
     * @param id
     * @return
     */
    private String checkRef(String ref, Integer id) {
        if (StringUtil.isEmpty(ref, true)) {
            return null;
        }
        InvReservedConfig config = new InvReservedConfig();
        config.setRef(ref);
        ServiceResult<List<InvReservedConfig>> result = this.stockReservedModel
                .queryReservedConfigs(config);
        if (result.getResult() != null && result.getResult().size() > 0) {
            if (id == null) {
                return "新增的单号已经存在";
            } else {
                InvReservedConfig conf = result.getResult().get(0);
                if (conf.getId() == id) {
                    return null;
                } else {
                    return "修改的单号已经存在";
                }
            }

        }
        return null;
    }
    /**
     * 检查渠道
     * @param channelCode
     * @param id
     * @return
     */
    private String checkChannel(String channelCode, Integer id) {
        if (channelCode == null) {
            return "渠道编码不能为空";
        }
        channelCode = channelCode.trim();
        InvReservedConfig config = new InvReservedConfig();
        config.setChannelCode(channelCode);
        ServiceResult<List<InvReservedConfig>> result = this.stockReservedModel
                .queryReservedConfigs(config);
        if (result.getResult() != null && result.getResult().size() > 0) {

            boolean channelExist = false;
            for (InvReservedConfig tempConfig : result.getResult()) {
                if (!StringUtil.isEmpty(tempConfig.getChannelCode(), true)
                        && tempConfig.getChannelCode().equals(channelCode)
                        && tempConfig.getId().intValue() != id
                        && StringUtil.isEmpty(tempConfig.getRef(), true)) {
                    channelExist = true;
                    break;
                }
            }
            if (id == null && channelExist) {
                return "新增的渠道已存在";
            }

            if (channelExist) {
                return "修改的渠道已经存在";
            }

        }
        return null;
    }
}
