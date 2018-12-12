package com.haier.svc.api.controller.util.intercept;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by 李超 on 2018/2/24.
 */
public class InterceptorConfig implements HandlerInterceptor {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        log.info("---------------------开始进入请求地址拦截----------------------------");
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("loginId") == null) {
            httpServletResponse.sendRedirect("/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        log.info("---------------视图渲染之后的操作-------------------------");
    }
}
