package com.haier.stock.dao.stock;

import com.haier.stock.model.SystemTo3wCbs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:33 2018/5/21
 * @Modified By:
 */
public interface SystemTo3wCbsDao {

  /**
   * 根据状态查询
   * @param interfaceFlag
   * @return
   */
  List<SystemTo3wCbs> queryList(@Param("interfaceFlag")Integer interfaceFlag);

  /**
   * 查询失败重试数据
   * @param interfaceFlag
   * @return
   */
  List<SystemTo3wCbs> queryRetryList(@Param("interfaceFlag")Integer interfaceFlag);

  /**
   * 根据id更新
   * @param systemTo3wCbs
   * @return
   */
  Integer updateById(@Param("systemTo3wCbs") SystemTo3wCbs systemTo3wCbs);

  /**
   * 查询处理失败的订单派工数据
   * @param param 查询参数
   * @return 查询结果
   */
  List<SystemTo3wCbs> queryFailList(Map param);

  /**
   * 查询失败的结果数
   * @param param 参数
   * @return 结果
   */
  Integer getFailCounts(Map param);

  /**
   * 通过id获取临时表数据
   * @param id 主键
   * @return 查询结果
   */
  SystemTo3wCbs getRecordById(Integer id);

}
