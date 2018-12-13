package com.haier.svc.api.controller;

import com.haier.common.util.StringUtil;
import com.haier.distribute.service.SelectProductInfoService;
import com.haier.shop.model.OrderProductsNew;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cbs")
public class ProductInfoController {
    @Autowired
    private SelectProductInfoService selectProductInfoService;
    @RequestMapping(value = { "/selectByCode" }, method = { RequestMethod.POST })
    public void selectByCode(HttpServletRequest request, HttpServletResponse response,String code)throws Exception{
        StringBuffer receiveMsg = new StringBuffer();
        BufferedReader reader = null;
        InputStream inputStream = null;
        PrintWriter writer = null;
        String xml = "";
        response.setCharacterEncoding("UTF-8");
        inputStream = request.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            receiveMsg.append(line);
        }
         code = receiveMsg.toString();
         xml=selectProductInfoService.getProductByCode(code);
        try {
            writer = response.getWriter();
            writer.write(xml);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
