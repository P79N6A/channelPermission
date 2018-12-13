package com.haier.stock.services;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.logistics.service.HpDispatchService;
import com.haier.shop.model.AllotNetPoint;
import com.haier.shop.model.HpAllotNetPoint;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.SpringContextUtil;
import com.haier.stock.util.XmlUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/28 18:04
 * @modificed by:
 */
@Service("handleHpAllotNetPointModel")
public class AllotNetPointHandleModel {

  private static final int STATUS_ONE = 1;
  private static final int STATUS_TWO = 2;

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(AllotNetPointHandleModel.class);

  @Autowired
  private HpDispatchService hpDispatchService;
  @Autowired
  private SystemTo3wCbsDaoService systemTo3wCbsDaoService;

  /**
   * 处理HP分配网点信息入口
   *
   */
  public void autohandleHpAllotNetPointModel() {
    try {

      List<SystemTo3wCbs> to3wCbsList = systemTo3wCbsDaoService.queryList(STATUS_ONE);
      if (null == to3wCbsList || to3wCbsList.isEmpty()) {
        log.info("处理旧cbs推送的HP分配网单数据,没有需要处理的初始数据，去执行失败数据重试操作");
        //失败重试数据
        to3wCbsList = systemTo3wCbsDaoService.queryRetryList(STATUS_ONE);
        if (CollectionUtils.isEmpty(to3wCbsList)){
          log.info("处理旧cbs推送的HP分配网单数据,没有需要处理的失败重试数据");
          return;
        }
      }
      execute(to3wCbsList);
    } catch (Exception e) {
      log.error("获取旧cbs推送数据失败" + e.getMessage());
    }
  }

  private void execute(List<SystemTo3wCbs> to3wCbsList) {
    ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
    //加入多线程执行
    ExecuteAllotNetPoint executeAllotNetPoint = new ExecuteAllotNetPoint();
    //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
    int splitSize = 100;
    int size = to3wCbsList.size();
    if (size > 10 && size <= 100) {
      splitSize = size / 2 + 1;
    }
    new MultiThreadTool<SystemTo3wCbs>()
        .processJobs(executeAllotNetPoint, threadHelper, log, to3wCbsList,
            splitSize, 3);
  }

  private class ExecuteAllotNetPoint implements IExcute {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Object obj) {
      ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
      try {
        List<SystemTo3wCbs> to3wCbsList = (List<SystemTo3wCbs>) obj;
        if (null == to3wCbsList || to3wCbsList.isEmpty()) {
          return;
        }
        handleVomDataProcess(to3wCbsList);
      } catch (Exception e) {
        log.error("[OrderMarkBuilderModel]发生异常", e);
      } finally {
        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
      }
    }
  }

  public void handleVomDataProcess(List<SystemTo3wCbs> list) {
      handleHpAllotNetPoint(list);
  }

  /**
   * 处理HP分配网点信息入口
   */
  private void handleHpAllotNetPoint(List<SystemTo3wCbs> list) {
    for (SystemTo3wCbs to3wCbs : list) {
      if(to3wCbs.getInterfaceFlag() != 1){
        continue;
      }
      String requestXml = "";
      long startTime = System.currentTimeMillis();
      try {

        String jsonStr = to3wCbs.getContent();
        if (!"[".equals(jsonStr.substring(0, 1))) {
          jsonStr = "[" + jsonStr + "]";
        }
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        List<AllotNetPoint> netPoints = new ArrayList<AllotNetPoint>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < jsonArray.size(); i++) {
          JSONObject job = jsonArray.getJSONObject(i);
          AllotNetPoint allotNetPoint = new AllotNetPoint();
          allotNetPoint.setCUSTOMER_CODE((String) job.get("customerCode"));
          allotNetPoint.setORDER_NO((String) job.get("orderNo"));
          allotNetPoint.setCREATED_DATE((String) job.get("createdDate"));
          allotNetPoint.setPROC_REMARK((String) job.get("procRemark"));
          allotNetPoint.setENTER_TIME((String) job.get("enterTime"));
          allotNetPoint.setSB_DATE((String) job.get("sbDate"));
          allotNetPoint.setASSIGN_DATE((String) job.get("assignDate"));
          allotNetPoint.setId((Integer) job.get("id"));
          allotNetPoint.setStatus((Integer) job.get("status"));
          allotNetPoint.setMessage((String) job.get("message"));
          allotNetPoint.setApiLogsId((Integer) job.get("apiLogsId"));
          try {
            allotNetPoint.setCreateTime(sdf.parse(job.get("createTime").toString()));
            allotNetPoint.setUpdateTime(sdf.parse(job.get("updateTime").toString()));
          } catch (Exception e) {
            e.printStackTrace();
          }

          netPoints.add(allotNetPoint);
        }

        HpAllotNetPoint hpAllotNetPoint = new HpAllotNetPoint();
        hpAllotNetPoint.setItem(netPoints);
        requestXml = XmlUtil.jAXBmarshalString(hpAllotNetPoint);
      } catch (Exception e) {
        try {
          to3wCbs.setStatus(STATUS_TWO);
          to3wCbs.setErrorMessage("数据格式有误");
          setFailCounts(to3wCbs);
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception exception) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理失败");
        }
        continue;
      }
      //保存源数据
      ServiceResult<String> saveResult = hpDispatchService.saveNetPoint(requestXml);
      if (!saveResult.getSuccess()) {
        log.error("保存hp分配网点信息失败：" + saveResult.getMessage());
        //更新中转表状态
        try {
          to3wCbs.setStatus(STATUS_TWO);
          setFailCounts(to3wCbs);
          to3wCbs.setErrorMessage(saveResult.getMessage());
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理失败");
        }
      } else {
        //更新中转表状态
        try {
          to3wCbs.setStatus(STATUS_ONE);
          to3wCbs.setErrorMessage(saveResult.getResult());
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理完成");
        }
      }
      long endTime = System.currentTimeMillis();
      long time = endTime - startTime;
      log.info("receive HP Allot netpoint,totalTime:" + time + "ms" + "system_to_3w_cbs表中记录的id为"
          + to3wCbs.getId());
    }

  }
  private void setFailCounts(SystemTo3wCbs to3wCbs){
    if (null == to3wCbs.getCounts()) {
      to3wCbs.setCounts(STATUS_ONE);
    } else {
      int temp = to3wCbs.getCounts();
      to3wCbs.setCounts(++temp);
    }
  }
}
