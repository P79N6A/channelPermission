package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.OdsGateGrossprofit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OdsGateGrossprofitDao {
    OdsGateGrossprofit queryGrossprofitByBrandAndCateGory(OdsGateGrossprofit odsGateGrossprofit);



    List<OdsGateGrossprofitDto> paging(@Param("param") OdsGateGrossprofitDto param, @Param("page") PagerInfo pagerInfo);
    long count(@Param("param") OdsGateGrossprofitDto param);
    int insert( OdsGateGrossprofit param);
    int update( OdsGateGrossprofit param);
    int delBatch(List<String> ids);

    /**
     * 查询是否重复
     * @param odsGateGrossprofit
     * @return
     */
    List<OdsGateGrossprofit> queryRepetition(OdsGateGrossprofit odsGateGrossprofit);

    void bulkImport(List<OdsGateGrossprofit> list);

}
