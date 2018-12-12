package com.haier.eis.service;

import com.haier.eis.model.IExecute;

/**
 * 写日志代理接口
 */
public interface EisWriteLogService {

    /**
     * 向 eis_interface_data_Log 写日志
     * @param foreignKey
     * @param interfaceCode
     * @param requestData
     * @param responseData
     */
    void writeLog(String foreignKey, String interfaceCode, String requestData, String responseData, String errMessage);

    /**
     * 向vomwww_outstock_synchronizelogs写日志
     * @param interfaceCode
     * @param requestData
     * @param execute
     */
    void vomwwwWriteLog(String interfaceCode, String requestData, IExecute execute);

    /**
     * 向 eis_interface_data_Log 写日志，返回ID
     * @param foreignKey
     * @param interfaceCode
     * @param requestData
     * @param execute
     * @return
     */
    Integer writeLogAndReturnId(String foreignKey, String interfaceCode, String requestData, IExecute execute);
}
