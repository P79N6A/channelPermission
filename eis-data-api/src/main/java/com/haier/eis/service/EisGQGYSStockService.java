package com.haier.eis.service;


import com.haier.eis.model.GQGYSStock;


import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */
public interface EisGQGYSStockService {
    List<GQGYSStock> getAll();
    List<GQGYSStock> getBySku(String sku);
}
