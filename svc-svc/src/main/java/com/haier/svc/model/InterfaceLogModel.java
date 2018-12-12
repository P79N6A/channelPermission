package com.haier.svc.model;

import com.haier.purchase.data.service.PurchaseT2OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李超 on 2018/3/16.
 */
@Service
public class InterfaceLogModel {

    private static Logger log = LogManager.getLogger(InterfaceLogModel.class);

    @Autowired
    private PurchaseT2OrderService purchaseT2OrderService;

    public void insertInterfaceLog(String interfaceName,String interfaceCategory,Object interfaceMessage){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("interfaceName",interfaceName);
            map.put("interfaceCategory",interfaceCategory);
            map.put("interfaceDate",new Date());
            map.put("interfaceMessage",interfaceMessage);
            purchaseT2OrderService.insertT2OrderInterfaceLog(map);
        } catch (Exception e){
            e.printStackTrace();
            log.error("添加接口日志异常：" + e.getMessage());
        }
    }
}