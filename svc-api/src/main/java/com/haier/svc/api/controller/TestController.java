package com.haier.svc.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("zb")
public class TestController {
    @RequestMapping("zhangbo")
    public void zhangbo(HttpServletRequest request, HttpServletResponse response)throws Exception{
        response.getWriter().write("1");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
