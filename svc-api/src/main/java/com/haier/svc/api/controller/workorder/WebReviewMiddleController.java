package com.haier.svc.api.controller.workorder;

import com.haier.svc.api.controller.util.WebReviewConstants;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;
import com.haier.traderate.service.WoReviewMiddleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;

/**
 * 中间结果Controller
 *                       
 * @Filename: WebReviewMiddleController.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Controller
@RequestMapping(value = "/middleresult", produces = "text/html;charset=UTF-8")
public class WebReviewMiddleController {
    private static Logger log = LogManager.getLogger(WebReviewMiddleController.class);
    private static org.slf4j.Logger psilog = LoggerFactory.getLogger("psilogger");
    /**
     * 中间结果操作
     */
    @Resource
    private WoReviewMiddleService woReviewMiddleService;

    /**
     * 查询中间表信息
     *
     * @param reviewId
     * @return
     */
    @RequestMapping(value = {"/selectmiddleresult.ajax"}, method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public String selectMiddleResult(@RequestParam(value = "reviewId", required = false) String reviewId, HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewMiddle>> result = woReviewMiddleService
                    .getReviewMiddleByReviewId(reviewId);
            if (result.getSuccess()) {
                return JsonUtil.toJson(result.getResult());
            } else {
                return result.getMessage();
            }
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("系统异常");
            log.error("系统异常：{}", e);
            return JsonUtil.toJson(serviceResult);
        }
    }

    /**
     * 根据网单号与责任位获取中间结果
     *
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    @RequestMapping(value = {"/selectmiddleresult2.ajax"}, method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public String selectMiddleResult(@RequestParam(value = "netOrderId", required = false) String netOrderId,
        @RequestParam(value = "question1Level2", required = false) String question1Level2, HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewMiddle>> result = woReviewMiddleService
                .findReviewMiddleByNetOrderIdAndQuestion1Level2(netOrderId, question1Level2);
            if (result != null && result.getSuccess()) {
                return JsonUtil.toJson(result.getResult());
            } else {
                return "获取数据异常";
            }
        } catch (Exception e) {
            serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
                WebReviewConstants.SYSTEM_ERROR);
            log.error(WebReviewConstants.SYSTEM_ERROR, e);
            return JsonUtil.toJson(serviceResult);
        }
    }


    /**
     * 查询最终结果历史信息
     *
     * @param reviewId
     * @return
     */
    @RequestMapping(value = {"/findFinalResult.ajax"}, method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public String findFinalResult(@RequestParam(value = "reviewId", required = false) String reviewId, HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewFinalResult>> result = woReviewMiddleService.findFinalResult(reviewId);
            if (result.getSuccess()) {
                return JsonUtil.toJson(result.getResult());
            } else {
                return result.getMessage();
            }
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("系统异常");
            log.error("系统异常：{}", e);
            return JsonUtil.toJson(serviceResult);
        }
    }



    @RequestMapping(value = {"/findFinalResults.ajax"}, method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public String findFinalResults(@RequestParam(value = "reviewId", required = false) String reviewId, HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewFinalResult>> result = woReviewMiddleService.findFinalResults(reviewId);
            if (result.getSuccess()) {
                return JsonUtil.toJson(result.getResult());
            } else {
                return result.getMessage();
            }
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("系统异常");
            log.error("系统异常：{}", e);
            return JsonUtil.toJson(serviceResult);
        }
    }
}