package com.haier.stock.service;

import com.haier.stock.model.SystemTo3wCbs;
import java.util.List;

/**
 * @author: gaohaiming
 * @description: 处理旧CBS推送过来的HP系统数据，旧CBS负责中转数据 后期VOM开发完，此接口就废弃
 * @date:created in 2018/5/23 16:36
 * @modificed by:
 */
public interface HandleVomDataService {

  /**
   * 处理HP分配网点信息入口
   */
  public void handleHpAllotNetPoint();

  /**
   * 处理HP系统回传预约送货时间
   */
  public void handleReservationGoodsDate();

  /**
   * 处理HP回传网点接单，网点出库，用户签收3个时间
   */
  public void handleAcceptHpTime();

  /**
   * 手动处理HP回传网点接单，网点出库，用户签收3个时间
   */
  public void manualHandleAcceptHpTime(List<SystemTo3wCbs> list);
}
