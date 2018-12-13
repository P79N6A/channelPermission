package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.SNOdsTMFXShopSummary;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SNOdsTMFXShopSummaryDao {

  /**
   * 通过主键删除返利汇总 按生态店<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_SHOP_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:49
   */
  int deleteByPrimaryKey(String id);
  /*{
        return getSqlSession().delete("OdsTMFXShopSummaryMapper.deleteByPrimaryKey",id);
    }*/

  /**
   * 添加数据到返利汇总 按生态店<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_SHOP_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:49
   */
  int insertSelective(SNOdsTMFXShopSummary record);
  /* {
        return getSqlSession().insert("OdsTMFXShopSummaryMapper.insertSelective",record);
    }*/

  /**
   * 通过主键查询返利汇总 按生态店<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_SHOP_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:49
   */
  SNOdsTMFXShopSummary selectByPrimaryKey(String id);
  /*{
        return getSqlSession().selectOne("OdsTMFXShopSummaryMapper.selectByPrimaryKey",id);
    }*/

  /**
   * 通过主键更新返利汇总 按生态店<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_SHOP_SUMMARY<br>
   *
   * DATE: 2017-11-07 16:49
   */
  int updateByPrimaryKeySelective(SNOdsTMFXShopSummary record);
  /*{
        return getSqlSession().update("OdsTMFXShopSummaryMapper.updateByPrimaryKeySelective",record);
    }*/

  /**
   * 清除历史数据
   */
  void clearHistoryData(String year, String month, String type);
  /*{
        getSqlSession().delete("OdsTMFXShopSummaryMapper.clearHistoryData", MapBuilder.of().ignoreNullValue().put("year",year).put("month",month).put("type",type).map());
    }*/

  /**
   * 分页查询
   */
  List<SNOdsTMFXShopSummary> queryShopSummaryList(SNOdsTMFXShopSummary paging);

  /**
   *
   * @param paging
   * @return
   */
  int queryShopSummaryCount(SNOdsTMFXShopSummary paging);
  /*{
        Long count = getSqlSession().selectOne("OdsTMFXShopSummaryMapper.queryShopSummaryCount",paging.getSearchMap());
        List data = getSqlSession().selectList("OdsTMFXShopSummaryMapper.queryShopSummaryList",paging.getSearchMap());
        paging.setTotal(count);
        paging.setData(data);
    }*/

  /**
   * 查询导出
   */
  List<SNOdsTMFXShopSummary> queryShopSummaryExcel(SNOdsTMFXShopSummary map);
  /*{
        return getSqlSession().selectList("OdsTMFXShopSummaryMapper.queryShopSummaryExcel",map);
    }*/
}