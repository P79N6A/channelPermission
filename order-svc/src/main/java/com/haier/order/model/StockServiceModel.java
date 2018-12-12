package com.haier.order.model;




import com.haier.common.ServiceResult;
import com.haier.shop.model.BigStoragesRela;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
@Service("stockServiceModel")
public class StockServiceModel {
    @Autowired
    private StockModel stockModel;


    private static Logger log = LoggerFactory.getLogger(StockServiceModel.class);

    public ServiceResult<List<BigStoragesRela>> getBigStoragesRelaList() {
        ServiceResult<List<BigStoragesRela>> result = new ServiceResult<List<BigStoragesRela>>();
        try {
            result.setResult(stockModel.getBigStoragesRelaList());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询大家电多级库位关系列表：" + e.getMessage());
            log.error("查询大家电多级库位关系列表：", e);
        }
        return result;
    }
}
