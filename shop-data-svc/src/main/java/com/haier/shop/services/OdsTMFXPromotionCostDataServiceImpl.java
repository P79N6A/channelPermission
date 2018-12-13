package com.haier.shop.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.settleCenter.OdsTMFXPromotionCostDao;
import com.haier.shop.model.OdsTMFXPromotionCost;
import com.haier.shop.service.OdsTMFXPromotionCostDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/5/22 13:53
 * @Modified By:
 */
@Service
public class OdsTMFXPromotionCostDataServiceImpl implements OdsTMFXPromotionCostDataService {

  @Resource
  private OdsTMFXPromotionCostDao odsTMFXPromotionCostDao;

  @Override
  public JSONObject queryPromotionCost(OdsTMFXPromotionCost param) {
    JSONObject result = new JSONObject();
    result.put("total", odsTMFXPromotionCostDao.queryPromotionCostCount(param));
    result.put("rows", odsTMFXPromotionCostDao.queryPromotionCostList(param));
    return result;
  }

  @Override
  public List<OdsTMFXPromotionCost> queryPromotionCostExcel(
      OdsTMFXPromotionCost param) {
    return odsTMFXPromotionCostDao.queryPromotionCostExcel(param);
  }

  @Override
  public int updateByPrimaryKeySelective(OdsTMFXPromotionCost param) {
    return odsTMFXPromotionCostDao.updateByPrimaryKeySelective(param);
  }

  @Override
  public List<OdsTMFXPromotionCost> queryRepetition(OdsTMFXPromotionCost param) {
    return odsTMFXPromotionCostDao.queryRepetition(param);
  }

  @Override
  public int insertSelective(OdsTMFXPromotionCost record) {
    return odsTMFXPromotionCostDao.insertSelective(record);
  }

  @Override
  @Transactional
  public ServiceResult<String> addPromotionCost(OdsTMFXPromotionCost record) {
    ServiceResult<String> result = new ServiceResult<>();
    //新增
    List<OdsTMFXPromotionCost> lists = odsTMFXPromotionCostDao.queryRepetition(record);
    if (lists.size() >= 1) {
      result.setSuccess(false);
      result.setMessage("数据已存在!");
    } else {
      record.setId(null);
      odsTMFXPromotionCostDao.insertSelective(record);
      result.setResult("新增成功");
    }
    return result;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return odsTMFXPromotionCostDao.deleteByPrimaryKey(id);
  }

  @Override
  public ServiceResult<String> bulkImport(List<OdsTMFXPromotionCost> list) {
    ServiceResult<String> result = new ServiceResult<>();
    if (list == null) {
      result.setSuccess(false);
      return result;
    }
    Map<String, List<Integer>> repeat = new HashMap<>();
    boolean noRepeat = true;
    List<String> errors = new ArrayList<>();
    int rowNo = 2;
    for (OdsTMFXPromotionCost ods : list) {
      if (StringUtils.isBlank(ods.getEcologyShop())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，生态店字段为空").toString());
      }
      if (StringUtils.isBlank(ods.getYear())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，年度字段为空").toString());
      }
      if (StringUtils.isBlank(ods.getMonth())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，期间字段为空").toString());
      }
      if (StringUtils.isBlank(ods.getIndustry())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，产业字段为空").toString());
      }
      if (StringUtils.isBlank(ods.getBrand())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，品牌字段为空").toString());
      }
      /*if (null == ods.getYxCostAmount()) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，营销费用金额字段为空").toString());
      }*/
      if (null == ods.getQtCostAmount()) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，其他费用金额字段为空").toString());
      }
      if (null == ods.getCostDes()) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，费用描述字段为空").toString());
      }
    }
    if (!errors.isEmpty()) {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
      return result;
    }
    for (OdsTMFXPromotionCost ods : list) {
      String s = new StringBuffer(ods.getYear()).append(ods.getMonth())
          .append(ods.getEcologyShop())
          .append(ods.getBrand()).append(ods.getQtCostAmount()).toString();
      List<Integer> ll = repeat.get(s);
      if (null == ll) {
        List<Integer> tl = new ArrayList<>();
        tl.add(rowNo);
        repeat.put(s, tl);
      } else {
        repeat.get(s).add(rowNo);
        noRepeat = false;
      }

      if (null != ods.getQtCostAmount() && StringUtils.isBlank(ods.getIndustry())) {
        errors.add("第" + rowNo + "行数据:其他费用需维护到产业");
      }
      List<OdsTMFXPromotionCost> queryList = odsTMFXPromotionCostDao
          .queryRepetition(ods);
      if (queryList.size() > 0) {
        errors.add("第" + rowNo + "行数据已存在");
      }
      rowNo++;
    }
    if (!noRepeat) {
      for (Map.Entry<String, List<Integer>> es : repeat.entrySet()) {
        List<Integer> l = es.getValue();
        errors.add(new StringBuffer("第").append(es.getValue().get(0)).append("行与第")
            .append(l.subList(1, l.size()).toString()).append("行重复").toString());
      }
    }
    if (errors.size() == 0) {
      odsTMFXPromotionCostDao.bulkImport(list);
    } else {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
    }
    return result;
  }
}
