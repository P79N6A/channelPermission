package com.haier.svc.api.controller.workorder;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.shop.dto.ReviewContacts;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.util.ReviewConstants;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.WebReviewConstants;
import com.haier.traderate.service.WoDictionaryService;
import com.haier.traderate.service.WoReviewContactsService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 通讯录表
 *
 * @Filename: WebReviewContactsController.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 */
@Controller
@RequestMapping(value = "/contacts", produces = "text/html;charset=UTF-8")
public class WebReviewContactsController {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(WebReviewContactsController.class);
  private static Logger psilog = LoggerFactory.getLogger("psilogger");
  @Value("${words.excelError}")
  private static String excelError;
  @Resource
  private WoReviewContactsService reviewContactsService;
  @Resource
  private WoDictionaryService woDictionaryService;
  private static String CHECKSTR = "人员,一级领导,二级领导,订单来源,责任位一,责任位二,工贸,人员ID,一级领导ID,二级领导ID,主键ID";

  /**
   * 查询分页信息
   */
  @PostMapping(value = {"/reviewcontactspage.ajax"})
  @ResponseBody
  public String reviewContactsPage(
      @RequestParam(value = "question1level1_search", required = false) String question1level1,
      @RequestParam(value = "question1level2_search", required = false) String question1Level2,
      @RequestParam(value = "ordercome_search", required = false) String orderCome,
      @RequestParam(value = "username_search", required = false) String userName,
      @RequestParam(value = "manageruserId1_search", required = false) String managerUserId1,
      @RequestParam(value = "manageruserId2_search", required = false) String managerUserId2,
      @RequestParam(value = "company_search", required = false) String company,
      @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer no) {
    ReviewContactsDto reviewContacts = new ReviewContactsDto();
    if (StringUtils.isNotEmpty(question1level1)) {
      reviewContacts.setQuestion1level1(question1level1);
    }
    if (StringUtils.isNotEmpty(question1Level2)) {
      reviewContacts.setQuestion1level2(question1Level2);
    }
    if (StringUtils.isNotEmpty(orderCome)) {
      reviewContacts.setOrdercome(orderCome);
    }
    if (StringUtils.isNotEmpty(userName)) {
      reviewContacts.setUsername(userName);
    }
    if (StringUtils.isNotEmpty(managerUserId1)) {
      reviewContacts.setUsername1(managerUserId1);
    }
    if (StringUtils.isNotEmpty(managerUserId2)) {
      reviewContacts.setUsername2(managerUserId2);
    }
    if (StringUtils.isNotEmpty(company)) {
      reviewContacts.setCompany(company);
    }
    if (StringUtils.isNotEmpty(userId)) {
      reviewContacts.setUserid(userId);
    }

    PagerInfo pager = null;
    if (size != null && !"".equals(size)) {
      pager = new PagerInfo(size, no);
    }
    return reviewContactsService.page(reviewContacts, pager).toJSONString();
  }

  /**
   * 人员匹配
   */
  @RequestMapping(value = {"/findcontacts.ajax"}, method = {RequestMethod.POST,
      RequestMethod.GET})
  @ResponseBody
  public String findcontacts(
      @RequestParam(value = "question1level1_search", required = false) String question1level1,
      @RequestParam(value = "question1level2_search", required = false) String question1Level2,
      @RequestParam(value = "ordercome_search", required = false) String orderCome,
      @RequestParam(value = "company_search", required = false) String company) {
    ReviewContactsDto reviewContacts = new ReviewContactsDto();
    reviewContacts.setQuestion1level1(question1level1);
    reviewContacts.setQuestion1level2(question1Level2);
    if (orderCome != null && !"".equals(orderCome)) {
      reviewContacts.setOrdercome(orderCome);
    }
    if (company != null && !"".equals(company)) {
      reviewContacts.setCompany(company);
    }
    return JsonUtil.toJson(reviewContactsService.findListLG(reviewContacts));
  }

  /**
   * 添加信息
   */
  @ResponseBody
  @RequestMapping(value = {"/addreviewcontacts.ajax"}, method = {RequestMethod.POST})
  public String getReView(ReviewContactsDto reviewContactsDto, HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    reviewContactsDto.setAdduser(userName);
    return JSON.toJSONString(reviewContactsService.addReviewContacts(reviewContactsDto));
  }

