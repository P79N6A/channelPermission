package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.SNOdsTMFXIndustrySummary;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SNOdsTMFXIndustrySummaryDao {

  /**
   * 通过主键删除按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  int deleteByPrimaryKey(String id);
    /* {
        return getSqlSession().delete("OdsTMFXIndustrySummaryMapper.deleteByPrimaryKey",id);
    }
*/

  /**
   * 添加数据到按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  int insertSelective(SNOdsTMFXIndustrySummary record);
    /* {
        return getSqlSession().insert("SNOdsTMFXIndustrySummaryMapper.insertSelective",record);
    }
*/

  /**
   * 通过主键查询按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  SNOdsTMFXIndustrySummary selectByPrimaryKey(String id); /*{
    return getSqlSession().selectOne("SNOdsTMFXIndustrySummaryMapper.selectByPrimaryKey", id);
  }*/

  /**
   * 通过主键更新按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  int updateByPrimaryKeySelective(SNOdsTMFXIndustrySummary record);/* {
    return getSqlSession()
        .update("SNOdsTMFXIndustrySummaryMapper.updateByPrimaryKeySelective", record);
  }*/

  /**
   * 清除历史数据
   */
  void clearHistoryData(String year, String month, String type);/* {
    getSqlSession().delete("SNOdsTMFXIndustrySummaryMapper.clearHistoryData",
        MapBuilder.of().ignoreNullValue().put("year", year).put("month", month).put("type", type)
            .map());
  }*/

  /**
   * 按产业品牌汇总---分页查询
   */
  List<SNOdsTMFXIndustrySummary> queryIndustrySummaryList(SNOdsTMFXIndustrySummary paging);
  /*{Long count = getSqlSession()
        .selectOne("SNOdsTMFXIndustrySummaryMapper.queryIndustrySummaryCount", paging.getSearchMap());
    List data = getSqlSession()
        .selectList("SNOdsTMFXIndustrySummaryMapper.queryIndustrySummaryList", paging.getSearchMap());
    paging.setTotal(count);
    paging.setData(data);
  }
*/

  /**
   *
   * @param paging
   * @return
   */
  int queryIndustrySummaryCount(SNOdsTMFXIndustrySummary paging);

  /**
   * 按产业品牌汇总---查询导出
   */
  List<SNOdsTMFXIndustrySummary> queryIndustrySummaryExcel(SNOdsTMFXIndustrySummary map); /*{
    return getSqlSession()
        .selectList("SNOdsTMFXIndustrySummaryMapper.queryIndustrySummaryExcel", map);
  }*/
}