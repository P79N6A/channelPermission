package com.haier.eis.services;

import java.util.Date;

import com.haier.eis.dao.eis.EisInterfaceDataLogDao;
import com.haier.eis.dao.eis.VomwwwOutstockSynchronizeLogsDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.IExecute;
import com.haier.eis.model.VomwwwOutstockSynchronizeLogs;
import com.haier.eis.service.EisWriteLogService;

/**
 * 写接口日志类
 */
@Service
public class EisWriteLogServiceImpl implements EisWriteLogService {

    private static Logger logger = LogManager.getLogger(EisWriteLogServiceImpl.class);

    @Autowired
    EisInterfaceDataLogDao eisInterfaceDataLogDao;

    @Autowired
    VomwwwOutstockSynchronizeLogsDao vomwwwOutstockSynchronizeLogsDao;

    @Override
    public void writeLog(String foreignKey, String interfaceCode, String requestData,
                         String responseData, String errMessage) {
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(foreignKey);
        dataLog.setInterfaceCode(interfaceCode);
        dataLog.setRequestData(requestData);
        dataLog.setRequestTime(new Date());
        Long startTime = System.currentTimeMillis();

        if (null == responseData || "".equals(responseData)){
            dataLog.setErrorMessage(
                    "调用" + interfaceCode + ",foreignKey(" + foreignKey + "),发生异常：" + errMessage);
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
            eisInterfaceDataLogDao.insert(dataLog);
        } catch (Exception e) {
            logger.error("调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),记录日志发生异常："
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
    @Override
    public void vomwwwWriteLog(String interfaceCode, String requestData, IExecute execute) {
        VomwwwOutstockSynchronizeLogs dataLog = new VomwwwOutstockSynchronizeLogs();
        String message = "";
        String responseData = null;
        try {
            responseData = execute.execute();
            //responseData = responseData.replaceAll("(\\s*)", "");
        } catch (Exception e) {
            logger.error("调用接口" + interfaceCode + ",发生异常：" + e.getMessage(), e);
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
            vomwwwOutstockSynchronizeLogsDao.insert(dataLog);
        } catch (Exception e) {
            logger.error("调用接口" + interfaceCode + ",记录日志发生异常：" + e.getMessage(), e);
        }
    }

    @Override
    public Integer writeLogAndReturnId(String foreignKey, String interfaceCode,
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
            logger.error(
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
            eisInterfaceDataLogDao.insertAndReturnId(dataLog);
        } catch (Exception e) {
            logger.error("调用接口" + interfaceCode + ",foreignKey(" + foreignKey + "),记录日志发生异常："
                      + e.getMessage(),
                e);
            return null;
        }
        return dataLog.getId();
    }

}
