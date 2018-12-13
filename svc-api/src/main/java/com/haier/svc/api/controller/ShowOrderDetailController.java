package com.haier.svc.api.controller;

import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.dangdang.DangdangClient;
import com.haier.svc.api.controller.util.http.gome.*;
import com.haier.svc.api.controller.util.http.jingdong.JingdongBxClient;
import com.haier.svc.api.controller.util.http.jingdong.JingdongClient;
import com.haier.svc.api.controller.util.http.suning.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;

@Controller
@RequestMapping("showOrderDetail")
public class ShowOrderDetailController {
    @Autowired
    private DangdangClient dangdangClient;
    @Autowired
    private JingdongBxClient jingdongBxClient;
    @Autowired
    private JingdongClient jingdongClient;
    // 国美 一共6个店
    @Autowired
    private GomeClient gomeClient;
    @Autowired
    private GomeTSClient gomeTSClient;
    // 苏宁一共有5个店
    @Autowired
    private SuningClient suningClient;
    @Autowired
    private SuningGqClient suningGqClient;
    @Autowired
    private SuningDepositClient suningDepositClient;
    @Autowired
    private SuningGQDepositClient suningGQDepositClient;
    @Autowired
    private SuningQDZXClient suningQDZXClient;

    @RequestMapping("/showOrderDetailList")
    public String showCommissionList() {

        return "order/ShowOrderDetail";
    }

    @RequestMapping("/showDetail")
    @ResponseBody
    public String orderDetailPage(String orderId, String reqType) {
        //默认为josnType调用excute双参,如果是xml调用excute三参
        String xmlType = "xml";
        if (reqType.equals("DDW")) {
            return excute( dangdangClient,orderId, xmlType);
        }else if(reqType.equals("JDHEGQ")){
            return excute(jingdongClient,orderId);
        }else if(reqType.equals("JDHEBXGQ")) {
            return excute( jingdongBxClient,orderId);
        }else if (reqType.equals("SNYG")) {
            return excute(suningClient,orderId);
        }else if(reqType.equals("SNHEGQ")){
            return excute( suningGqClient,orderId);
        }else if(reqType.equals("SNQDZX")) {
            return excute( suningQDZXClient,orderId);
        }else if (reqType.equals("SNYGDJUNP")) {
            return excute( suningDepositClient, orderId);
        } else if (reqType.equals("SNYGDJ")) {
            return excute( suningDepositClient,orderId);
        }else if(reqType.equals("SNHEGQDJUNP")){
            return excute( suningGQDepositClient,orderId);
        }else if(reqType.equals("SNHEGQDJ")){
            return excute(suningGQDepositClient, orderId);
        }else if (reqType.equals("GMZX")) {
            return excute( gomeClient, orderId);
        }else if (reqType.equals("GMZXTS")) {
            return excute( gomeTSClient,orderId);
        }
        return "没有查询到可执行的方法，请检查请求的渠道是否正确";
    }

    public String excute(AbstractHaierHttpClient haierHttpClient, String orderId) {
        String josnType = "json";
        return excute( haierHttpClient, orderId,josnType);
    }

    public String excute(AbstractHaierHttpClient haierHttpClient, String orderId, String type) {
        try {
            if (type.equals("json")) {
                return jsonFormatter(haierHttpClient.getOrderByOrderId(orderId));
            } else {
                return formatXml(haierHttpClient.getOrderByOrderId(orderId));
            }
        } catch (Exception e) {
            return "获取数据失败，请检查接口是否异常";
        }
    }

    public static String jsonFormatter(String uglyJSONString) {
        String prettyJsonString = format(uglyJSONString);
        return prettyJsonString;
    }

    public static String formatXml(String str) throws Exception {
        Document document = null;
        document = DocumentHelper.parseText(str);
        // 格式化输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("gb2312");
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();
        return StringEscapeUtils.escapeHtml3(writer.toString());
    }

    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}
