package com.haier.svc.service;


import com.haier.purchase.data.model.WwwStockSaveRequire;
import java.util.List;

/**
 * <p>
 * Description: [获取3W库存接口]
 * </p>
 * Created on 2017年03月27日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface WwwStockGetService {
    //3w库存获取接口
    public String getWwwStock();

    public List<WwwStockSaveRequire> select(String productTypeName, String enChannleId);
}
