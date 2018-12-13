package com.haier.svc.api.controller.workorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.shop.dto.LogBean;
import com.haier.traderate.service.WoReviewLogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;

/**
 * 日志
 *                       
 * @Filename: WebReviewLogController.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
@Controller
@RequestMapping(value = "/log", produces = "text/html;charset=UTF-8")
public class WebReviewLogController {
    private static Logger     log = LogManager.getLogger(WebReviewLogController.class);

    @Resource
    private WoReviewLogService woReviewLogService;

    @GetMapping("/log")
    public String orderList(){
        return "workorder/log";
    }
    @RequestMapping(value = { "/reviewlogpage.ajax" }, method = { RequestMethod.POST,
            RequestMethod.GET })
    @ResponseBody
    public String reviewContactsPage(@RequestParam(value = "username_search", required = false) String username_search,
                                     @RequestParam(value = "mk_search", required = false) String mk_search,
                                     @RequestParam(value = "id_search", required = false) String id,
                                     @RequestParam(value = "startTime_search", required = false) String startTime,
                                     @RequestParam(value = "endTime_search", required = false) String endTime,
                                     @RequestParam(value = "rows", required = false) Integer size,
                                     @RequestParam(value = "page", required = false) Integer no,
                                     HttpServletRequest request, HttpServletResponse response) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            LogBean review = new LogBean();
            if (username_search != null && !"".equals(username_search)) {
                review.setUsername(username_search);
            }
            if (mk_search != null && !"".equals(mk_search)) {
                review.setMkname(mk_search);
            }
            if (id != null && !"".equals(id)) {
                review.setFkid(id);
            }
            if ("".equals(startTime)) {
                startTime = null;
            }
            if ("".equals(endTime)) {
                endTime = null;
            }
            PagerInfo pager = new PagerInfo(size, no);

            ServiceResult<List<LogBean>> result = woReviewLogService.page(review, pager, startTime,
                endTime);
            //创建一个Map,为了适应前台jquery easyUi语法
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("total", result.getPager().getRowsCount());
            retMap.put("rows", result.getResult());
            if (result.getSuccess()) {
                return JsonUtil.toJson(retMap);
            } else {
                return result.getMessage();
            }
        } catch (Exception e) {
            serviceResult.setError("SYSTEM_ERROR",
                    "系统异常，请联系系统管理员");
            log.error("系统异常，请联系系统管理员", e);
            return JsonUtil.toJson(serviceResult);
        }
    }

}
