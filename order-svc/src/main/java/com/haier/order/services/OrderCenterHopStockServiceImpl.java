package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.StockModel;
import com.haier.stock.model.InventoryBusinessTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import com.haier.svc.bean.pop.InvStockLock;
import com.haier.svc.bean.pop.InventoryBusinessTypes;
import com.haier.svc.bean.pop.Stock;*/
@Service
public class OrderCenterHopStockServiceImpl {
    private static Logger log = LoggerFactory.getLogger(OrderCenterHopStockServiceImpl.class);
    private static final String LOG_MARK = "[Stock][HopStockService] ";
    @Autowired
    private StockModel stockModel;

    /**
     * 释放冻结库存
     * @param sku 物料
     * @param releaseQty 释放数量
     * @param refNo 单据号
     * @param billType 交易类型
     * @return 释放结果
     */
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, Integer releaseQty,
                                                        String refNo,
                                                        InventoryBusinessTypes billType) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            return stockModel.releaseFrozenStockQty(sku, refNo, releaseQty, billType);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("释放冻结库存失败：" + e.getMessage());
            log.error(LOG_MARK + "释放冻结库存失败(" + sku + "," + refNo + "," + releaseQty + ","
                            + billType.getCode() + ")：",
                    e);
        }
        return result;
    }

}
