package com.haier.svc.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.IDMLoginResult;
import com.haier.svc.api.controller.util.LdapUserAuth;
import com.haier.svc.api.controller.util.LoginResult;
import com.haier.system.model.SysUser;
import com.haier.system.service.SystemCenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LdapUserAuth info;

    @Autowired
    SystemCenterService systemCenterService;

    /**
     *
     * 登录
     * @param userCode 用户名
     * @param userPassword 密码
     * @return
     */

    @ResponseBody
    @RequestMapping(value = {"/toLogin"},method = {RequestMethod.POST})
    public String toLogin(String userCode, String userPassword, HttpServletResponse resp, HttpServletRequest request){

        LoginResult lr = new LoginResult();

        //去idm验证用户名和密码
        IDMLoginResult result = info.login(userCode, userPassword);

        System.out.println("======="+result);

        if(IDMLoginResult.SUCCESS.equals(result.getResult())){
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            SysUser sysUser = systemCenterService.getUserByLoginId(userCode).getResult();
            if (sysUser == null){
                SysUser su = new SysUser();
                su.setUserName(userCode);
                su.setLoginId(userCode);
                su.setPassword(userPassword);
                ServiceResult<Integer> res = systemCenterService.createUser(su);
            }
            if (session.getAttribute("loginId") == null){
                session.setAttribute("loginId",userCode);
                session.setAttribute("userId",sysUser.getUserId());
                session.setAttribute("userName",sysUser.getUserName());
                session.setAttribute("password",userPassword);
                systemCenterService.userLogin(userCode,userPassword,sessionId);
            }

            System.out.println("success");
            System.out.println("sessionId==>"+sessionId);
        }else{
            BeanUtils.copyProperties(result, lr);
        }
        return result.getMsg();
    }

    @ResponseBody
    @RequestMapping(value = {"/logout"},method = {RequestMethod.POST})
    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }

}