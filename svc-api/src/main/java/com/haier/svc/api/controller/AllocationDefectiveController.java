package com.haier.svc.api.controller;

import com.haier.common.util.StringUtil;
import com.haier.svc.api.controller.pop.HpAllotNetPointController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Controller
@RequestMapping("cbs")
public class AllocationDefectiveController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(AllocationDefectiveController.class);
    @RequestMapping(value = { "/allocationDefective" }, method = { RequestMethod.POST })
    public void allocationDefective(HttpServletRequest request, HttpServletResponse response){
        StringBuffer receiveMsg = new StringBuffer();
        BufferedReader reader = null;
        InputStream inputStream = null;
        PrintWriter writer = null;
        StringBuffer responseXml = new StringBuffer();
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
                responseMsg(writer, processFailMsg("F", "", "接收OMS信息为空"));
                return;
            }

        }catch (Exception e){
            log.error("接收数据OMS数据失败：", e);
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
     * @param cOrderSn
     * @param msg
     * @return
     */
    private String processFailMsg(String flag, String cOrderSn, String msg) {
        return "\n<FLAG>" + flag + "</FLAG>" + "<MESSAGE>" + msg + "</MESSAGE>";
    }
}