  /**
   * 修改数据
   */
  @ResponseBody
  @RequestMapping(value = {"/updatecontacts.ajax"}, method = {RequestMethod.POST})
  public String getUpdate(ReviewContacts review, HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    review.setUpdateuser(userName);
    return JSON.toJSONString(reviewContactsService.updateReviewContacts(review));
  }

  /**
   * 删除
   */
  @ResponseBody
  @RequestMapping(value = {"/deletecontacts.ajax"}, method = {RequestMethod.POST})
  public String getDel(@RequestParam("values") String deleteData,
      @RequestParam(value = "qf", required = false) String qf,
      HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束

    List<ReviewContacts> list = JSON.parseArray(deleteData, ReviewContacts.class);
    for (ReviewContacts r : list) {
      r.setUpdateuser(userName);
    }
    return JSON.toJSONString(reviewContactsService.deleteReviewContacts(list, qf));
  }

  /**
   * 人员责任位添加逻辑验证
   */
  @ResponseBody
  @RequestMapping(value = {"/findinsert.ajax"}, method = {RequestMethod.POST})
  public String findinsert(ReviewContactsDto review, HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    review.setAdduser(userName);
    return JSON.toJSONString(reviewContactsService.findinsert(review));
  }

  /**
   * 删除单条数据
   */
  @ResponseBody
  @RequestMapping(value = {"/updatedel.ajax"}, method = {RequestMethod.POST})
  public String updatedel(ReviewContacts review, HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    review.setUpdateuser(userName);
    return JSON.toJSONString(reviewContactsService.updatedel(review));
  }

  @ResponseBody
  @RequestMapping(value = {"/delete.ajax"}, method = {RequestMethod.POST})
  public String delete(String str, HttpServletRequest request) {
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    String[] s = str.split(",");
    boolean res = false;
    for (int i = 0; i < s.length; i++) {
      ReviewContacts review = new ReviewContacts();
      review.setId(s[i]);
      review.setUpdateuser(userName);
      res = reviewContactsService.updatedel(review);
    }
    return JSON.toJSONString(res);

  }

  /**
   * 人员责任页面根据userId更新领导，是否发送通知
   */
  @ResponseBody
  @RequestMapping(value = {"/updatemanageruserid.ajax"}, method = {RequestMethod.POST})
  public String updatemanagerUserId(ReviewContactsDto reviewContactsDto,
      HttpServletRequest request) {
    //        if (reviewUser == null) {
    //            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数不能为空！");
    //            return JsonUtil.toJson(serviceResult);
    //        }
    //从cookie中获取用户登录名
    String userName = (String) request.getSession().getAttribute("userName");
    if (StringUtils.isEmpty(userName)) {
      return JSON.toJSONString(false);
    }
    //从cookie中获取用户登录名结束
    reviewContactsDto.setUpdateuser(userName);
    Integer data = reviewContactsService
        .updatemanagerUserId(reviewContactsDto);
    if (data != null && data > 0) {
      return JSON.toJSONString(true);
    }
    return JSON.toJSONString(false);
  }

