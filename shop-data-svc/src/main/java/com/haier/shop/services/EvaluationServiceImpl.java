package com.haier.shop.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.shopread.EvaluationReadDao;
import com.haier.shop.model.Evaluation;
import com.haier.shop.model.EvaluationParameter;
import com.haier.shop.service.EvaluationService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/24 20:19
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(EvaluationServiceImpl.class);
    @Autowired
    private EvaluationReadDao evaluationReadDao;

    @Override
    public ServiceResult<List<Evaluation>> getAllData(EvaluationParameter queryEvaluationParameter) {
        ServiceResult<List<Evaluation>> result = new ServiceResult<List<Evaluation>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (queryEvaluationParameter == null) {
                result.setSuccess(false);
                result.setMessage("【用户评价getAllData】获取订单信息服务参数并为null");
                logger.error("【用户评价getAllData】获取订单信息服务参数并为null");
                return result;
            }
            List<Evaluation> findqueryEvaluationParameterList = evaluationReadDao
                    .getAllData(queryEvaluationParameter);
            int count = evaluationReadDao.getCount(queryEvaluationParameter);
            List<Evaluation> resultList = new ArrayList<Evaluation>();
            for (Evaluation item : findqueryEvaluationParameterList) {
                resultList.add(item);
            }
            result.setResult(resultList);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("用户评价获取评论信息list失败：", e);
        }
        return result;
    }

    @Override
    public int getCount(EvaluationParameter queryEvaluationParameter) {
        return evaluationReadDao.getCount(queryEvaluationParameter);
    }
}
