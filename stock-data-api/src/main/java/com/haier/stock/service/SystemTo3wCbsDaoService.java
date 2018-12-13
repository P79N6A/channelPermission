package com.haier.stock.service;

import com.haier.stock.model.SystemTo3wCbs;
import java.util.List;
import java.util.Map;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 *
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:34 2018/5/21
 * @Modified By:
 */
public interface SystemTo3wCbsDaoService {

  /**
   * 查询
   * @param interfaceFlag
   * @return
   * @throws Exception
   */
  List<SystemTo3wCbs> queryList(Integer interfaceFlag) throws Exception;

  /**
   * 查询失败重试网单
   * @param interfaceFlag 标识
   * @return 返回集合
   * @throws Exception
   */
  List<SystemTo3wCbs> queryRetryList(Integer interfaceFlag) throws Exception;

  /**
   * 更新
   * @param systemTo3wCbs
   * @return
   * @throws Exception
   */
  Integer updateById(SystemTo3wCbs systemTo3wCbs) throws Exception;

  /**
   * 查询处理失败的订单派工数据
   * @param param 查询参数
   * @return 返回结果
   * @throws Exception 抛出异常
   */
  List<SystemTo3wCbs> queryFailList(Map param) throws Exception;

  /**
   * 获取处理失败数据量
   * @param param 参数
   * @return 结果
   * @throws Exception 异常
   */
  Integer getFailCounts(Map param) throws Exception;

  /**
   * 根据id查询
   * @param id 参数
   * @return 结果
   * @throws Exception 异常
   */
  SystemTo3wCbs getRecordById(Integer id) throws Exception;

}
