package com.haier.shop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.SNOdsTMFXPointMaintain;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.service
 * @Date: Created in 2018/4/23 10:34
 * @Modified By:
 */
public interface SNOdsTMFXPointMaintainDataService {


  public JSONObject paging(SNOdsTMFXPointMaintain param, PagerInfo page) ;

  public JSONObject export(SNOdsTMFXPointMaintain param) ;

  public int insert(SNOdsTMFXPointMaintain param);

  public int update(SNOdsTMFXPointMaintain param);

  public int delBatch(List<String> ids);

  public List<SNOdsTMFXPointMaintain> queryRepetition(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

  public void bulkImport(List<SNOdsTMFXPointMaintain> list);

  /**
   * 获取最后一次执行计算日志
   * @return
   */
  public String getLogInfo(String source);

}
