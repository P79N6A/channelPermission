package com.haier.vehicle.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.InterfaceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.vehicle.model.VehicleInterfaceLogModel;
import com.haier.vehicle.service.VehicleInterfaceLogService;

import java.util.List;
import java.util.Map;

/**
 * 接口调用参数日志
 *
 * @author
 * @create 2017-09-13 9:20
 **/
@Service
public class VehicleInterfaceLogServiceImpl implements VehicleInterfaceLogService {

	@Autowired
    private VehicleInterfaceLogModel vehicleInterfaceLogModel;

    @Override
    public int insertLog(String interfaceName,String interfaceMessage) {

        return vehicleInterfaceLogModel.inserrtLog(interfaceName,interfaceMessage);
    }

    @Override
    public ServiceResult<List<InterfaceLog>> findInterfaceLog(Map<String, Object> params) {
        ServiceResult<List<InterfaceLog>> result = new ServiceResult<List<InterfaceLog>>();
        try {
            result.setResult(vehicleInterfaceLogModel.findInterfaceLog(params));
            int pagecount = vehicleInterfaceLogModel.getInterfaceLogRow(params);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(pagecount);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
