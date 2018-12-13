package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.SNOdsTMFXTargetMaintainService;
import com.haier.shop.model.SNOdsTMFXTargetMaintain;
import com.haier.shop.service.SNOdsTMFXTargetMaintainDataService;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.invoice.services
 * @Date: Created in 2018/5/28 14:37
 * @Modified By:
 */
@Service("SNOdsTMFXTargetMaintainServiceImpl")
public class SNOdsTMFXTargetMaintainServiceImpl implements SNOdsTMFXTargetMaintainService {

  @Resource
  private SNOdsTMFXTargetMaintainDataService sNOdsTMFXTargetMaintainDataService;

  /**
   * 苏宁分销-目标维护分页查询
   */
  @Override
  public JSONObject queryTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    return sNOdsTMFXTargetMaintainDataService.queryTMFXTargetMaintain(odsTMFXTargetMaintain);
  }

  /**
   * 苏宁分销-目标维护查询导出
   */
  @Override
  public List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(
      SNOdsTMFXTargetMaintain map) {
    return sNOdsTMFXTargetMaintainDataService.queryTMFXTargetMaintainExcel(map);
  }

  /**
    *  苏宁分销-目标维护新增修改
   */
  @Override
  public ServiceResult<String> editTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain) {
    ServiceResult<String> result = new ServiceResult<>();
    if (odsTMFXTargetMaintain.getId() == 0) {
      //新增
      //新增之前先校验
      result = sNOdsTMFXTargetMaintainDataService.addRecord(odsTMFXTargetMaintain);
    } else {
      //修改
      try {
        odsTMFXTargetMaintain.setType(null);
        sNOdsTMFXTargetMaintainDataService.updateByPrimaryKeySelective(odsTMFXTargetMaintain);
        result.setResult("修改成功");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * 导入校验
   */
/*
  @Override
  public ServiceResult<String> checkInfo(List<OdsTMFXTargetMaintainExcel> list, BaseUser user) {
    ServiceResult<String> resultInfo = new ServiceResult<String>();
    StringBuffer result = new StringBuffer();
    List<OdsTMFXTargetMaintainExcel> newList = new ArrayList<OdsTMFXTargetMaintainExcel>();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        OdsTMFXTargetMaintainExcel excel = list.get(i);
        this.isNullCheck(i, excel.getCol0(), "生态店", result);
        this.isNullCheck(i, excel.getCol1(), "年度", result);
        this.isNullCheck(i, excel.getCol2(), "期间", result);
        this.isNullCheck(i, excel.getCol3(), "产业", result);
        this.isNullCheck(i, excel.getCol4(), "品牌", result);
        this.isNullCheck(i, excel.getCol5(), "类型", result);
        this.isNullCheck(i, excel.getCol6(), "目标", result);
        for (int j = i + 1; j < list.size(); j++) {
          OdsTMFXTargetMaintainExcel excel2 = list.get(j);
          if (excel.getCol0().equals(excel2.getCol0()) && excel.getCol1().equals(excel2.getCol1())
              && excel.getCol2().equals(excel2.getCol2())
              && excel.getCol3().equals(excel2.getCol3()) && excel.getCol4()
              .equals(excel2.getCol4()) && excel.getCol5().equals(excel2.getCol5())) {
            result.append("第" + (i + 1) + "行和第" + (j + 1) + "行重复:");
          }
        }
        newList.add(excel);
      }
      list.clear();
      list.addAll(newList);
      if (!Strings.isNullOrEmpty(result.toString())) {
        resultInfo.setError(result.toString());
      } else {
        resultInfo.setResult("校验通过");
      }
    } else {
      resultInfo.setError("导入数据为空");
    }
    return resultInfo;
  }
*/

  /**
   * 导入处理
   */
/*
  @Override
  public ServiceResult<String> execExcel(List<OdsTMFXTargetMaintainExcel> list, BaseUser user) {
    ServiceResult<String> result = new ServiceResult<String>();
    StringBuffer errorInfo = new StringBuffer();
    List<OdsTMFXTargetMaintain> newList = new ArrayList<OdsTMFXTargetMaintain>();
    for (int i = 0; i < list.size(); i++) {
      OdsTMFXTargetMaintainExcel excel = list.get(i);
      OdsTMFXTargetMaintain otm = new OdsTMFXTargetMaintain();
      try {
        otm.setEcologyShop(excel.getCol0());
        otm.setYear(excel.getCol1());
        otm.setMonth(excel.getCol2());
        otm.setIndustry(excel.getCol3());
        otm.setBrand(excel.getCol4());
        if ("月度".equals(excel.getCol5())) {
          otm.setType("m");
        } else if ("季度".equals(excel.getCol5())) {
          otm.setType("q");
        } else if ("年度".equals(excel.getCol5())) {
          otm.setType("y");
        }
        otm.setTarget(new BigDecimal(excel.getCol6()));
        otm.setCreateBy(user.getNickName());
      } catch (Exception e) {
        e.printStackTrace();
        result.setError("导入失败:" + e.getMessage());
      }
      List<OdsTMFXTargetMaintain> repetition = sNOdsTMFXTargetMaintainDataService.queryRepetition(otm);
      if (repetition.size() > 0) {
        errorInfo.append("第" + (i + 1) + "行数据已存在");
      }
      newList.add(otm);
    }
    if (errorInfo.length() < 1) {
      sNOdsTMFXTargetMaintainDataService.bulkImport(newList);
      result.setResult("导入成功");
    } else {
      result.setError(errorInfo.toString());
    }
    return result;
  }
*/

  /**
   * 删除
   */
  @Override
  public ServiceResult<String> deleteTargetMaintain(String ids, String userName) {
    ServiceResult<String> result = new ServiceResult<String>();
    Set<String> setIds = new HashSet<>();
    Collections.addAll(setIds, ids.split(","));
    try {
      for (String id : setIds) {
        SNOdsTMFXTargetMaintain otm = new SNOdsTMFXTargetMaintain();
        otm.setId(Integer.valueOf(id));
        otm.setDeleteTab("Y");
        sNOdsTMFXTargetMaintainDataService.updateByPrimaryKeySelective(otm);
      }
      result.setResult("删除成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.setMessage("删除失败");
    }
    return result;
  }

  /**
   * 审核
   */
  @Override
  public ServiceResult<String> auditTargetMaintain(String ids, String audit, String userName) {
    ServiceResult<String> result = new ServiceResult<>();
    if (audit == null) {
      result.setMessage("审核类型不能为空");
      return result;
    }
    Set<String> setIds = new HashSet<>();
    Collections.addAll(setIds, ids.split(","));
    try {
      if ("B".equals(audit)) {
        //业务审核
        for (String id : setIds) {
          SNOdsTMFXTargetMaintain otm = new SNOdsTMFXTargetMaintain();
          otm.setId(Integer.parseInt(id));
          otm.setAuditState("F");
          otm.setAuditBy(userName);
//          otm.setAuditBy(user.getNickName());
          otm.setAuditTime(new Date());
          sNOdsTMFXTargetMaintainDataService.updateByPrimaryKeySelective(otm);
        }
      } else if ("F".equals(audit)) {
        //财务审核
        for (String id : setIds) {
          SNOdsTMFXTargetMaintain otm = new SNOdsTMFXTargetMaintain();
          otm.setId(Integer.parseInt(id));
          otm.setAuditState("A");
          otm.setAuditBy(userName);
          otm.setAuditTime(new Date());
          sNOdsTMFXTargetMaintainDataService.updateByPrimaryKeySelective(otm);
        }
      }
      result.setResult("审核完成");
    } catch (Exception e) {
      e.printStackTrace();
      result.setMessage("审核失败" + e.getMessage());
    }
    return result;
  }

  @Override
  public ServiceResult<String> execExcel(List<SNOdsTMFXTargetMaintain> list) {
    return sNOdsTMFXTargetMaintainDataService.bulkImport(list);
  }

/*  public void isNullCheck(int index, String col, String colname, StringBuffer result) {
    if (Strings.isNullOrEmpty(col)) {
      result.append("第" + (index + 1) + "行:" + colname + "为空;");
    }
  }*/
}
