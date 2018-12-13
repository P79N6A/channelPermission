package com.haier.svc.api.controller.workorder;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.WoDictionaryService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.workorder
 * @Date: Created in 2018/4/24 14:35
 * @Modified By:
 */
@Controller
@RequestMapping("/dictionary")
public class WorkOrderDutyController {

  private static Logger log = LogManager
      .getLogger(WorkOrderDutyController.class);
  private static org.slf4j.Logger psilog = LoggerFactory.getLogger("psilogger");
  /**
   * 数据字典操作
   */
  @Resource
  private WoDictionaryService woDictionaryService;

  /**
   * 查询数据字典信息
   */
  @RequestMapping("/dutyPage")
  public String dutyPage(){
    return "workorder/addressList";
  }

  @ResponseBody
  @RequestMapping(value = "/getdictionary.ajax", method = {RequestMethod.POST, RequestMethod.GET})
  public Object getDictionary(@RequestParam(value = "value_set_id") String value_set_id) {
    return woDictionaryService.selectBySetId(value_set_id);
  }

  /**
   * 查询数据字典信息
   */
  @ResponseBody
  @RequestMapping(value = "/getdictionarys.ajax", method = {RequestMethod.POST, RequestMethod.GET})
  public Object getDictionarys(@RequestParam(value = "value_set_id") String value_set_id,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer no) {
    PagerInfo pager = new PagerInfo(size, no);
    return woDictionaryService.selectBySetIds(value_set_id, pager);
  }

  /**
   * 获取二级菜单
   */
  @ResponseBody
  @RequestMapping(value = "/gettwomenu.ajax", method = RequestMethod.GET)
  public Object getTwoMenu(@RequestParam(value = "value_set_id") String value_set_id,
      @RequestParam(value = "parent_value") String parentValue) {
    return woDictionaryService.selectByParentValue(value_set_id, parentValue);
  }

  /**
   * 分页查询订单来源
   */
  @ResponseBody
  @RequestMapping(value = "/findpagelist.ajax", method = {RequestMethod.POST, RequestMethod.GET})
  public Object findPageList(ReviewDataDictionaryEntity dic,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer no) {
    PagerInfo pager = new PagerInfo(size, no);
    return woDictionaryService.findPageList(dic, pager);
  }

  /**
   * 添加订单来源
   */
  @ResponseBody
  @RequestMapping(value = "/addordersource.ajax", method = {RequestMethod.POST,
      RequestMethod.GET})
  public Object addOrderSource(ReviewDataDictionaryEntity dic, HttpServletRequest request) {
    //从cookie中获取用户登录名
    HttpSession session = request.getSession();
    String userName = (String)session.getAttribute("userName");

/*    if (StringUtils.isEmpty(userName)) {
      return false;
    }*/
    //从cookie中获取用户登录名结束
    return woDictionaryService.addOrderSource(dic, userName);
  }

  /**
   * 修改订单来源
   */
  @ResponseBody
  @RequestMapping(value = "/updateordersource.ajax", method = {RequestMethod.POST,
      RequestMethod.GET})
  public Object updateOrderSource(ReviewDataDictionaryEntity dic, HttpServletRequest request) {
    //从cookie中获取用户登录名
    HttpSession session = request.getSession();
    String userName = (String)session.getAttribute("userName");

/*    if (StringUtils.isEmpty(userName)) {
      return false;
    }*/
    //从cookie中获取用户登录名结束
    return woDictionaryService.updateOrderSource(dic, userName);
  }

  /**
   * 查询数据字典信息
   */
  @ResponseBody
  @RequestMapping(value = "/getdictionaryignoreflg.ajax", method = {RequestMethod.POST,
      RequestMethod.GET})
  public Object getDictionaryIgnoreFlg(@RequestParam(value = "value_set_id") String value_set_id) {
    return woDictionaryService.selectBySetIdIgnoreFlg(value_set_id);

  }

