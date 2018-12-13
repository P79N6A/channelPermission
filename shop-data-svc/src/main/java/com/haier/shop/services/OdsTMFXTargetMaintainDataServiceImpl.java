package com.haier.shop.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.settleCenter.OdsTMFXTargetMaintainDao;
import com.haier.shop.model.OdsTMFXTargetMaintain;
import com.haier.shop.service.OdsTMFXTargetMaintainDataService;
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
@Service
public class OdsTMFXTargetMaintainDataServiceImpl implements OdsTMFXTargetMaintainDataService {

  @Resource
  private OdsTMFXTargetMaintainDao odsTMFXTargetMaintainDao;

  @Override
  public int deleteByPrimaryKey(String id) {
    return odsTMFXTargetMaintainDao.deleteByPrimaryKey(id);
  }

  @Override
  public int insertSelective(OdsTMFXTargetMaintain record) {
    return odsTMFXTargetMaintainDao.insertSelective(record);
  }

  @Override
  public OdsTMFXTargetMaintain selectByPrimaryKey(String id) {
    return odsTMFXTargetMaintainDao.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(OdsTMFXTargetMaintain record) {
    return odsTMFXTargetMaintainDao.updateByPrimaryKeySelective(record);
  }

  @Override
  public JSONObject queryTMFXTargetMaintain(OdsTMFXTargetMaintain paging) {
    JSONObject result = new JSONObject();
    result.put("total", odsTMFXTargetMaintainDao.queryTMFXTargetMaintainCount(paging));
    result.put("rows", odsTMFXTargetMaintainDao.queryTMFXTargetMaintainList(paging));
    return result;
  }

  @Override
  public List<OdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(OdsTMFXTargetMaintain map) {
    return odsTMFXTargetMaintainDao.queryTMFXTargetMaintainExcel(map);
  }

  @Override
  public List<OdsTMFXTargetMaintain> queryRepetition(OdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return odsTMFXTargetMaintainDao.queryRepetition(odsTMFXTargetMaintain);
  }

  @Override
  @Transactional
  public ServiceResult<String> addRecord(OdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    ServiceResult<String> result = new ServiceResult<>();
    List<OdsTMFXTargetMaintain> list = this.queryRepetition(odsTMFXTargetMaintain);
    if (list.size() == 0) {
      try {
        odsTMFXTargetMaintain.setId(null);
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
  public ServiceResult<String> bulkImport(List<OdsTMFXTargetMaintain> list) {
    ServiceResult<String> result = new ServiceResult<>();
    if (list == null) {
      result.setSuccess(false);
      return result;
    }
    Map<String, List<Integer>> repeat = new HashMap<>();
    boolean noRepeat = true;
    List<String> errors = new ArrayList<>();
    int rowNo = 2;
    for (OdsTMFXTargetMaintain ods : list) {
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
    for (OdsTMFXTargetMaintain ods : list) {
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
      List<OdsTMFXTargetMaintain> queryList = odsTMFXTargetMaintainDao
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
      odsTMFXTargetMaintainDao.bulkImport(list);
    } else {
      result.setSuccess(false);
      result.setMessage(JSONObject.toJSONString(errors));
    }
    return result;
  }

  @Override
  public List<OdsTMFXTargetMaintain> queryAllTargetMaintain(String year, String month,
      String type) {
    return odsTMFXTargetMaintainDao.queryAllTargetMaintain(year, month, type);
  }

  @Override
  public OdsTMFXTargetMaintain findTargetMaintain(OdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return odsTMFXTargetMaintainDao.findTargetMaintain(odsTMFXTargetMaintain);
  }

  @Override
  public OdsTMFXTargetMaintain findByShop(OdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return odsTMFXTargetMaintainDao.findByShop(odsTMFXTargetMaintain);
  }

  @Override
  public BigDecimal findTargetSummary(String year, String month, String ecologyShop, String brand,
      String industry, String type) {
    return odsTMFXTargetMaintainDao
        .findTargetSummary(year, month, ecologyShop, brand, industry, type);
  }

  @Override
  public BigDecimal findYearTarget(String year, String ecologyShop) {
    return odsTMFXTargetMaintainDao.findYearTarget(year, ecologyShop);
  }

}
