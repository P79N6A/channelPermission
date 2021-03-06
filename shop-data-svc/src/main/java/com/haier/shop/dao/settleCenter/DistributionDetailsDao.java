package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.model.DistributionDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Code generated by CodeGen
 * Desc: 天猫分销明细表Dao类
 * Date: 2018-05-22
 */
@Mapper
public interface DistributionDetailsDao {

    /**
     * 添加
     */
    void create(DistributionDetails model);

    /**
     * 批量添加
     */
    void creates(List<DistributionDetails> model);

    /**
     * 根据网单号查询
     */
    List<DistributionDetails> loadByNetSn(@Param("netSn")String netSn);

    /**
     * 更新
     */
    void update(DistributionDetails model);

    /**
     * 删除
     */
    void delete(@Param("netSn")String netSn);

    /**
     * 批量删除
     */
    void deletes(List<String> list);

    /**
     * 查询
     */
    List<DistributionDetails> list(DistributionDetails model);

    /**
     * 分页查询
     */
    List<DistributionDetails> paging(@Param("param") DistributionDetails param, @Param("page") PagerInfo pagerInfo);

    /**
     * 导出
     * @param param
     * @return
     */
    List<Map<String,Object>> export(@Param("param") DistributionDetails param);
    /**
     * 总条数查询
     */
    long count(@Param("param") DistributionDetails param);

    /**
     * 根据年月 店铺 品牌 产业 汇总分销明细数据
     * @param year
     * @param month
     * @param type
     * @return
     */
    public List<DistributionDetails> queryDetailToSummary(String year,String month,String type);

    /**
     * 根据生态店年度期间产业品牌查询销量和销额
     * @param odsDistributionDetails
     * @return
     */
    public DistributionDetails querySummaryVolumeOrAmount(DistributionDetails odsDistributionDetails);

}
