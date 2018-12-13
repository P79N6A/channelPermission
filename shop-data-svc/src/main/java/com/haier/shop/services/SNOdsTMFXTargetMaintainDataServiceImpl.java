package com.haier.shop.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.settleCenter.SNOdsTMFXTargetMaintainDao;
import com.haier.shop.model.SNOdsTMFXTargetMaintain;
import com.haier.shop.service.SNOdsTMFXTargetMaintainDataService;
import java.math.BigDecimal;
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
 * @Date: Created in 2018/5/28 14:41
 * @Modified By:
 */
@Service("SNOdsTMFXTargetMaintainDataServiceImpl")
public class SNOdsTMFXTargetMaintainDataServiceImpl implements SNOdsTMFXTargetMaintainDataService {

  @Resource
  private SNOdsTMFXTargetMaintainDao sNOdsTMFXTargetMaintainDao;

  @Override
  public int deleteByPrimaryKey(String id) {
    return sNOdsTMFXTargetMaintainDao.deleteByPrimaryKey(id);
  }

  @Override
  public int insertSelective(SNOdsTMFXTargetMaintain record) {
    return sNOdsTMFXTargetMaintainDao.insertSelective(record);
  }

  @Override
  public SNOdsTMFXTargetMaintain selectByPrimaryKey(String id) {
    return sNOdsTMFXTargetMaintainDao.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SNOdsTMFXTargetMaintain record) {
    return sNOdsTMFXTargetMaintainDao.updateByPrimaryKeySelective(record);
  }

  @Override
  public JSONObject queryTMFXTargetMaintain(SNOdsTMFXTargetMaintain paging) {
    JSONObject result = new JSONObject();
    result.put("total", sNOdsTMFXTargetMaintainDao.queryTMFXTargetMaintainCount(paging));
    result.put("rows", sNOdsTMFXTargetMaintainDao.queryTMFXTargetMaintainList(paging));
    return result;
  }

  @Override
  public List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(SNOdsTMFXTargetMaintain map) {
    return sNOdsTMFXTargetMaintainDao.queryTMFXTargetMaintainExcel(map);
  }

  @Override
  public List<SNOdsTMFXTargetMaintain> queryRepetition(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return sNOdsTMFXTargetMaintainDao.queryRepetition(odsTMFXTargetMaintain);
  }

  @Override
  @Transactional
  public ServiceResult<String> addRecord(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    ServiceResult<String> result = new ServiceResult<>();
    List<SNOdsTMFXTargetMaintain> list = this.queryRepetition(odsTMFXTargetMaintain);
    if (list.size() == 0) {
      try {
//          odsTMFXTargetMaintain.setCreateBy(user.getNickName());
        this.insertSelective(odsTMFXTargetMaintain);
        result.setResult("添加成功");
      } catch (Exception e) {
        e.printStackTrace();
        result.setSuccess(false);
      }
    }
    return result;
  }

  @Override
  public ServiceResult<String> bulkImport(List<SNOdsTMFXTargetMaintain> list) {
    ServiceResult<String> result = new ServiceResult<>();
    if (list == null) {
      result.setSuccess(false);
      return result;
    }
    Map<String, List<Integer>> repeat = new HashMap<>();
    boolean noRepeat = true;
    List<String> errors = new ArrayList<>();
    int rowNo = 2;
    for (SNOdsTMFXTargetMaintain ods : list) {
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
      if (StringUtils.isBlank(ods.getType())) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，类型字段为空").toString());
      } else {
        switch (ods.getType()) {
          case "年度":
            ods.setType("y");
            break;
          case "月度":
            ods.setType("m");
            break;
          case "季度":
            ods.setType("q");
            break;
          default:
            errors
                .add(new StringBuffer("第").append(rowNo).append("行，类型字段值不正确").toString());
        }
      }
      if (null == ods.getTarget()) {
        errors.add(new StringBuffer("第").append(rowNo).append("行，目标字段为空").toString());
      }
    }
    if (!errors.isEmpty()) {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
      return result;
    }
    for (SNOdsTMFXTargetMaintain ods : list) {
      String s = new StringBuffer(ods.getYear()).append(ods.getMonth())
          .append(ods.getEcologyShop())
          .append(ods.getBrand()).append(ods.getIndustry()).append(ods.getType()).toString();
      List<Integer> ll = repeat.get(s);
      if (null != ll) {
        repeat.get(s).add(rowNo);
        noRepeat = false;
      } else {
        List<Integer> tl = new ArrayList<>();
        tl.add(rowNo);
        repeat.put(s, tl);
      }
      List<SNOdsTMFXTargetMaintain> queryList = sNOdsTMFXTargetMaintainDao
          .queryRepetition(ods);
      if (queryList.size() > 0) {
        errors.add("第" + rowNo + "行数据已存在");
      }
      rowNo++;
    }
    if (!noRepeat) {
      for (Map.Entry<String, List<Integer>> es : repeat.entrySet()) {
        List<Integer> l = es.getValue();
        if(l.size()>1){
          errors.add(new StringBuffer("第").append(es.getValue().get(0)).append("行与第")
                         .append(l.subList(1, l.size()).toString()).append("行重复").toString());
        }

      }
    }
    if (errors.size() == 0) {
      sNOdsTMFXTargetMaintainDao.bulkImport(list);
    } else {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
    }
    return result;
  }

  @Override
  public List<SNOdsTMFXTargetMaintain> queryAllTargetMaintain(String year, String month,
      String type) {
    return sNOdsTMFXTargetMaintainDao.queryAllTargetMaintain(year, month, type);
  }

  @Override
  public SNOdsTMFXTargetMaintain findTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return sNOdsTMFXTargetMaintainDao.findTargetMaintain(odsTMFXTargetMaintain);
  }

  @Override
  public SNOdsTMFXTargetMaintain findByShop(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return sNOdsTMFXTargetMaintainDao.findByShop(odsTMFXTargetMaintain);
  }

  @Override
  public BigDecimal findTargetSummary(String year, String month, String ecologyShop, String brand,
      String industry, String type) {
    return sNOdsTMFXTargetMaintainDao
        .findTargetSummary(year, month, ecologyShop, brand, industry, type);
  }

  @Override
  public BigDecimal findYearTarget(String year, String ecologyShop) {
    return sNOdsTMFXTargetMaintainDao.findYearTarget(year, ecologyShop);
  }

}
