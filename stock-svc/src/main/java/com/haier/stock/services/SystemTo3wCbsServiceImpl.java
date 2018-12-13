package com.haier.stock.services;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import com.haier.stock.service.SystemTo3wCbsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 *
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:36 2018/5/21
 * @Modified By:
 */
@Service
public class SystemTo3wCbsServiceImpl implements SystemTo3wCbsService {

  private static Logger log = LoggerFactory.getLogger(SystemTo3wCbsServiceImpl.class);

  @Autowired
  private SystemTo3wCbsDaoService systemTo3wCbsDaoService;
//  @Autowired
//  private ProductsService productsService;


  @Override
  public ServiceResult<List<SystemTo3wCbs>> queryList(Integer interfaceFlag) throws Exception {
    ServiceResult<List<SystemTo3wCbs>> result = new ServiceResult<List<SystemTo3wCbs>>();
    try {
      result.setResult(systemTo3wCbsDaoService.queryList(interfaceFlag));
    } catch (Exception e) {
      log.error("获取3w_cbs执行定时任务所需数据失败：", e);
      result.setSuccess(false);
      result.setMessage("出现未知异常:" + e.getMessage());
    }
    return result;
  }

  @Override
  public ServiceResult<Boolean> updateById(SystemTo3wCbs systemTo3wCbs) throws Exception {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    Integer num = systemTo3wCbsDaoService.updateById(systemTo3wCbs);
    if (num > 0) {
      result.setResult(true);
      result.setMessage("更新成功！");
    } else {
      result.setResult(false);
      result.setMessage("更新失败！");
    }
    return result;
  }

  @Override
  public ServiceResult<List<JSONObject>> queryFailList(Map param) throws Exception {
    ServiceResult<List<JSONObject>> result = new ServiceResult<List<JSONObject>>();
    List<SystemTo3wCbs> list = systemTo3wCbsDaoService.queryFailList(param);
    List<JSONObject> packData = rePackData(list);

    if (CollectionUtils.isNotEmpty(packData)) {
      result.setResult(packData);
    } else {
      result.setResult(new ArrayList<>());
      result.setMessage("获取数据为空");
    }
    return result;
  }

  private List<JSONObject> rePackData(List<SystemTo3wCbs> list) {
    List<JSONObject> packedData = new ArrayList<JSONObject>();
    for (SystemTo3wCbs demo : list) {
      JSONObject jsonObject = new JSONObject();
      jsonObject = JSONObject.parseObject(demo.getContent());
      jsonObject.put("systemId", demo.getId());
      jsonObject.put("notifyTime", demo.getNotifyTime());
      jsonObject.put("errorMessage", demo.getErrorMessage());
      jsonObject.put("interfaceFlag", demo.getInterfaceFlag());
      jsonObject.put("counts", demo.getCounts());
      packedData.add(jsonObject);
    }
    return packedData;
  }

  @Override
  public ServiceResult<Integer> queryFailCounts(Map param) throws Exception {
    ServiceResult<Integer> result = new ServiceResult<Integer>();
    Integer num = systemTo3wCbsDaoService.getFailCounts(param);
    num = (null == num) ? 0 : num;
    result.setResult(num);
    return result;
  }

  @Override
  public ServiceResult<SystemTo3wCbs> getRecordById(Integer id) throws Exception {
    ServiceResult<SystemTo3wCbs> result = new ServiceResult<SystemTo3wCbs>();
    SystemTo3wCbs to3wCbs = null;
    try {
      to3wCbs = systemTo3wCbsDaoService.getRecordById(id);
    } catch (Exception e) {
      result.setResult(new SystemTo3wCbs());
      result.setSuccess(false);
      result.setMessage(e.getMessage());
    }
    if (null == to3wCbs) {
      result.setResult(new SystemTo3wCbs());
      result.setSuccess(false);
      result.setMessage("查询结果为空");
    } else {
      result.setSuccess(true);
      result.setResult(to3wCbs);
    }
    return result;
  }
}