package com.haier.svc.api.controller;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.QueryTopTradeRateParameter;
import com.haier.traderate.service.TopTradeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by LuJun on 2017/11/6.
 * 获取天猫订单评价信息
 */

@Controller
@RequestMapping(value = "toptraderate/")
public class TopTradeRateController {

    @Autowired
    private TopTradeRateService topTradeRateService;


    /**
     * 根据条件查询天猫评论信息
     *
     * @param source
     * @param sourceOrderSn
     * @param commentCreateTime
     * @param commentEndTime
     * @param isGiveMark
     * @param markResult
     * @param productType
     * @param page
     * @param rows
     * @param request
     * @param response
     */

    @RequestMapping("search")
    public void search(
            String source,
            String sourceOrderSn,
            String commentCreateTime,
            String commentEndTime,
            Integer isGiveMark,
            String markResult,
            String productType,
            Integer page,
            Integer rows,
            HttpServletRequest request, HttpServletResponse response) {
        QueryTopTradeRateParameter queryParams = new QueryTopTradeRateParameter();
        queryParams.setSource(source);
        queryParams.setSourceOrderSn(sourceOrderSn);
        queryParams.setCommentCreateTime(commentCreateTime);
        queryParams.setCommentEndTime(commentEndTime);
        queryParams.setIsGiveMark(isGiveMark);
        queryParams.setMarkResult(markResult);
        queryParams.setProductType(productType);
        queryParams.setPage(page);
        queryParams.setRows(rows);
        try {
            if (queryParams == null) {
                return;
            }
            // 参数验证
            if (queryParams.getPage() == null || queryParams.getPage() == 0) {
                queryParams.setPage(1);
            } else {
                queryParams.setPage((queryParams.getPage() - 1)
                        * queryParams.getRows());
            }
            if (queryParams.getRows() == null) {
                queryParams.setRows(queryParams.getRows() == null ? 20
                        : queryParams.getRows());
            }
            if (queryParams.getCommentCreateTime() != null
                    && queryParams.getCommentCreateTime().length() > 0) {

                queryParams.setCommentCreateTime(queryParams.getCommentCreateTime() + " 00:00:00");
            }
            if (queryParams.getCommentEndTime() != null
                    && queryParams.getCommentEndTime().length() > 0) {
                queryParams.setCommentEndTime(queryParams.getCommentEndTime() + " 23:59:59");
            }

            ServiceResult<List<QueryTopTradeRateParameter>> list = topTradeRateService.getAllData(queryParams);


            Map<String, Object> retMap = new HashMap<String, Object>();
            if (list != null && list.getSuccess() == true) {
                List<QueryTopTradeRateParameter> result = list
                        .getResult();
                PagerInfo pager = list.getPager();
                retMap.put("total", pager.getRowsCount());
                retMap.put("rows", result);
            } else {
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<>());
            }
            Gson gson = new Gson();
            response.addHeader("Content-type", "text/html;charset=utf-8");
            response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
