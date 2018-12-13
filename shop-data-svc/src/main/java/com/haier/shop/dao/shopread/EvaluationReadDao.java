package com.haier.shop.dao.shopread;

import com.haier.shop.model.Evaluation;
import com.haier.shop.model.EvaluationParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/24 20:20
 */
@Mapper
public interface EvaluationReadDao {
    List<Evaluation> getAllData(EvaluationParameter queryEvaluationParameter);

    int getCount(EvaluationParameter queryEvaluationParameter);
}
