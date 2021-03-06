package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.logistics.Model.HpAllotNetPointModel;
import com.haier.logistics.service.HpDispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HpDispatchServiceImpl implements HpDispatchService {
    private static Logger log = LoggerFactory.getLogger(HpDispatchServiceImpl.class);
    @Autowired
    private HpAllotNetPointModel hpAllotNetPointModel;
    @Override
    public ServiceResult<String> saveNetPoint(String requestXml) {

        return this.saveNetPointFromVom(requestXml);
    }

    @Override
    public ServiceResult<String> saveNetPointFromVom(String requestData) {


        ServiceResult<String> result = new ServiceResult<String>();
        try {
            String response = "";
            if (requestData.contains("<?xml")){
                response = hpAllotNetPointModel.saveNetPoint(requestData);
                result.setSuccess(true);
            }else {
                response = hpAllotNetPointModel.saveNetPointFromVom(requestData);
                result.setSuccess("保存网点数据成功".equals(response));
            }
            result.setResult(response);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("保存HP分配网点信息失败");
            log.error("保存HP分配网点信息失败", e);
        }
        return result;
    }
}
