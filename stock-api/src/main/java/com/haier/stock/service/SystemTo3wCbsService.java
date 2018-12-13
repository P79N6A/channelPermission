package com.haier.stock.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.stock.model.SystemTo3wCbs;
import java.util.List;
import java.util.Map;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 * @Author: liwei
 * @Description:
 * @Date: Create in 15:19 2018/5/18
 * @Modified By:
 */

public interface SystemTo3wCbsService {

  /**
   * 根据状态批量查询
   * @param interfaceFlag
   * @return
   * @throws Exception
   */
  ServiceResult<List<SystemTo3wCbs>> queryList(Integer interfaceFlag) throws Exception;

  /**
   * 根据id更新状态
   * @param systemTo3wCbs
   * @return
   * @throws Exception
   */
  ServiceResult<Boolean> updateById(SystemTo3wCbs systemTo3wCbs) throws Exception;

  /**
   * 查询订单派工失败数据
   * @param param 查询参数
   * @return 返回结果
   * @throws Exception 抛出异常
   */
  ServiceResult<List<JSONObject>> queryFailList(Map param) throws Exception;

  /**
   * 查询订单派工失败数据量
   * @param param 参数
   * @return 结果
   * @throws Exception 异常
   */
  ServiceResult<Integer> queryFailCounts(Map param) throws Exception;

  ServiceResult<SystemTo3wCbs> getRecordById(Integer id) throws Exception;
}
