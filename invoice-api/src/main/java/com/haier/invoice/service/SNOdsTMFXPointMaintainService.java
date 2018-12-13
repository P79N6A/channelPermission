package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SNOdsTMFXPointMaintain;
import java.util.List;

/**
 * Created by jtbshan on 2018/5/22.
 */
public interface SNOdsTMFXPointMaintainService {

  public JSONObject paging(SNOdsTMFXPointMaintain param, PagerInfo pagerInfo);

  public JSONObject export(SNOdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> insert(SNOdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> update(SNOdsTMFXPointMaintain param);

  public ServiceResult<JSONObject> delBatch(List<String> ids);

  public ServiceResult<String> checkInfo(List<List<String>> list);

  public ServiceResult<String> execExcel(List<List<String>> list, String user);

  /**
   * 获取最后一次执行计算日志
   * @return
   */
  public String getLogInfo();

}
