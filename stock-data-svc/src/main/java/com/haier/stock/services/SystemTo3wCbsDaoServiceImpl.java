package com.haier.stock.services;

import com.haier.stock.dao.stock.SystemTo3wCbsDao;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 旧cbs系统推入新cbs 用来执行定时任务
 *
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:33 2018/5/21
 * @Modified By:
 */
@Service
public class SystemTo3wCbsDaoServiceImpl implements SystemTo3wCbsDaoService {

  @Autowired
  private SystemTo3wCbsDao systemTo3wCbsDao;


  @Override
  public List<SystemTo3wCbs> queryList(Integer interfaceFlag) throws Exception {
    return systemTo3wCbsDao.queryList(interfaceFlag);
  }

  @Override
  public List<SystemTo3wCbs> queryRetryList(Integer interfaceFlag) throws Exception {
    return systemTo3wCbsDao.queryRetryList(interfaceFlag);
  }

  @Override
  public Integer updateById(SystemTo3wCbs systemTo3wCbs) throws Exception {
    return systemTo3wCbsDao.updateById(systemTo3wCbs);
  }

  @Override
  public List<SystemTo3wCbs> queryFailList(Map param) throws Exception {
    return systemTo3wCbsDao.queryFailList(param);
  }

  @Override
  public Integer getFailCounts(Map param) throws Exception {
    return systemTo3wCbsDao.getFailCounts(param);
  }

  @Override
  public SystemTo3wCbs getRecordById(Integer id) throws Exception {
    return systemTo3wCbsDao.getRecordById(id);
  }
}
