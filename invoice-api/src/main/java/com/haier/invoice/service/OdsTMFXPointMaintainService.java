package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.OdsTMFXPointMaintain;
import java.util.List;

/**
 * Created by jtbshan on 2018/5/22.
 */
public interface OdsTMFXPointMaintainService {

  public JSONObject paging(OdsTMFXPointMaintain param, PagerInfo pagerInfo);

  public JSONObject export(OdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> insert(OdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> update(OdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> delBatch(List<String> ids);

  public ServiceResult<String> checkInfo(List<List<String>> list);

  public ServiceResult<String> execExcel(List<List<String>> list, String user);

  /**
   * 获取最后一次执行计算日志
   * @return
   */
  public String getLogInfo();

}
