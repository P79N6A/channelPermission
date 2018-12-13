package com.haier.svc.api.controller;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Evaluation;
import com.haier.shop.model.EvaluationParameter;
import com.haier.shop.service.EvaluationService;
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
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/24 20:12
 */
@Controller
@RequestMapping(value = "evaluation/")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping("/list")
    public String showCommissionList() {

        return "evaluation/evaluation";
    }

    @RequestMapping("search")
    public void search(
            String source,
            String sourceOrderSn,
            String productName,
            String productCode,
            String commentCreateTime,
            String commentEndTime,
            String markResult,
            Integer page,
            Integer rows,
            HttpServletRequest request, HttpServletResponse response) {
        EvaluationParameter queryParams = new EvaluationParameter();
        queryParams.setSource(source);
        queryParams.setSourceOrderSn(sourceOrderSn);
        queryParams.setCommentCreateTime(commentCreateTime);
        queryParams.setCommentEndTime(commentEndTime);
        queryParams.setProductName(productName);
        queryParams.setProductCode(productCode);
        queryParams.setCustStar(markResult);
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

            ServiceResult<List<Evaluation>> list = evaluationService.getAllData(queryParams);


            Map<String, Object> retMap = new HashMap<String, Object>();
            if (list != null && list.getSuccess() == true) {
                List<Evaluation> result = list
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
