package com.haier.svc.api.controller.pop;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;

import com.haier.logistics.service.HpDispatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Controller
@RequestMapping("hp")
public class HpAllotNetPointController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(HpAllotNetPointController.class);
    @Resource
    private HpDispatchService hpDispatchService;
    /**
     * 接收HP分配网点信息入口
     * @param request
     * @param response
     */
    @RequestMapping(value = { "/hpallot_netpoint" }, method = { RequestMethod.POST })
    public void hpAllotNetPoint(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer receiveMsg = new StringBuffer();
        BufferedReader reader = null;
        InputStream inputStream = null;
        PrintWriter writer = null;
        StringBuffer responseXml = new StringBuffer();
        response.setCharacterEncoding("UTF-8");
        try {
            long startTime = System.currentTimeMillis();
            writer = response.getWriter();
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                receiveMsg.append(line);
            }
            String requestXml = receiveMsg.toString();
            if (StringUtil.isEmpty(requestXml, true)) {
                log.info("接收hp分配网点信息为空");
                responseMsg(writer, processFailMsg("N", "", "接收hp分配网点信息为空"));
                return;
            }
            //保存源数据
            ServiceResult<String> saveResult = hpDispatchService.saveNetPoint(requestXml);
            if (!saveResult.getSuccess()) {
                log.error("保存hp分配网点信息失败：" + saveResult.getMessage());
                responseMsg(writer, processFailMsg("N", "", "保存hp分配网点信息失败"));
                return;
            }
            //返回处理结果到HP
            responseMsg(writer, saveResult.getResult());
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.info("receive HP Allot netpoint,totalTime:" + time + "ms");
        } catch (Exception e) {
            log.error("处理hp分配网点信息出现异常：", e);
            responseXml.append(processFailMsg("N", "", "处理hp分配网点信息出现异常"));
            //返回处理结果到HP
            responseMsg(writer, responseXml.toString());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
    /**
     * 返回处理结果给HP
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
        return "\n<ITEM>" + "<FLAG>" + flag + "</FLAG>" + "<MSG>" + msg + "</MSG>" + "<ORDERNUM>"
                + cOrderSn + "</ORDERNUM>" + "</ITEM>";
    }
}
