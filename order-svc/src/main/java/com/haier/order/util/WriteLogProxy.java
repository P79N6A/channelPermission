package com.haier.order.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.VomwwwOutstockSynchronizeLogs;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.VomwwwOutstockSynchronizeLogsService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;

/**
 * 写接口日志代理类
 *
 * @Filename: WriteLogProxy.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class WriteLogProxy implements ApplicationContextAware {

    private static org.apache.log4j.Logger          log = org.apache.log4j.LogManager
            .getLogger(WriteLogProxy.class);
    private static ApplicationContext               applicationContext;
    private static EisInterfaceDataLogService eisInterfaceDataLogService;
    private static VomwwwOutstockSynchronizeLogsService vomwwwOutstockSynchronizeLogsService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WriteLogProxy.applicationContext = applicationContext;
    }

    public static void writeLog(String foreignKey, String interfaceCode, String requestData,
                                IExecute iExecute) {
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(foreignKey);
        dataLog.setInterfaceCode(interfaceCode);
        dataLog.setRequestData(requestData);
        dataLog.setRequestTime(new Date());
        Long startTime = System.currentTimeMillis();
        String responseData = null;
        try {
            responseData = iExecute.execute();
        } catch (Exception e) {
            log.error(
                    "调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),发生异常：" + e.getMessage(),
                    e);
            dataLog.setErrorMessage(
                    "调用" + interfaceCode + ",foreignKey(" + foreignKey + "),发生异常：" + e.getMessage());
            responseData = "";
        }
        dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
        dataLog.setResponseData(responseData);
        dataLog.setResponseTime(new Date());
        if (!StringUtils.isBlank(responseData)) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        } else {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        }
        try {
            getDao().insert(dataLog);
        } catch (Exception e) {
            log.error("调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),记录日志发生异常："
                            + e.getMessage(),
                    e);
        }
    }

    /**
     * www菜鸟仓，获取vom出库信息
     * @param interfaceCode
     * @param requestData
     * @param execute
     */
    public static void vomwwwWriteLog(String interfaceCode, String requestData, IExecute execute) {
        VomwwwOutstockSynchronizeLogs dataLog = new VomwwwOutstockSynchronizeLogs();
        String message = "";
        String responseData = null;
        try {
            responseData = execute.execute();
            //responseData = responseData.replaceAll("(\\s*)", "");
        } catch (Exception e) {
            log.error("调用接口" + interfaceCode + ",发生异常：" + e.getMessage(), e);
            message = "调用" + interfaceCode + ",发生异常：" + e.getMessage();
            responseData = "";
        }
        dataLog.setCount(0);
        dataLog.setType("xml");
        dataLog.setPushData(requestData);
        dataLog.setAddTime(System.currentTimeMillis() / 1000);
        dataLog.setReturnData(responseData);
        dataLog.setVerifyResult(0);
        dataLog.setMessage(message);
        if (!StringUtils.isBlank(responseData)) {
            if (responseData.contains("<flag>T</flag>")) {
                if (!responseData.contains("<list>")
                        || responseData.replace(" ", "").contains("<list></list>")) {
                    dataLog.setStatus(VomwwwOutstockSynchronizeLogs.RESPONSE_STATUS_ERROR);
                } else {
                    dataLog.setStatus(VomwwwOutstockSynchronizeLogs.RESPONSE_STATUS_UNTREADED);
                }
            } else {
                dataLog.setStatus(VomwwwOutstockSynchronizeLogs.RESPONSE_STATUS_ERROR);
            }
        } else {
            dataLog.setStatus(VomwwwOutstockSynchronizeLogs.RESPONSE_STATUS_ERROR);
        }
        try {
            getVomwwwOutstockSynchronizeLogsDao().insert(dataLog);
        } catch (Exception e) {
            log.error("调用接口" + interfaceCode + ",记录日志发生异常：" + e.getMessage(), e);
        }
    }

    public static Integer writeLogAndReturnId(String foreignKey, String interfaceCode,
                                              String requestData, IExecute execute) {
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(foreignKey);
        dataLog.setInterfaceCode(interfaceCode);
        dataLog.setRequestData(requestData);
        dataLog.setRequestTime(new Date());
        Long startTime = System.currentTimeMillis();
        String responseData = null;
        try {
            responseData = execute.execute();
        } catch (Exception e) {
            log.error(
                    "调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),发生异常：" + e.getMessage(),
                    e);
            dataLog.setErrorMessage(
                    "调用" + interfaceCode + ",foreignKey(" + foreignKey + "),发生异常：" + e.getMessage());
            responseData = "";
        }
        dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
        dataLog.setResponseData(responseData);
        dataLog.setResponseTime(new Date());
        if (!StringUtils.isBlank(responseData)) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        } else {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        }
        try {
            getDao().insertAndReturnId(dataLog);
        } catch (Exception e) {
            log.error("调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),记录日志发生异常："
                            + e.getMessage(),
                    e);
            return null;
        }
        return dataLog.getId();
    }

    private static EisInterfaceDataLogService getDao() {
        if (null == eisInterfaceDataLogService) {
            eisInterfaceDataLogService = (EisInterfaceDataLogService) applicationContext
                    .getBean("eisInterfaceDataLogService");
        }
        return eisInterfaceDataLogService;
    }

    private static VomwwwOutstockSynchronizeLogsService getVomwwwOutstockSynchronizeLogsDao() {
        if (null == vomwwwOutstockSynchronizeLogsService) {
            vomwwwOutstockSynchronizeLogsService = (VomwwwOutstockSynchronizeLogsService) applicationContext
                    .getBean("vomwwwOutstockSynchronizeLogsDao");
        }
        return vomwwwOutstockSynchronizeLogsService;
    }

}
