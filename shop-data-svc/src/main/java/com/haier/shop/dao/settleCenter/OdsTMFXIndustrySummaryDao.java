package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.OdsTMFXIndustrySummary;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OdsTMFXIndustrySummaryDao {

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
  int insertSelective(OdsTMFXIndustrySummary record);
    /* {
        return getSqlSession().insert("OdsTMFXIndustrySummaryMapper.insertSelective",record);
    }
*/

  /**
   * 通过主键查询按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  OdsTMFXIndustrySummary selectByPrimaryKey(String id); /*{
    return getSqlSession().selectOne("OdsTMFXIndustrySummaryMapper.selectByPrimaryKey", id);
  }*/

  /**
   * 通过主键更新按产业品牌汇总<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_INDUSTRY_SUMMARY<br>
   *
   * DATE: 2017-11-30 10:23
   */
  int updateByPrimaryKeySelective(OdsTMFXIndustrySummary record);/* {
    return getSqlSession()
        .update("OdsTMFXIndustrySummaryMapper.updateByPrimaryKeySelective", record);
  }*/

  /**
   * 清除历史数据
   */
  void clearHistoryData(String year, String month, String type);/* {
    getSqlSession().delete("OdsTMFXIndustrySummaryMapper.clearHistoryData",
        MapBuilder.of().ignoreNullValue().put("year", year).put("month", month).put("type", type)
            .map());
  }*/

  /**
   * 按产业品牌汇总---分页查询
   */
  List<OdsTMFXIndustrySummary> queryIndustrySummaryList(OdsTMFXIndustrySummary paging);
  /*{Long count = getSqlSession()
        .selectOne("OdsTMFXIndustrySummaryMapper.queryIndustrySummaryCount", paging.getSearchMap());
    List data = getSqlSession()
        .selectList("OdsTMFXIndustrySummaryMapper.queryIndustrySummaryList", paging.getSearchMap());
    paging.setTotal(count);
    paging.setData(data);
  }
*/

  /**
   *
   * @param paging
   * @return
   */
  int queryIndustrySummaryCount(OdsTMFXIndustrySummary paging);

  /**
   * 按产业品牌汇总---查询导出
   */
  List<OdsTMFXIndustrySummary> queryIndustrySummaryExcel(OdsTMFXIndustrySummary map); /*{
    return getSqlSession()
        .selectList("OdsTMFXIndustrySummaryMapper.queryIndustrySummaryExcel", map);
  }*/
}