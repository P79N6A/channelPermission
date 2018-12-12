package com.haier.eis.service;

import java.util.List;
import java.util.Map;


import com.haier.eis.model.VomwwwOutinstockAnalysis;

public interface EisVomwwwOutinstockAnalysisService {
	 /**
     * 根据交易子单号查询
     * @param tradeNo
     * @param subTradeNo
     * @param busType
     * @return
     */
    List<VomwwwOutinstockAnalysis> getInAnalysisByTradeNo( String tradeNo,
                                                          String subTradeNo,
                                                          int busType);
    
    /**
     * 更新推送sap状态
     * @param vomwwwOutinstockAnalysis
     * @return
     */
    Integer updateSapStatusById(VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis);
    
    /**
     * 根据条件获取队列数据
     * queryMap(key:q_VomwwwOutinstockAnalysis.属性名称),例如：q_id
     * @return
     */
    List<VomwwwOutinstockAnalysis> getByCondition( Map<String, Object> queryMap,
                                                  int size);
    
    VomwwwOutinstockAnalysis  quereyVOMthNO(String tbNo);//根据tb单号查询退货入库信息
}
