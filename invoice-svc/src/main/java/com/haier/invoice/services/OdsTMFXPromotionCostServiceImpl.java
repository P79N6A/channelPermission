package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.OdsTMFXPromotionCostService;
import com.haier.shop.model.OdsTMFXPromotionCost;
import com.haier.shop.service.OdsTMFXPromotionCostDataService;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 天猫分销-营销费用 Created by zdd on 2017/10/23.
 */
@Service
public class OdsTMFXPromotionCostServiceImpl implements OdsTMFXPromotionCostService {

  @Autowired
  private OdsTMFXPromotionCostDataService odsTMFXPromotionCostDataService;

  /**
   * 分页查询
   */
  @Override
  public JSONObject queryPromptionCost(OdsTMFXPromotionCost odsTMFXPromotionCost) {
    return odsTMFXPromotionCostDataService.queryPromotionCost(odsTMFXPromotionCost);
  }

  /**
   * 查询导出
   */
  @Override
  public List<OdsTMFXPromotionCost> queryPromptionCostExcel(
      OdsTMFXPromotionCost map) {
    return odsTMFXPromotionCostDataService.queryPromotionCostExcel(map);
  }

  /**
   * 新增/修改
   */
  @Override
  public ServiceResult<String> editPromotionCost(OdsTMFXPromotionCost odsTMFXPromotionCost) {
    ServiceResult<String> result = new ServiceResult<>();
    if (odsTMFXPromotionCost == null) {
      result.setSuccess(false);
      result.setMessage("没有上传数据");
      return result;
    }
    if (odsTMFXPromotionCost.getId() != null && !"".equals(odsTMFXPromotionCost.getId())) {
      //修改
      odsTMFXPromotionCostDataService.updateByPrimaryKeySelective(odsTMFXPromotionCost);
      result.setResult("修改成功");
    } else {
      result = odsTMFXPromotionCostDataService.addPromotionCost(odsTMFXPromotionCost);
    }
    return result;
  }

  @Override
  public ServiceResult<String> execExcel(List<OdsTMFXPromotionCost> list) {
    return odsTMFXPromotionCostDataService.bulkImport(list);
  }

  /**
   * 数据校验
   */
/*  @Override
  public ServiceResult<String> checkInfo(List<OdsTMFXPromotionCostExcel> list, BaseUser user) {
    Response<String> result = new Response<String>();
    StringBuffer info = new StringBuffer();
    List<OdsTMFXPromotionCostExcel> newList = new ArrayList<OdsTMFXPromotionCostExcel>();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        OdsTMFXPromotionCostExcel excel = list.get(i);
        this.isNullCheck(i, excel.getCol0(), "年度", info);
        this.isNullCheck(i, excel.getCol1(), "期间", info);
        this.isNullCheck(i, excel.getCol2(), "生态店", info);
        this.isNullCheck(i, excel.getCol4(), "品牌", info);
        this.isNullCheck(i, excel.getCol6(), "费用描述", info);
        for (int j = i + 1; j < list.size(); j++) {
          OdsTMFXPromotionCostExcel excel2 = list.get(j);
          if (excel.getCol0().equals(excel2.getCol0()) && excel.getCol1().equals(excel2.getCol1())
              && excel.getCol2().equals(excel2.getCol2()) &&
              excel.getCol4().equals(excel2.getCol4()) && excel.getCol5()
              .equals(excel2.getCol5())) {
            info.append("第" + (i + 1) + "与第" + (j + 1) + "行数据重复!");
          }
        }
        newList.add(excel);
      }
      list.clear();
      list.addAll(newList);
      if (!Strings.isNullOrEmpty(info.toString())) {
        result.setError(info.toString());
      } else {
        result.setResult("校验通过");
      }
    } else {
      result.setError("导入数据为空");
    }
    return result;
  }*/

