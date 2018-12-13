package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.OdsTMFXRebatesSummary;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OdsTMFXRebatesSummaryDao {

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
  int insertSelective(OdsTMFXRebatesSummary record); /*{
        return getSqlSession().insert("OdsTMFXRebatesSummaryMapper.insertSelective",record);
    }*/

  /**
   * 通过主键查询返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  OdsTMFXRebatesSummary selectByPrimaryKey(String id); /*{
        return getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.selectByPrimaryKey",id);
    }*/

  /**
   * 通过主键更新返利汇总按 生态店 产业 品牌<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_REBATES_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:45
   */
  int updateByPrimaryKeySelective(OdsTMFXRebatesSummary record); /*{
        return getSqlSession().update("OdsTMFXRebatesSummaryMapper.updateByPrimaryKeySelective",record);
    }*/

  /**
   * 清除历史数据
   */
  void clearHistoryData(String year, String month, String type);/*{
        getSqlSession().delete("OdsTMFXRebatesSummaryMapper.clearHistoryData",MapBuilder.of().ignoreNullValue().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 按生态店汇总
   */
  OdsTMFXRebatesSummary findSummaryByShop(OdsTMFXRebatesSummary odsTMFXRebatesSummary);/*{
        return getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.findSummaryByShop",odsTMFXRebatesSummary);
    }*/

  /**
   * 天猫分销按产业汇总--分页查询
   */
  List<OdsTMFXRebatesSummary> queryRebatesSummaryList(OdsTMFXRebatesSummary paging);

  /**
   *
   * @param paging
   * @return
   */
  int queryRebatesSummaryCount(OdsTMFXRebatesSummary paging);
  /*{
        Long count = getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.queryRebatesSummaryCount",paging.getSearchMap());
        List data = getSqlSession().selectList("OdsTMFXRebatesSummaryMapper.queryRebatesSummaryList",paging.getSearchMap());
        paging.setTotal(count);
        paging.setData(data);
    }*/

  /**
   * 天猫分销按产业汇总--查询导出
   */
  List<OdsTMFXRebatesSummary> queryRebatesSummaryExcel(OdsTMFXRebatesSummary map);/*{
        return getSqlSession().selectList("OdsTMFXRebatesSummaryMapper.queryRebatesSummaryExcel",map);
    }
*/

  /**
   * 根据品牌查询出月度返利总额
   */
  BigDecimal queryRebatesAmountByBrand(String year, String month, String type, String brand,
      String ecologyShop);/*{
        return getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.queryRebatesAmountByBrand",MapBuilder.of().put("year",year).put("month",month).put("type",type).put("brand",brand).put("ecologyShop",ecologyShop).map());
    }*/

  /**
   * 只按产业品牌汇总
   */
  List<OdsTMFXRebatesSummary> queryByIndustry(String year, String month, String type);/*{
        return getSqlSession().selectList("OdsTMFXRebatesSummaryMapper.queryByIndustry",MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 按产业汇总查询汇总数据
   */
  List<OdsTMFXRebatesSummary> querySummaryByShop(String year, String month, String type);/*{
        return getSqlSession().selectList("OdsTMFXRebatesSummaryMapper.querySummaryByShop",MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 查询所有的返利汇总
   */
  List<OdsTMFXRebatesSummary> queryAllRebatesSummary(String year, String month, String type);/*{
        return getSqlSession().selectList("OdsTMFXRebatesSummaryMapper.queryAllRebatesSummary", MapBuilder.of().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 查询单店铺的总的销额
   */
  BigDecimal querySummaryAmount(String year, String month, String ecologyShop);/* {
        return getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.querySummaryAmount", MapBuilder.of().put("year", year).put("month", month).put("ecologyShop", ecologyShop).map());
    }*/

  /**
   * 查询单产业销额
   */
  BigDecimal queryAmountByIndustry(String year, String month, String type, String brand,
      String industry);/* {
        return getSqlSession().selectOne("OdsTMFXRebatesSummaryMapper.queryAmountByIndustry", MapBuilder.of().put("year", year).put("month", month).put("type", type).put("industry", industry).put("brand",brand).map());
    }*/
}