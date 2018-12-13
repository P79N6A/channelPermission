package com.haier.svc.api.controller.workorder;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.shop.model.ReviewContext;
import com.haier.shop.util.ReviewConstants;
import com.haier.svc.api.controller.util.WebReviewConstants;
import com.haier.traderate.service.ReviewContextService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 评论表Controller
 *                       
 * @Filename: WebReviewContextController.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Controller
@RequestMapping(value = "/comment", produces = "text/html;charset=UTF-8")
public class WebReviewContextController {
    private static Logger log = LogManager.getLogger(WebReviewContextController.class);
    /**
     * 评论操作
     */
    @Resource
    private ReviewContextService reviewContextService;
    private static org.slf4j.Logger                  psilog   = LoggerFactory.getLogger("psilogger");
    /**
     * 查询评论信息
     * @param reviewId
     * @return
     */
    @RequestMapping(value = { "/selectcomment.ajax" }, method = { RequestMethod.POST,
            RequestMethod.GET })
    @ResponseBody
    public String selectComment(@RequestParam(value = "reviewId", required = false) String reviewId,HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewContext>> result = reviewContextService
                .getReviewContextByReviewId(reviewId);
            if (result.getSuccess()) {
                return JsonUtil.toJson(result.getResult());
            } else {
                return result.getMessage();
            }
        } catch (Exception e) {
            serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
                WebReviewConstants.SYSTEM_ERROR);
            log.error(WebReviewConstants.SYSTEM_ERROR, e);
            return JsonUtil.toJson(serviceResult);
        }
    }

    /**
     * 保存评论信息
     */
    @RequestMapping(value = { "/addcomment.ajax" }, method = { RequestMethod.POST,
            RequestMethod.GET })
    @ResponseBody
    public String addComment(@RequestParam(value = "reviewId", required = false) String reviewId,
                             @RequestParam(value = "comment1", required = false) String comment1,
                             HttpServletRequest request, HttpServletResponse response) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
        	
            //从cookie中获取用户登录名
            String userName = this.getUserName(request);
            if (StringUtils.isEmpty(userName)) {
                serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "获取不到当前用户");
                return JsonUtil.toJson(serviceResult);
            }
            //从cookie中获取用户登录名结束
            //        String userName = "评论添加人";
            //修改评论信息表的记录
            Date d = new Date();
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
            ReviewContext reviewContext = new ReviewContext();
            UUID uuid = UUID.randomUUID();
            reviewContext.setReviewid(reviewId);
            reviewContext.setContext(comment1);
            reviewContext.setAddtime(dateStr);
            reviewContext.setAdduser(userName);
            reviewContext.setId(uuid.toString());
            ServiceResult<Boolean> result = reviewContextService.addReviewContext(reviewContext);
            if (result.getSuccess()) {
                String r = dateStr + "  " + userName + "  " + comment1;
                serviceResult.setResult(r);
                return JsonUtil.toJson(serviceResult);
            } else {
                serviceResult.setError(result.getCode(), result.getMessage());
                return JsonUtil.toJson(serviceResult);
            }
        } catch (Exception e) {
            serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
                WebReviewConstants.SYSTEM_ERROR);
            log.error(WebReviewConstants.SYSTEM_ERROR, e);
            return JsonUtil.toJson(serviceResult);
        }
    }

    /**
     * 根据网单号与责任位获取反馈信息
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    @RequestMapping(value = { "/selectcomment2.ajax" }, method = { RequestMethod.POST,
            RequestMethod.GET })
    @ResponseBody
    public String findReviewMiddleByNetOrderIdAndQuestion1Level2(@RequestParam(value = "netOrderId", required = false) String netOrderId,
                                                                 @RequestParam(value = "question1Level2", required = false) String question1Level2,
                                                                 @RequestParam(value = "question1Level3", required = false) String question1Level3,
                                                                 HttpServletRequest request) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            ServiceResult<List<ReviewContext>> result = reviewContextService
                .findReviewContextByNetOrderIdAndQuestion1Level2(netOrderId, question1Level2,question1Level3);
            return JsonUtil.toJson(result);
        } catch (Exception e) {
            serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
                WebReviewConstants.SYSTEM_ERROR);
            log.error(WebReviewConstants.SYSTEM_ERROR, e);
            return JsonUtil.toJson(serviceResult);
        }
    }

    private String getUserName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("userName");
    }

}
