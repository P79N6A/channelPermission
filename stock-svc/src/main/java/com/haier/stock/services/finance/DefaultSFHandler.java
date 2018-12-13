package com.haier.stock.services.finance;


import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import org.springframework.stereotype.Service;

/**
 * 如果前面的处理者无法处理时，则由此默认的处理者处理，能处理任何请求且处理结果都是失败
 *                       
 * @Filename: DefaultSFHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
@Service
public class DefaultSFHandler extends SFHandler {

    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        return true;
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        Result result = new Result();
        result.status = EisInterfaceFinance.STATUS_SUCCESS;
        result.message = "cOrderSn:" + transQueue.getCorderSn() + ",type:"
                         + transQueue.getBillType();
        return result;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        Result result = new Result();
        result.status = EisInterfaceFinance.STATUS_SUCCESS;
        result.message = "无需发送财务接口";
        return result;
    }

}
