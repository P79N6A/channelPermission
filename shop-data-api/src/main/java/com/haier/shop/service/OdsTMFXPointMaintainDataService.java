package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewUserDto;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.shop.model.WOUser;

import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.service
 * @Date: Created in 2018/4/23 10:34
 * @Modified By:
 */
public interface OdsTMFXPointMaintainDataService {


  public JSONObject paging(OdsTMFXPointMaintain param, PagerInfo page) ;

  public JSONObject export(OdsTMFXPointMaintain param) ;

  public int insert(OdsTMFXPointMaintain param);

  public int update(OdsTMFXPointMaintain param);

  public int delBatch(List<String> ids);

  public List<OdsTMFXPointMaintain> queryRepetition(OdsTMFXPointMaintain odsTMFXPointMaintain);

  public void bulkImport(List<OdsTMFXPointMaintain> list);

  /**
   * 获取最后一次执行计算日志
   * @return
   */
  public String getLogInfo(String source);

}
