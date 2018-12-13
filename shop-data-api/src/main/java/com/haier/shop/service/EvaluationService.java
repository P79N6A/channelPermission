package com.haier.shop.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.Evaluation;
import com.haier.shop.model.EvaluationParameter;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/24 20:16
 */
public interface EvaluationService {
    ServiceResult<List<Evaluation>> getAllData(EvaluationParameter queryEvaluationParameter);


    int getCount(EvaluationParameter queryEvaluationParameter);
}