  /**
   * 通过ID修改责任人
   */
  @ResponseBody
  @RequestMapping(value = {"/updatebyid.ajax"}, method = {RequestMethod.POST})
  public String updateById(ReviewContactsDto reviewContactsDto, HttpServletRequest request) {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    try {
      //从cookie中获取用户登录名
      String userName = (String) request.getSession().getAttribute("userName");
      if (StringUtils.isEmpty(userName)) {
        serviceResult.setError("11", "11");
        return JsonUtil.toJson(serviceResult);
      }
      //从cookie中获取用户登录名结束
      reviewContactsDto.setUpdateuser(userName);
      if (reviewContactsDto.getId() == null || "".equals(reviewContactsDto.getId())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
            "Id不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (reviewContactsDto.getUserid() == null || "".equals(reviewContactsDto.getUserid())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
            "用户Id不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      Integer data = reviewContactsService.updateById(reviewContactsDto);
      return JsonUtil.toJson(data);
    } catch (Exception e) {
      return JsonUtil.toJson(serviceResult);
    }
  }

  /**
   * 上传文件
   */
  @ResponseBody
  @RequestMapping(value = "/reviewcontactsupload.ajax")
  public String reviewContactsUpLoad(HttpServletRequest request, HttpServletResponse response,
      MultipartFile file) {
    ServiceResult<String> result = new ServiceResult<String>();
    //        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
    //        MultipartFile file = multipartHttpServletRequest.getFile("file");
    try {
      List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
      if (list.size() <= 1) {
        result.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
            "您上传的Excel没有数据记录，请查看重新整理导入！");
        return JsonUtil.toJson(result);
      }
      // 验证模板表头信息
      String[] firstLineData = list.get(0);
      boolean flag = ExcelReader.checkDataTemplate(firstLineData, CHECKSTR);
      if (!flag) {
        result.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
            "您上传的Excel数据格式与系统模板格式存在差异，请下载模板后重新上传！");
        return JsonUtil.toJson(result);
      }

      ServiceResult<List<ReviewContactsDto>> resultList = this.importCheck(list);
      if (resultList.getSuccess()) {
        result.setResult(JsonUtil.toJson(resultList.getResult()));
      } else {
        result.setError("", resultList.getMessage());
      }

    } catch (Exception e) {
      result.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
          WebReviewConstants.SYSTEM_ERROR);
      return JsonUtil.toJson(result);
    }
    return JsonUtil.toJson(result);
  }

  /**
   * JSON转换
   */
  private static final <T> List<T> parseArray(String text, Class<T> clazz) {
    return JSON.parseArray(text, clazz);
  }

  /**
   * 人员责任位添加逻辑验证
   */
  @ResponseBody
  @RequestMapping(value = {"/findinsertlist.ajax"}, method = {RequestMethod.POST})
  public String findinsertList(String dataList, HttpServletRequest request) {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    try {
      List<ReviewContactsDto> reviewContactsDtoList = parseArray(dataList,
          ReviewContactsDto.class);
      //从cookie中获取用户登录名
      String userName = (String) request.getSession().getAttribute("userName");
      if (StringUtils.isEmpty(userName)) {
        return JsonUtil.toJson(serviceResult);
      }
      //从cookie中获取用户登录名结束
      List<Map<String, Object>> rowNumList = new ArrayList<>();
      for (ReviewContactsDto review : reviewContactsDtoList) {
        review.setAdduser(userName);
        Boolean data = reviewContactsService.findinsert(review);
        if (!data) {
          Map<String, Object> map = new HashMap<>();
          map.put("rowId", review.getId());
          map.put("error", data);
          rowNumList.add(map);
        }
      }
      serviceResult.setResult(JsonUtil.toJson(rowNumList));
      //psi监控服务层错误日志 end
      return JsonUtil.toJson(serviceResult);
    } catch (Exception e) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
          WebReviewConstants.SYSTEM_ERROR);
      return JsonUtil.toJson(serviceResult);
    }
  }

  private ServiceResult<List<ReviewContactsDto>> importCheck(List<String[]> list) {
    ServiceResult<List<ReviewContactsDto>> dataList = new ServiceResult<List<ReviewContactsDto>>();
    List<ReviewContactsDto> resultList = new ArrayList<ReviewContactsDto>();
    Map<String, Object> params = new HashMap<String, Object>();
    //获取所有订单来源名称
    List<ReviewDataDictionaryEntity> orderSourceList = woDictionaryService
        .selectBySetId("order_source");
    if (orderSourceList.size() == 0) {
      dataList.setError("", "获取订单来源失败!");
      return dataList;
    }
    //获取所有工贸名称
    List<ReviewDataDictionaryEntity> companyList = woDictionaryService
        .selectBySetId("company");
    if (companyList.size() == 0) {
      dataList.setError("", "获取工贸信息失败!");
      return dataList;
    }
    //获取所有责任位1名称
    List<ReviewDataDictionaryEntity> duty1List = woDictionaryService
        .selectBySetId("duty_1");
    if (duty1List.size() == 0) {
      dataList.setError("", "获取责任位1失败!");
      return dataList;
    }
    //获取所有责任位2名称
    List<ReviewDataDictionaryEntity> duty2List = woDictionaryService
        .selectBySetId("duty_2");
    if (duty2List.size() == 0) {
      dataList.setError("", "获取责任位2失败!");
      return dataList;
    }
    int forIndex = 0;
    for (String[] resultArray : list) {
      forIndex++;
      if (forIndex == 1) {
        continue;
      }
      ReviewContactsDto reviewContactsDto = new ReviewContactsDto();
      if (StringUtils.isNotEmpty(resultArray[0])) {
        reviewContactsDto.setUsername(resultArray[0]);//人员名称
      }
      if (StringUtils.isNotEmpty(resultArray[1])) {
        reviewContactsDto.setUsername1(resultArray[1]);//一级领导
      }
      if (StringUtils.isNotEmpty(resultArray[2])) {
        reviewContactsDto.setUsername2(resultArray[2]);//二级领导
      }
      String ddly = resultArray[3];//订单来源逗号分隔
      if (StringUtils.isNotEmpty(ddly)) {
        reviewContactsDto.setOrdercome(ddly);//订单来源
        //校验是否存在订单来源
        List<String> ddlyList = Arrays.asList(ddly.split(","));
        for (String ly : ddlyList) {
          boolean is = false;
          for (ReviewDataDictionaryEntity orderSource : orderSourceList) {
            String orderSourceName = orderSource.getValueMeaning();
            if (orderSourceName.equals(ly)) {
              is = true;
              break;
            }
          }
          if (!is) {
            dataList.setError("", "第" + forIndex + "行:未找到名称为\'" + ly + "\'的订单来源!");
            return dataList;
          }
        }

      }
      String zr1 = resultArray[4];
      if (StringUtils.isNotEmpty(zr1)) {
        reviewContactsDto.setQuestion1level1(zr1);//责任1
        //校验是否存在订单责任1
        boolean is = false;
        for (ReviewDataDictionaryEntity duty1 : duty1List) {
          String gmName = duty1.getValueMeaning();
          if (gmName.equals(zr1)) {
            is = true;
            break;
          }
        }
        if (!is) {
          dataList.setError("", "第" + forIndex + "行:未找到名称为\'" + zr1 + "\'的责任位1!");
          return dataList;
        }

      }
      String zr2 = resultArray[5];
      if (StringUtils.isNotEmpty(zr2)) {
        reviewContactsDto.setQuestion1level2(zr2);//责任2
        //校验是否存在订单责任2
        boolean is = false;
        for (ReviewDataDictionaryEntity duty2 : duty2List) {
          String gmName = duty2.getValueMeaning();
          if (gmName.equals(zr2)) {
            is = true;
            break;
          }
        }
        if (!is) {
          dataList.setError("", "第" + forIndex + "行:未找到名称为\'" + zr2 + "\'的责任位2!");
          return dataList;
        }
      }
      String gmStr = resultArray[6];//工贸逗号分隔
      if (StringUtils.isNotEmpty(gmStr)) {
        reviewContactsDto.setCompany(gmStr);//工贸
        //校验是否存在工贸
        List<String> gmList = Arrays.asList(gmStr.split(","));
        for (String gm : gmList) {
          boolean is = false;
          for (ReviewDataDictionaryEntity company : companyList) {
            String gmName = company.getValueMeaning();
            if (gmName.equals(gm)) {
              is = true;
              break;
            }
          }
          if (!is) {
            dataList.setError("", "第" + forIndex + "行:未找到名称为\'" + gm + "\'的工贸!");
            return dataList;
          }
        }
      }
      if (StringUtils.isNotEmpty(resultArray[7])) {
        reviewContactsDto.setUserid(resultArray[7]);//人员ID
      }
      if (StringUtils.isNotEmpty(resultArray[8])) {
        reviewContactsDto.setManageruserid1(resultArray[8]);//一级领导ID
      } else {
        String errorStr = MessageFormat.format(excelError, forIndex, "一级领导ID");
        dataList.setError("", errorStr);
        return dataList;
      }
      if (StringUtils.isNotEmpty(resultArray[9])) {
        reviewContactsDto.setManageruserid2(resultArray[9]);//二级领导ID
      } else {
        String errorStr = MessageFormat.format(excelError, forIndex, "二级领导ID");
        dataList.setError("", errorStr);
        return dataList;
      }
      if (StringUtils.isNotEmpty(resultArray[10])) {
        reviewContactsDto.setId(resultArray[10]);//主键ID
        params.put("id", resultArray[10]);
      } else {
        String errorStr = MessageFormat.format(excelError, forIndex, "主键ID");
        dataList.setError("", errorStr);
        return dataList;
      }
      resultList.add(reviewContactsDto);
    }
    dataList.setResult(resultList);
    return dataList;
  }
}
