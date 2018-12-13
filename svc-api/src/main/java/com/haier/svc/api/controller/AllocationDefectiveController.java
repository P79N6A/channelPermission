package com.haier.svc.api.controller;

import com.haier.afterSale.service.OmsReceivedQueueService;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderProductsNew;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
*
* 接收OMS推送的调拨残次数据
* */
@Controller
@RequestMapping("cbs")
public class AllocationDefectiveController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(AllocationDefectiveController.class);
    @Autowired
    private OmsReceivedQueueService omsReceivedQueueService;
    @RequestMapping(value = { "/allocationDefective" }, method = { RequestMethod.POST })
    public void allocationDefective(HttpServletRequest request, HttpServletResponse response){
        StringBuffer receiveMsg = new StringBuffer();
        BufferedReader reader = null;
        InputStream inputStream = null;
        PrintWriter writer = null;
        String xml = "";
        Document document = null;
        StringBuffer responseXml = new StringBuffer();
        List<OrderProductsNew> reservationDates = new ArrayList<OrderProductsNew>();
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                receiveMsg.append(line);
            }
            String requestXml = receiveMsg.toString();
            if (StringUtil.isEmpty(requestXml)){
                log.info("接收OMS数据为空");
                responseMsg(writer, processFailMsg("F",  "接收OMS信息为空"));
                return;
            }
            xml = receiveMsg.toString();
            try {
                document = DocumentHelper.parseText(xml);
            }catch (Exception e){
                log.error("xml转换document对象失败：", e);
                responseMsg(writer, processFailMsg("F",  "xml转换document对象失败"));
                return;
            }
            if (document == null) {
                log.info("xml转换document对象为空！");
                responseMsg(writer, processFailMsg("F",  "xml转换document对象为空！"));
                return;
            }
            Map<String,Object> map=parseDoc(document);
            String flag=(String) map.get("FLAG");
            String message=(String) map.get("MESSAGE");
            responseMsg(writer, processFailMsg(flag,message));
            if (flag.equals("S")){
               int sign= insert(xml);
               if (sign !=1){
                   log.info("插入数据失败！");
                   responseMsg(writer, processFailMsg("F",  "插入数据失败,请重新推送！"));
                   return;
               }
            }
            return;
        }catch (Exception e){
            log.error("接收数据OMS数据失败：", e);
            responseMsg(writer, processFailMsg("F",  "接收数据OMS数据失败！"));
            return;
        }
    }
    /**
     * 返回处理结果给OMS
     * @param writer
     * @param responseXml
     */
    private void responseMsg(PrintWriter writer, String responseXml) {
        String responseMsg = "<processResponse>" + responseXml + "\n</processResponse>";
        writer.write(responseMsg);
        writer.flush();
        writer.close();
    }
    /**
     * 拼接单条处理结果
     * @param flag
     * @param
     * @param msg
     * @return
     */
    private String processFailMsg(String flag,String msg) {
        return "\n<FLAG>" + flag + "</FLAG>" + "<MESSAGE>" + msg + "</MESSAGE>";
    }
    /**
     * 解析xml
     * @param
     * @param
     * @param
     * @return
     */
    private static Map<String,Object> parseDoc(Document doc){
        Map<String,Object> map=new HashMap<>();
        // 获取根节点
        org.dom4j.Element rootElt = doc.getRootElement();
        if (!rootElt.getName().equals("Return")){
            map.put("FLAG","F");
            map.put("MESSAGE","根节点不是Return");
            return map;
        }
        //通过根元素得到一级子元素
        List<org.dom4j.Element>firstChild=rootElt.elements();
        if (firstChild.size()==0){
            map.put("FLAG","F");
            map.put("MESSAGE","数据为空");
            return map;
        }
        for (org.dom4j.Element element:firstChild){
            if(!element.getName().equals("row")){
                System.out.println(element.getName());
                map.put("FLAG","F");
                map.put("MESSAGE","一级子元素不是row");
                return map;
            }
            List<org.dom4j.Element> rowChild=element.elements();
        }
        map.put("FLAG","S");
        map.put("MESSAGE","成功");
        return map;
    }
    private  int insert(String xml){
        Map<String,Object> map=new HashMap<>();
        int x;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data=simpleDateFormat.format(new Date());
        map.put("addTime",data);
        map.put("status",0);
        map.put("content",xml);
        try{
             x=omsReceivedQueueService.insert(map);
        }catch (Exception e){
            log.error("插入数据失败:"+e);
            return 0;
        }
        return x;
    }
}