  /**
   * 导入处理
   */
 /* @Override
  public Response<String> execExcel(List<OdsTMFXPromotionCostExcel> list, BaseUser user) {
    Response<String> result = new Response<String>();
    StringBuffer info = new StringBuffer();
    List<OdsTMFXPromotionCost> newList = new ArrayList<OdsTMFXPromotionCost>();
    for (int i = 0; i < list.size(); i++) {
      OdsTMFXPromotionCostExcel excel = list.get(i);
      OdsTMFXPromotionCost odsTMFXPromotionCost = new OdsTMFXPromotionCost();
      odsTMFXPromotionCost.setYear(excel.getCol0());
      odsTMFXPromotionCost.setMonth(excel.getCol1());
      odsTMFXPromotionCost.setEcologyShop(excel.getCol2());
      odsTMFXPromotionCost.setBrand(excel.getCol4());
      odsTMFXPromotionCost.setIndustry(excel.getCol5());
      odsTMFXPromotionCost.setCostDes(excel.getCol6());
      if (!Strings.isNullOrEmpty(excel.getCol7())) {
        odsTMFXPromotionCost.setYxCostAmount(new BigDecimal(excel.getCol7()));
        if (!Strings.isNullOrEmpty(excel.getCol5())) {
          info.append();
        }
      }
      if (!Strings.isNullOrEmpty(excel.getCol8())) {
        odsTMFXPromotionCost.setQtCostAmount(new BigDecimal(excel.getCol8()));
        if (Strings.isNullOrEmpty(excel.getCol5())) {
          info.append("第" + (i + 1) + "行数据:其他费用需维护到产业");
        }
      }
      odsTMFXPromotionCost.setCreateBy(user.getNickName());
      newList.add(odsTMFXPromotionCost);

      List<OdsTMFXPromotionCost> queryList = odsTMFXPromotionCostDataService
          .queryRepetition(odsTMFXPromotionCost);
      if (queryList.size() > 0) {
        info.append("第" + (i + 1) + "行数据已存在");
      }
    }
    if (Strings.isNullOrEmpty(info.toString())) {
      odsTMFXPromotionCostDataService.bulkImport(newList);
      result.setResult("导入成功");
    } else {
      result.setError(info.toString());
    }
    return result;
  }*/

  /**
   * 删除
   */
  @Override
  public ServiceResult<String> deletePromotionCost(String ids) {
    ServiceResult<String> result = new ServiceResult<>();
    Set<String> setIds = new HashSet<>();
    Collections.addAll(setIds, ids.split(","));
    try {
      for (String id : setIds) {
        odsTMFXPromotionCostDataService.deleteByPrimaryKey(id);
      }
      result.setResult("删除成功");
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("删除失败:" + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 审核
   */
  @Override
  public ServiceResult<String> auditPromotionCost(String ids, String audit, String nickName) {
    ServiceResult<String> result = new ServiceResult<>();
    if (audit == null) {
      result.setSuccess(false);
      result.setMessage("审核类型不能为空");
      return result;
    }
    Set<String> setIds = new HashSet<>();
    Collections.addAll(setIds, ids.split(","));
    try {
      if ("B".equals(audit)) {
        //业务初审
        for (String id : setIds) {
          OdsTMFXPromotionCost opc = new OdsTMFXPromotionCost();
          opc.setId(id);
          opc.setFirstAudit("A");
          opc.setAuditBy(nickName);
          opc.setAuditTime(new Date());
          odsTMFXPromotionCostDataService.updateByPrimaryKeySelective(opc);
        }
      }
      if ("F".equals(audit)) {
        //财务复审
        for (String id : setIds) {
          OdsTMFXPromotionCost opc = new OdsTMFXPromotionCost();
          opc.setId(id);
          opc.setSecondAudit("A");
          opc.setAuditBy(nickName);
          opc.setAuditTime(new Date());
          odsTMFXPromotionCostDataService.updateByPrimaryKeySelective(opc);
        }
      }
      result.setResult("审核通过");
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("审核失败:" + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }


/*  public void isNullCheck(int index, String col, String colname, StringBuffer result) {
    if (Strings.isNullOrEmpty(col)) {
      result.append("第" + (index + 1) + "行:" + colname + "为空;");
    }
  }*/
}
