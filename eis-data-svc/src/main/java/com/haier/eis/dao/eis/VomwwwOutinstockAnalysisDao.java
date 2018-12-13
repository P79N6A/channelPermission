package com.haier.eis.dao.eis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.eis.model.VomwwwOutinstockAnalysis;

public interface VomwwwOutinstockAnalysisDao {
	 /**
     * 根据交易子单号查询
     * @param tradeNo
     * @param subTradeNo
     * @param busType
     * @return
     */
    List<VomwwwOutinstockAnalysis> getInAnalysisByTradeNo(@Param("tradeNo") String tradeNo,
                                                          @Param("subTradeNo") String subTradeNo,
                                                          @Param("busType") int busType);
    
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
    List<VomwwwOutinstockAnalysis> getByCondition(@Param("queryMap") Map<String, Object> queryMap,
                                                  @Param("size") int size);
    /**
     * 查询是否出库并且推送sap成功
     * @return
     */
    List<VomwwwOutinstockAnalysis> outStockSap(String tbNo);
    /**
     * 根据tb单号查询退货入库信息
     * @param tbNo
     * @return
     */
    VomwwwOutinstockAnalysis  quereyVOMthNO(String tbNo);
    /**
     * 根据thj号查询3W库存信息
     * @param backNo
     * @return
     */
    VomwwwOutinstockAnalysis  quereyBackNo(String backNo);
}
