package com.haier.svc.api.controller.workorder;

import com.alibaba.fastjson.JSON;
import com.haier.common.PagerInfo;
import com.haier.shop.model.WOUser;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.traderate.service.WorkOrderWOUserService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.workorder
 * @Date: Created in 2018/4/23 15:40
 * @Modified By:
 */
@Controller
@RequestMapping("/woUser")
public class WorkOrderUserController {

  private static Logger log = LogManager.getLogger(WorkOrderUserController.class);
  @Resource
  private WorkOrderWOUserService workOrderWOUserService;

  @GetMapping("userList")
  public String userList() {
    return "workorder/userList";
  }

  @GetMapping("getList")
  @ResponseBody
  public Object getList(WOUser param, Integer page, Integer rows) {
    if (page == null) {
      page = 1;
    }
    rows = rows == null ? 50 : rows;
    PagerInfo pager = new PagerInfo(rows, page);
    return workOrderWOUserService.getPageByParam(param, pager);
  }

  @ResponseBody
  @GetMapping(value = "/getuser.ajax")
  public Object getUser(@RequestParam(value = "value_set_id", required = false) String value_set_id,
      @RequestParam(value = "type", required = false) String type) {
    return workOrderWOUserService.selectBySetId(value_set_id, type);
  }


  @PostMapping("addUser")
  @ResponseBody
  public Object addUser(HttpServletRequest request, WOUser woUser) {
    HttpSession session = request.getSession();
    String userName = (String) session.getAttribute("userName");
    woUser.setCreateUser(userName);
    return workOrderWOUserService.addWOUser(woUser);
  }

  @PostMapping("detail")
  @ResponseBody
  public Object detail(String userId) {
    return workOrderWOUserService.getById(userId);
  }

  @PostMapping("editUser")
  @ResponseBody
  public Object updateUser(HttpServletRequest request, WOUser woUser) {
    WOUser res = workOrderWOUserService.getById(woUser.getUserId());
    BeanUtils.copyProperties(woUser, res);
    HttpSession session = request.getSession();
    String userName = (String) session.getAttribute("userName");
    res.setUpdateUser(userName);
    return workOrderWOUserService.updateById(res);
  }

  @PostMapping("delUser")
  @ResponseBody
  public Object delUser(String userIds) {
    List<String> list = JSON.parseArray(userIds, String.class);
    return workOrderWOUserService.delBatch(list);
  }

  /**
   * 获取没有人员的责任位的下拉列表
   */
  @ResponseBody
  @GetMapping(value = "/selectnotuserid.ajax")
  public String selectNotuserId() {
    return JSON.toJSONString(workOrderWOUserService.selectNotuserId());
  }
}
