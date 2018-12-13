package com.haier.stock.services;

import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.HandleVomDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gaohaiming
 * @description: 处理旧CBS推送过来的HP系统数据，旧CBS负责中转数据 后期VOM开发完，此接口就废弃
 * @date:created in 2018/5/23 16:44
 * @modificed by:
 */
@Service("handleVomDataServiceImpl")
public class HandleVomDataServiceImpl implements HandleVomDataService {

  @Autowired
  private AllotNetPointHandleModel handleHpAllotNetPointModel;
  @Autowired
  private HandleReservationGoodsDateModel handleReservationGoodsDateModel;
  @Autowired
  private HandleAcceptHpTimeModel handleAcceptHpTimeModel;
  @Autowired
  private HandleVomDataModel handleVomDataModel;

  @Override
  public void handleHpAllotNetPoint() {
    handleHpAllotNetPointModel.autohandleHpAllotNetPointModel();
  }

  @Override
  public void handleReservationGoodsDate() {
    handleReservationGoodsDateModel.autoHandleReservationGoodsDate();
  }

  @Override
  public void handleAcceptHpTime() {
    handleAcceptHpTimeModel.autoHandleVomData();
  }

  @Override
  public void manualHandleAcceptHpTime(List<SystemTo3wCbs> list) {
    handleVomDataModel.handleVomDataProcess(list,3);
  }
}
