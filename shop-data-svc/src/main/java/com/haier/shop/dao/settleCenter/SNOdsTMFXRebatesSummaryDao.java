package com.haier.shop.dao.settleCenter;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.haier.shop.model.SNOdsTMFXRebatesSummary;

@Repository
public interface SNOdsTMFXRebatesSummaryDao {

  /**
   * 通过主键删除返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  int deleteByPrimaryKey(String id);
/*     {
        return getSqlSession().delete("OdsTMFXRebatesSummaryMapper.deleteByPrimaryKey",id);
    }*/

  /**
   * 添加数据到返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  int insertSelective(SNOdsTMFXRebatesSummary record); /*{
        return getSqlSession().insert("SNOdsTMFXRebatesSummaryMapper.insertSelective",record);
    }*/

  /**
   * 通过主键查询返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  SNOdsTMFXRebatesSummary selectByPrimaryKey(String id); /*{
        return getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.selectByPrimaryKey",id);
    }*/

  /**
   * 通过主键更新返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  int updateByPrimaryKeySelective(SNOdsTMFXRebatesSummary record); /*{
        return getSqlSession().update("SNOdsTMFXRebatesSummaryMapper.updateByPrimaryKeySelective",record);
    }*/

  /**
   * 清除历史数据
   */
  void clearHistoryData(String year, String month, String type);/*{
        getSqlSession().delete("SNOdsTMFXRebatesSummaryMapper.clearHistoryData",MapBuilder.of().ignoreNullValue().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 按生态店汇总
   */
  SNOdsTMFXRebatesSummary findSummaryByShop(SNOdsTMFXRebatesSummary odsTMFXRebatesSummary);/*{
        return getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.findSummaryByShop",odsTMFXRebatesSummary);
    }*/

  /**
   * 天猫分销按产业汇总--分页查询
   */
  List<SNOdsTMFXRebatesSummary> queryRebatesSummaryList(SNOdsTMFXRebatesSummary paging);

  /**
   *
   * @param paging
   * @return
   */
  int queryRebatesSummaryCount(SNOdsTMFXRebatesSummary paging);
  /*{
        Long count = getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.queryRebatesSummaryCount",paging.getSearchMap());
        List data = getSqlSession().selectList("SNOdsTMFXRebatesSummaryMapper.queryRebatesSummaryList",paging.getSearchMap());
        paging.setTotal(count);
        paging.setData(data);
    }*/

  /**
   * 天猫分销按产业汇总--查询导出
   */
  List<SNOdsTMFXRebatesSummary> queryRebatesSummaryExcel(SNOdsTMFXRebatesSummary map);/*{
        return getSqlSession().selectList("SNOdsTMFXRebatesSummaryMapper.queryRebatesSummaryExcel",map);
    }
*/

  /**
   * 根据品牌查询出月度返利总额
   */
  BigDecimal queryRebatesAmountByBrand(String year, String month, String type, String brand,
      String ecologyShop);/*{
        return getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.queryRebatesAmountByBrand",MapBuilder.of().put("year",year).put("month",month).put("type",type).put("brand",brand).put("ecologyShop",ecologyShop).map());
    }*/

  /**
   * 只按产业品牌汇总
   */
  List<SNOdsTMFXRebatesSummary> queryByIndustry(String year, String month, String type);/*{
        return getSqlSession().selectList("SNOdsTMFXRebatesSummaryMapper.queryByIndustry",MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 按产业汇总查询汇总数据
   */
  List<SNOdsTMFXRebatesSummary> querySummaryByShop(String year, String month, String type);/*{
        return getSqlSession().selectList("SNOdsTMFXRebatesSummaryMapper.querySummaryByShop",MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 查询所有的返利汇总
   */
  List<SNOdsTMFXRebatesSummary> queryAllRebatesSummary(String year, String month, String type);/*{
        return getSqlSession().selectList("SNOdsTMFXRebatesSummaryMapper.queryAllRebatesSummary", MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 查询单店铺的总的销额
   */
  BigDecimal querySummaryAmount(String year, String month, String ecologyShop);/* {
        return getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.querySummaryAmount", MapBuilder.of().put("year", year).put("month", month).put("ecologyShop", ecologyShop).map());
    }*/

  /**
   * 查询单产业销额
   */
  BigDecimal queryAmountByIndustry(String year, String month, String type, String brand,
      String industry);/* {
        return getSqlSession().selectOne("SNOdsTMFXRebatesSummaryMapper.queryAmountByIndustry", MapBuilder.of().put("year", year).put("month", month).put("type", type).put("industry", industry).put("brand",brand).map());
    }*/
}