  /**
   * 分页查询区县与工贸对应关系
   */
  @ResponseBody
  @RequestMapping(value = "/findpageregioncompanylist.ajax", method = {RequestMethod.POST,
      RequestMethod.GET})
  public Object findPageRegionCompanyList(
      @RequestParam(value = "provinceId", required = false) String provinceId,
      @RequestParam(value = "cityId", required = false) String cityId,
      @RequestParam(value = "regionId", required = false) String regionId,
      @RequestParam(value = "companyId", required = false) String companyId,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer no) {
    Map<String, Object> map = new HashMap<>();
    if (provinceId != null && !"".equals(provinceId)) {
      map.put("provinceId", provinceId);
    }
    if (cityId != null && !"".equals(cityId)) {
      map.put("cityId", cityId);
    }
    if (regionId != null && !"".equals(regionId)) {
      map.put("regionId", regionId);
    }
    if (companyId != null && !"".equals(companyId)) {
      map.put("companyId", companyId);
    }
    PagerInfo pager = new PagerInfo(size, no);
    return woDictionaryService
        .findPageRegionCompanyList(map, pager);

  }

  /**
   * 添加区县
   *
   * @param companyId 工贸ID
   * @param companyName 工贸名称
   * @param cityName 市名称
   * @param provinceName 省
   */
  @ResponseBody
  @RequestMapping(value = "/addregion.ajax")
  public Object addRegion(ReviewDataDictionaryEntity dic, String companyId, String companyName,
      String cityName, String provinceName, HttpServletRequest request) {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    if (companyId == null || "".equals(companyId)) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工贸不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    String value = dic.getValue();
    String valueMeaning = dic.getValueMeaning();
    String parentValue = dic.getParentValue();
    if (value == null || "".equals(value)) {
      serviceResult
          .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "区县编号不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    if (valueMeaning == null || "".equals(valueMeaning)) {
      serviceResult
          .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "区县名称不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    if (parentValue == null || "".equals(parentValue)) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "市不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    Map<String, Object> map = new HashMap<>();
    map.put("companyId", companyId);
    map.put("companyName", companyName);
    map.put("cityName", cityName);
    map.put("provinceName", provinceName);
    //从cookie中获取用户登录名
    HttpSession session = request.getSession();
    String userName = (String)session.getAttribute("userName");

/*    if (StringUtils.isEmpty(userName)) {
      return false;
    }*/
    //从cookie中获取用户登录名结束
    return woDictionaryService.addRegion(dic, map, userName);
  }

  /**
   * 在工贸中 追加/删除 区县ID
   */
  @ResponseBody
  @RequestMapping(value = "/addcompanyofregion.ajax")
  public Object addCompanyOfRegion(String newCompanyId, String oldCompanyId, String regionId,
      String newCompanyName, String oldCompanyName,
      String regionName, HttpServletRequest request) {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    Map<String, Object> map = new HashMap<>();
    if (newCompanyId == null || "".equals(newCompanyId)) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工贸不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    map.put("newCompanyId", newCompanyId);
    map.put("newCompanyName", newCompanyName);
    if (regionId == null || "".equals(regionId)) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "区县不能为空！");
      return JsonUtil.toJson(serviceResult);
    }
    map.put("regionId", regionId);
    map.put("regionName", regionName);
    if (oldCompanyId != null && !"".equals(oldCompanyId)) {
      map.put("oldCompanyId", oldCompanyId);
      map.put("oldCompanyName", oldCompanyName);
    }
    //从cookie中获取用户登录名
    HttpSession session = request.getSession();
    String userName = (String)session.getAttribute("userName");

/*    if (StringUtils.isEmpty(userName)) {
      return false;
    }*/

    map.put("user", userName);
    //从cookie中获取用户登录名结束
    return woDictionaryService.addCompanyOfRegion(map);
  }

  /**
   * 查询当前责任位在第三方责任表中是否存在
   */
  @ResponseBody
  @RequestMapping(value = "/findThirdPartyCount.ajax", method = {RequestMethod.POST,
      RequestMethod.GET})
  public String findThirdPartyCount(@RequestParam(value = "question1Level1") String question1Level1,
      @RequestParam(value = "question1Level2") String question1Level2,
      @RequestParam(value = "question1Level3") String question1Level3) {
    ThirdPartyLiabilityCondition condition = new ThirdPartyLiabilityCondition();
    condition.setQuestion1Level1(question1Level1);
    condition.setQuestion1Level2(question1Level2);
    condition.setQuestion1Level3(question1Level3);
    Integer toal = woDictionaryService.findThirdPartyCount(condition);
    if (null != toal && toal > 0) {
      return JsonUtil.toJson("已取到");
    } else {
      return JsonUtil.toJson("未取到");
    }
  }
}

