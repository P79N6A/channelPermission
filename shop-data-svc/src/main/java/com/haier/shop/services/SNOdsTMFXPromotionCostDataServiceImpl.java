package com.haier.shop.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.settleCenter.SNOdsTMFXPromotionCostDao;
import com.haier.shop.model.SNOdsTMFXPromotionCost;
import com.haier.shop.service.SNOdsTMFXPromotionCostDataService;

/**
 * @Author: wsh
 * @Description:苏宁分销
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/5/22 13:53
 * @Modified By:
 */
@Service("SNOdsTMFXPromotionCostDataServiceImpl")
public class SNOdsTMFXPromotionCostDataServiceImpl implements SNOdsTMFXPromotionCostDataService {

  @Resource
  private SNOdsTMFXPromotionCostDao sNodsTMFXPromotionCostDao;

  @Override
  public JSONObject queryPromotionCost(SNOdsTMFXPromotionCost param) {
    JSONObject result = new JSONObject();
    result.put("total", sNodsTMFXPromotionCostDao.queryPromotionCostCount(param));
    result.put("rows", sNodsTMFXPromotionCostDao.queryPromotionCostList(param));
    return result;
  }

  @Override
  public List<SNOdsTMFXPromotionCost> queryPromotionCostExcel(
      SNOdsTMFXPromotionCost param) {
    return sNodsTMFXPromotionCostDao.queryPromotionCostExcel(param);
  }

  @Override
  public int updateByPrimaryKeySelective(SNOdsTMFXPromotionCost param) {
    return sNodsTMFXPromotionCostDao.updateByPrimaryKeySelective(param);
  }

  @Override
  public List<SNOdsTMFXPromotionCost> queryRepetition(SNOdsTMFXPromotionCost param) {
    return sNodsTMFXPromotionCostDao.queryRepetition(param);
  }

  @Override
  public int insertSelective(SNOdsTMFXPromotionCost record) {
    return sNodsTMFXPromotionCostDao.insertSelective(record);
  }

  @Override
  @Transactional
  public ServiceResult<String> addPromotionCost(SNOdsTMFXPromotionCost record) {
    ServiceResult<String> result = new ServiceResult<>();
    //新增
    List<SNOdsTMFXPromotionCost> lists = sNodsTMFXPromotionCostDao.queryRepetition(record);
    if (lists.size() >= 1) {
      result.setSuccess(false);
      result.setMessage("数据已存在!");
    } else {
      sNodsTMFXPromotionCostDao.insertSelective(record);
      result.setResult("新增成功");
    }
    return result;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return sNodsTMFXPromotionCostDao.deleteByPrimaryKey(id);
  }

  @Override
  public ServiceResult<String> bulkImport(List<SNOdsTMFXPromotionCost> list) {
    ServiceResult<String> result = new ServiceResult<>();
    if (list == null) {
      result.setSuccess(false);
      return result;
    }
    Map<String, List<Integer>> repeat = new HashMap<>();
    boolean noRepeat = true;
    List<String> errors = new ArrayList<>();
    int rowNo = 2;
    for (SNOdsTMFXPromotionCost ods : list) {
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
    for (SNOdsTMFXPromotionCost ods : list) {
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
      List<SNOdsTMFXPromotionCost> queryList = sNodsTMFXPromotionCostDao
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
      sNodsTMFXPromotionCostDao.bulkImport(list);
    } else {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
    }
    return result;
  }
}
